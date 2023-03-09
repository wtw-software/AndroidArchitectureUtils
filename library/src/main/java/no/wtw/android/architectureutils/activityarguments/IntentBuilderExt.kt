package no.wtw.android.architectureutils.activityarguments

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import kotlin.reflect.KClass


fun <T : Activity> KClass<T>.intent(ctx: Context): IntentBuilder<T> {
    return IntentBuilder(ctx, null, this)
}

fun <T : Activity> KClass<T>.intent(fragment: Fragment): IntentBuilder<T> {
    return IntentBuilder(fragment.requireContext(), fragment, this)
}
