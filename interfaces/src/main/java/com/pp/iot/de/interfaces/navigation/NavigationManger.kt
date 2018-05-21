package com.pp.iot.de.interfaces.navigation

import com.pp.iot.de.models.enums.PageIndex

/**
 * Interface defining navigation methods used in app.
 */
interface NavigationManger {
    fun navigate(page : PageIndex)
    fun goBack()
    fun wentBack()
}