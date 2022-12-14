package no.wtw.android.architectureutils.activityarguments

import android.app.Activity
import java.io.Serializable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

inline fun <reified T : BundleData> activityArguments() = ActivityArgumentsDelegate(T::class)

class ActivityArgumentsDelegate<T : BundleData>(private val clazz: KClass<T>) : ReadOnlyProperty<Activity, T> {

    private var arguments: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        arguments?.let { return it }
        return thisRef.intent.getArguments(clazz).also { arguments = it }
    }

}

open class BundleData : Serializable