package com.pp.iot.de.service.fragments

import android.os.Bundle
import com.github.salomonbrys.kodein.TT
import com.pp.iot.de.service.App
import com.pp.iot.de.service.viewModels.ViewModelBase
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlin.reflect.KProperty

/**
 * Base class for all fragments used in app.
 * Provides ViewModel instance and streamlines fragment lifecycle.
 */
abstract class FragmentBase<TViewModel : ViewModelBase>(private val classToken: Class<TViewModel>) : NavigationFragmentBase() {

    protected lateinit var viewModel: TViewModel

    protected val bindings: MutableList<Disposable> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = App.kodeinContainer.Instance(TT(classToken))
        super.onCreate(savedInstanceState)
    }

    /**
     * Creates simple binding for given property. Consumer gets invoked whenever property's value changes.
     */
    fun <T> createBinding(property: KProperty<T>): Observable<T> {
        return Observable.fromPublisher({
            viewModel.propertyChanged += { _, e ->
                if (e.propertyName == property.name)
                    it.onNext(property.call())
            }
        })
    }

    /**
     * Creates one way binding and invokes update after subscription.
     */
    protected inline fun <reified T>
            KProperty<T>.subscribeOneWay(noinline binding: (T) -> Unit): Disposable {
        val subscription = createBinding(this).subscribe(binding)
        binding(this.call())
        return subscription
    }
}
