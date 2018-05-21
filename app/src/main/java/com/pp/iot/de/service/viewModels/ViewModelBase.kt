package com.pp.iot.de.service.viewModels

import kotlin.reflect.KProperty

/**
 * Base class for all view models implementing simple property change notifications.
 * Code from https://github.com/MasuqaT-NET/BlogExamples/tree/master/Misc/kotlin-inotifypropertychanged
 */
abstract class ViewModelBase {

    /**
     * Class that could be used as delegate for bindable properties.
     * Calls {@link #raisePropertyChanged()} by default.
     */
    protected class RaisePropertyChangedDelegate<T>(initialValue: T) {

        private var value: T = initialValue

        operator fun setValue(thisRef: ViewModelBase, property: KProperty<*>, value: T) {
            this.value = value
            thisRef.raisePropertyChanged(property.name)
        }

        operator fun getValue(thisRef: ViewModelBase, property: KProperty<*>): T {
            return value
        }
    }

    /**
     * Base class for event calls.
     */
    open class EventArgs

    /**
     * Property changed notification container.
     */
    class PropertyChangedEventArgs(val propertyName: String = "") : EventArgs()

    /**
     * Class behaving like "event" it allows to subscribe delegates and invoke them with given arguments.
     */
    class EventHandler<TObj, TArg : EventArgs> {
        private val handlers = arrayListOf<(EventHandler<TObj, TArg>.(TObj, TArg) -> Unit)>()

        operator fun plusAssign(handler: EventHandler<TObj, TArg>.(TObj, TArg) -> Unit) {
            handlers.add(handler)
        }

        operator fun invoke(sender: TObj, e: TArg) {
            for (handler in handlers) {
                handler(sender, e)
            }
        }
    }

    /**
     * Storage of all registered handlers withing ViewModels.
     */
    companion object {
        private val eventHandlers: MutableMap<Int, EventHandler<Any, PropertyChangedEventArgs>> = mutableMapOf()
    }

    /**
     * Obtains handlers for given ViewModel instance.
     */
    val propertyChanged: EventHandler<Any, PropertyChangedEventArgs>
        get() = eventHandlers.getOrPut(hashCode(), { EventHandler() })

    /**
     * Use to notify View about changed property value.
     */
    fun raisePropertyChanged(propertyName: String) {
        propertyChanged(this, PropertyChangedEventArgs(propertyName));
    }
}