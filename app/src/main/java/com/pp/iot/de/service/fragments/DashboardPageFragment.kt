package com.pp.iot.de.service.fragments

import android.content.Context.LOCATION_SERVICE
import android.location.Location
import com.pp.iot.de.service.R
import com.pp.iot.de.service.viewModels.DashboardViewModel
import kotlinx.android.synthetic.main.dashboard_page.*
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.bluetooth.BluetoothAdapter
import android.content.IntentFilter


class DashboardPageFragment : FragmentBase<DashboardViewModel>(DashboardViewModel::class.java) {
    override val layoutResourceId: Int
        get() = R.layout.dashboard_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager

        activity?.registerReceiver(receiver, IntentFilter(BluetoothDevice.ACTION_FOUND));
    }

    private var locationManager: LocationManager? = null

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            LocationText.text = "" + location.longitude + ":" + location.latitude
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private val BTAdapter = BluetoothAdapter.getDefaultAdapter()

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, java.lang.Short.MIN_VALUE)
                val name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME)
                LocationText.text = "" + LocationText.text + name + " => " + rssi + "dBm\n";
            }
        }
    }

    override fun initBindings() {
        LocationButton.setOnClickListener { _ ->
            try {
                locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener);
            } catch (ex: SecurityException) {
                Log.d("DPF", "Security Exception, no location available")
            }
        }

        BluetoothDevicesButton.setOnClickListener { _ -> BTAdapter.startDiscovery() }
    }
}