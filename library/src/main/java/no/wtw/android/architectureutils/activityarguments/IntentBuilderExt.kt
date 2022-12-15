package no.wtw.android.architectureutils.activityarguments

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass


fun <T : Activity> KClass<T>.intent(ctx: Context): IntentBuilder<T> {
    return IntentBuilder(ctx, null, this)
}

fun <T : Activity> KClass<T>.intent(fragment: Fragment): IntentBuilder<T> {
    return IntentBuilder(fragment.requireContext(), fragment, this)
}

class IntentBuilder<T : Activity>(
    private val ctx: Context,
    private val fragment: Fragment?,
    clazz: KClass<T>
) {

    val intent = Intent(ctx, clazz.java)

    fun putArguments(bundleData: BundleData): IntentBuilder<T> {
        intent.putArguments(bundleData)
        return this
    }

    fun addFlags(flags: Int): IntentBuilder<T> {
        intent.flags = intent.flags or flags
        return this
    }

    fun removeFlags(flags: Int): IntentBuilder<T> {
        intent.flags = intent.flags and flags.inv()
        return this
    }

    fun get() = intent
    fun start() = ctx.startActivity(intent)

    @Deprecated(message = "Deprecated", replaceWith = ReplaceWith("registerForActivityResult"))
    fun startForResult(requestCode: Int) {
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode)
        } else if (ctx is Activity) {
            ActivityCompat.startActivityForResult(ctx, intent, requestCode, null)
        } else {
            ctx.startActivity(intent)
        }
    }

}