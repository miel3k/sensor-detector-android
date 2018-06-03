package com.pp.iot.de.service.fragments

import android.support.v7.widget.LinearLayoutManager
import com.pp.iot.de.service.R
import com.pp.iot.de.service.adapters.RecyclerAdapter
import com.pp.iot.de.service.viewModels.ServerDataViewModel
import kotlinx.android.synthetic.main.server_data_page.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ServerDataPageFragment : FragmentBase<ServerDataViewModel>(ServerDataViewModel::class.java) {
    override fun initBindings() {
        MeasurementsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            async(UI) {
                adapter = RecyclerAdapter(viewModel.getDevices())
            }
        }
    }

    override val layoutResourceId: Int
        get() = R.layout.server_data_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
    }
}
