package com.pp.iot.de.service.recyclerAdapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

class CollectionAdapter<T>(
        private val collection: Iterable<T>,
        private val templateDelegate: (position: Int, convertView: View?) -> View
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return templateDelegate(position, convertView)
    }

    override fun getItem(position: Int): T {
        return collection.elementAt(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return collection.count()
    }
}
