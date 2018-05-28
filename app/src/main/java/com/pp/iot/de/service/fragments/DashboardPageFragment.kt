package com.pp.iot.de.service.fragments

import com.pp.iot.de.service.R
import com.pp.iot.de.service.viewModels.DashboardViewModel
import kotlinx.android.synthetic.main.dashboard_page.DeviceDataViewButton
import kotlinx.android.synthetic.main.dashboard_page.ServerDataViewButton

class DashboardPageFragment : FragmentBase<DashboardViewModel>(DashboardViewModel::class.java) {

    override val layoutResourceId: Int
        get() = R.layout.dashboard_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
    }

    override fun initBindings() {
        DeviceDataViewButton.setOnClickListener { viewModel.onDeviceDataViewButtonClick() }
        ServerDataViewButton.setOnClickListener { viewModel.onServerDataViewButtonClick() }
    }
}
