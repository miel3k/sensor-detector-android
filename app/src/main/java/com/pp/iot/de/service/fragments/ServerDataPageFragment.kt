package com.pp.iot.de.service.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pp.iot.de.models.model.Device
import com.pp.iot.de.service.R
import com.pp.iot.de.service.recyclerAdapters.BasicRecyclerAdapter
import com.pp.iot.de.service.viewModels.ServerDataViewModel
import kotlinx.android.synthetic.main.device_item.view.*
import kotlinx.android.synthetic.main.server_data_page.*

class ServerDataPageFragment : FragmentBase<ServerDataViewModel>(ServerDataViewModel::class.java) {
    override fun initBindings() {
        MeasurementsList.layoutManager = LinearLayoutManager(context)
        bindings.add(viewModel::devicesList.subscribeOneWay {
            MeasurementsList.adapter =
                    BasicRecyclerAdapter(
                            viewModel.devicesList,
                            this::itemDataTemplate,
                            this::itemItemTemplate,
                            this::itemHolderTemplate
                    ).apply { applyHorizontalContentAlignment = true }
        })
    }

    override val layoutResourceId: Int
        get() = R.layout.server_data_page

    override fun navigatedTo() {
        super.navigatedTo()
        viewModel.navigatedTo()
    }

    private fun itemDataTemplate(item: Device, holder: ItemViewHolder) {
        holder.bind(item)
    }

    private fun itemItemTemplate(): View {
        return layoutInflater.inflate(R.layout.device_item, null)
    }

    private fun itemHolderTemplate(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener({ itemView.ItemTextView.text = "click" })
        }

        fun bind(device: Device) {
            itemView.ItemTextView.text = device.name
        }
    }
}
