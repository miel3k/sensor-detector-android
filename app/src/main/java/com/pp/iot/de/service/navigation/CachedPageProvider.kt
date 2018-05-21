package com.pp.iot.de.service.navigation

import android.support.v4.app.Fragment
import com.pp.iot.de.interfaces.navigation.PageProvider

/**
 *  Page provider which caches given fragment instance.
 */
class CachedPageProvider<TPage : Fragment>(private val pageFactory: () -> (TPage))
    : PageProvider<TPage> {
    private var page: TPage? = null

    override fun getPage(): TPage {
        if (page == null)
            page = pageFactory()
        return page as TPage
    }
}