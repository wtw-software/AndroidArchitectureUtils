package no.wtw.android.architectureutils.view

import android.view.View
import android.view.View.OnAttachStateChangeListener
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type.displayCutout
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import androidx.core.view.WindowInsetsCompat.Type.tappableElement
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

// https://medium.com/androiddevelopers/windowinsets-listeners-to-layouts-8f9ccc8fa4d1

fun <T : View> T.doOnApplyWindowInsets(
    callback: (view: T, insets: Insets, padding: Padding, margin: Margin) -> Unit,
) {
    doOnApplyWindowInsets(typeMask = ime() or statusBars() or tappableElement() or displayCutout() or navigationBars(), callback = callback)
}

fun <T : View> T.doOnApplyWindowInsets(
    typeMask: Int = ime() or statusBars() or tappableElement() or displayCutout() or navigationBars(),
    callback: (view: T, insets: Insets, padding: Padding, margin: Margin) -> Unit,
) {
    val margin = Margin(marginLeft, marginTop, marginRight, marginBottom)
    val padding = Padding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        val internalInsets = insets.getInsets(typeMask)

        @Suppress("UNCHECKED_CAST")
        val vAsT = v as T
        callback(vAsT, internalInsets, padding, margin)
        insets
    }
    requestApplyInsetsWhenAttached()
}

data class Padding(val left: Int, val top: Int, val right: Int, val bottom: Int)
data class Margin(val left: Int, val top: Int, val right: Int, val bottom: Int)

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}