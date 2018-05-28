package com.pp.iot.de.service.fragments

import com.pp.iot.de.service.R
import com.pp.iot.de.service.viewModels.ServerDataViewModel
import kotlinx.android.synthetic.main.server_data_page.ShowDataButton
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ServerDataPageFragment : FragmentBase<ServerDataViewModel>(ServerDataViewModel::class.java) {
    override fun initBindings() {
        ShowDataButton.setOnClickListener { async(UI) { viewModel.getMeasurement() } }
    }

    override val layoutResourceId: Int
        get() = R.layout.server_data_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
    }

}
