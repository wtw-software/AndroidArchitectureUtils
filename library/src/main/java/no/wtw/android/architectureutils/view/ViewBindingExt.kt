package no.wtw.android.architectureutils.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> View.viewBinding() = CustomViewBindingDelegate(T::class.java)

class CustomViewBindingDelegate<T : ViewBinding>(private val bindingClass: Class<T>) : ReadOnlyProperty<View, T> {
    /**
     * initiate variable for binding view
     */
    private var viewBinding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: View, property: KProperty<*>): T {
        viewBinding?.let { return it }

        val inflatedView = try {
            val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            inflateMethod.invoke(null, LayoutInflater.from(thisRef.context), thisRef, true) as T
        } catch (e: NoSuchMethodException) {
            val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java)
            inflateMethod.invoke(null, LayoutInflater.from(thisRef.context), thisRef) as T
        }

        viewBinding = inflatedView
        return inflatedView
    }

}