package no.wtw.android.architectureutils.activityarguments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlin.reflect.KClass
import kotlin.reflect.cast

const val SAVED_INSTANCE_STATE = "saved_instance_state"
const val ARGUMENTS = "activity_arguments"

fun <T : BundleData> Intent.putArguments(args: T, key: String = ARGUMENTS): Intent {
    putExtra(key, args)
    return this
}

fun <T : BundleData> Intent.getArguments(clazz: KClass<T>, key: String = ARGUMENTS): T {
    return extras?.let { bundle ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getBundleDataTiramisu(key, clazz)
        } else {
            bundle.getBundleDataCompat(key, clazz)
        }
    } ?: throw IllegalArgumentException("Extras is null")
}

fun <T : BundleData> Bundle.putSavedState(args: T, key: String = SAVED_INSTANCE_STATE): Bundle {
    putSerializable(key, args)
    return this
}

inline fun <reified T : BundleData> Bundle.getSavedState(key: String = SAVED_INSTANCE_STATE): T? {
    return getSavedStateWithExplicitKClass(T::class, key)
}

fun <T : BundleData> Bundle.getSavedStateWithExplicitKClass(clazz: KClass<T>, key: String): T? {
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getBundleDataTiramisu(key, clazz)
        } else {
            getBundleDataCompat(key, clazz)
        }
    } catch (e: Exception) {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun <T : BundleData> Bundle.getBundleDataTiramisu(key: String, clazz: KClass<T>): T {
    return getSerializable(key, clazz.java)
        ?: throw IllegalArgumentException("Arguments are null or of wrong type. Expected " + clazz.java)
}

@Suppress("DEPRECATION")
fun <T : BundleData> Bundle.getBundleDataCompat(extraName: String, clazz: KClass<T>): T {
    val arguments = getSerializable(extraName)
    if (arguments != null) {
        if (clazz.isInstance(arguments))
            return clazz.cast(arguments)
        else
            throw IllegalArgumentException("Expected arguments of type " + clazz.java + ", but was " + arguments.javaClass)
    }
    throw IllegalArgumentException("Arguments are null, expected " + clazz.java)
}

