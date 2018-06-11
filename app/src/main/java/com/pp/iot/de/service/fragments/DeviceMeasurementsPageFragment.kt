package com.pp.iot.de.service.fragments

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.pp.iot.de.models.model.DeviceMeasurement
import com.pp.iot.de.service.R
import com.pp.iot.de.service.recyclerAdapters.BasicRecyclerAdapter
import com.pp.iot.de.service.viewModels.DeviceMeasurementsViewModel
import kotlinx.android.synthetic.main.device_item.view.*
import kotlinx.android.synthetic.main.measurement_item.view.*
import kotlinx.android.synthetic.main.server_data_page.*

class DeviceMeasurementsPageFragment : FragmentBase<DeviceMeasurementsViewModel>(DeviceMeasurementsViewModel::class.java) {
    override fun initBindings() {
        MeasurementsList.layoutManager = LinearLayoutManager(context)
        bindings.add(viewModel::measurementsList.subscribeOneWay {
            MeasurementsList.adapter =
                    BasicRecyclerAdapter(
                            viewModel.measurementsList,
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

    private fun itemDataTemplate(item: DeviceMeasurement, holder: ItemViewHolder) {
        holder.bind(item)
    }

    private fun itemItemTemplate(): View {
        return layoutInflater.inflate(R.layout.measurement_item, null)
    }

    private fun itemHolderTemplate(view: View): ItemViewHolder {
        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(measurement: DeviceMeasurement) {
            var tmp = measurement.measurementType + ' ' + measurement.measurement + ' ' + measurement.unit
            itemView.measurement.text = tmp
            tmp = measurement.timestamp.replace('T',' ')
            itemView.timestamp.text = tmp
        }
    }
}
