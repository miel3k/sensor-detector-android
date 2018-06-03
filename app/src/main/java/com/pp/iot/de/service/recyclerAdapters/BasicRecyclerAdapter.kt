package com.pp.iot.de.service.recyclerAdapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

class BasicRecyclerAdapter<TItem, THolder : RecyclerView.ViewHolder>(
        private val items: Iterable<TItem>,
        private val dataTemplate: (TItem, THolder) -> Any,
        private val itemTemplate: () -> View,
        private var holderTemplate: (View) -> THolder
) : RecyclerView.Adapter<THolder>() {
    var applyHorizontalContentAlignment: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): THolder {
        val view = itemTemplate()
        if (applyHorizontalContentAlignment) {
            view.layoutParams = RecyclerView.LayoutParams(-1, -2)
        }
        return holderTemplate(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: THolder, position: Int) {
        dataTemplate(items.elementAt(position), holder)
    }
}
