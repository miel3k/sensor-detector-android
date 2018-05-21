package com.pp.iot.de.service

import com.pp.iot.de.service.businessLogic.DefaultApiCommunicator
import com.pp.iot.de.service.viewModels.DashboardViewModel
import com.pp.iot.de.service.viewModels.ViewModelBase
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

/**
 * Test class for various binding scenarios.
 */
class BindingsUnitTests {

    @Test
    fun `test whether delegated properties are working properly`() {
        //given
        val vm = DashboardViewModel(DefaultApiCommunicator(), mockk())
        var eventArgs: ViewModelBase.PropertyChangedEventArgs? = null
        vm.propertyChanged += { _, e ->
            eventArgs = e
        }
        //when
        vm.boundText = "test"
        //then
        Assert.assertTrue(eventArgs!!.propertyName == vm::boundText.name && vm.boundText == "test")
    }
}
