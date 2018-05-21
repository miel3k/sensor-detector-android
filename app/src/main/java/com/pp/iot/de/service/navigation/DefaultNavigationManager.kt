package com.pp.iot.de.service.navigation

import android.support.v4.app.FragmentManager
import android.view.View
import com.pp.iot.de.service.fragments.NavigationFragmentBase
import com.pp.iot.de.interfaces.navigation.NavigationManger
import com.pp.iot.de.interfaces.navigation.PageProvider
import com.pp.iot.de.models.enums.PageIndex


/**
 * Class responsible for performing navigation on fragment stack.
 */
class DefaultNavigationManager(
        private val pageDefinitions: Map<PageIndex, PageProvider<NavigationFragmentBase>>,
        private val rootView: View,
        private val fragmentManager: FragmentManager) : NavigationManger {

    override fun wentBack() {
        (fragmentManager.fragments.last() as NavigationFragmentBase).navigatedTo()
    }

    private var firstNavigation: Boolean = true

    override fun goBack() {
        fragmentManager.popBackStack()
        wentBack()
    }

    //TODO Add navigation arguments if needed.
    override fun navigate(page: PageIndex) {

        val fragmentPage = pageDefinitions[page]!!.getPage()
        val transaction = fragmentManager
                .beginTransaction()
                .replace(rootView.id, fragmentPage)

        //since we are navigating first time first fragment can't be removed,
        //it'd result in displaying empty backing activity when navigating back
        if (firstNavigation)
            firstNavigation = false
        else
            transaction.addToBackStack(null)

        transaction.commit()
        fragmentManager.executePendingTransactions()
        fragmentPage.navigatedTo()

    }
}