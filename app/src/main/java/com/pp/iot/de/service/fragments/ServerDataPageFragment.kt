package com.pp.iot.de.service.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pp.iot.de.models.model.ExampleMeasurement
import com.pp.iot.de.service.R
import com.pp.iot.de.service.recyclerAdapters.BasicRecyclerAdapter
import com.pp.iot.de.service.viewModels.ServerDataViewModel
import kotlinx.android.synthetic.main.example_list_page_item.view.ExampleItem
import kotlinx.android.synthetic.main.server_data_page.ItemsRecyclerView
import kotlinx.android.synthetic.main.server_data_page.ShowDataButton
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class ServerDataPageFragment : FragmentBase<ServerDataViewModel>(ServerDataViewModel::class.java) {
    override fun initBindings() {
        ShowDataButton.setOnClickListener { async(UI) { viewModel.getMeasurement() } }

        ItemsRecyclerView.layoutManager = LinearLayoutManager(context)
        bindings.add(viewModel::exampleMeasurementsList.subscribeOneWay {
            ItemsRecyclerView.adapter =
                    BasicRecyclerAdapter(
                            viewModel.exampleMeasurementsList,
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

    private fun itemDataTemplate(item: ExampleMeasurement, holder: ItemViewHolder) {
        holder.bind(item)
    }

    private fun itemItemTemplate(): View {
        return layoutInflater.inflate(R.layout.example_list_page_item, null)
    }

    private fun itemHolderTemplate(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bind(exampleMeasurement: ExampleMeasurement) {
            itemView.ExampleItem.text = exampleMeasurement.measurement.toString()
        }
    }

}
