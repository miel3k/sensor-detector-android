package com.pp.iot.de.interfaces.navigation

/**
 * Interface defining provider of pages for our application.
 */
interface PageProvider<TPage> {
    fun getPage() : TPage
}