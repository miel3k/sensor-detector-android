package com.pp.iot.de.service.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class NavigationFragmentBase : Fragment() {

    private var rootView: View? = null

    /**
     * Describes layout that will be inflated.
     */
    abstract val layoutResourceId: Int

    /**
     * Called once in order to create bindings after view has been inflated.
     */
    abstract fun initBindings()

    /**
     * Override in order to obtain notification about navigating to this page.
     */
    open fun navigatedTo() {

    }

    override fun getContext(): Context? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return activity
        return super.getContext()
    }

    //For kotlin view "properties" generation
    override fun getView(): View? {
        return rootView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(layoutResourceId, container, false)
            initBindings()
        }

        return rootView
    }
}