package no.wtw.android.architectureutils.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.BoolRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

abstract class AbstractPrefs(
    val ctx: Context,
    private val sharedPreferences: SharedPreferences
) {

    fun getStringPref(@StringRes keyRes: Int): String? =
        getStringPref(ctx.getString(keyRes))

    fun getStringPref(key: String): String? =
        sharedPreferences.getString(key, null)

    fun putStringPref(@StringRes keyRes: Int, value: String?) =
        putStringPref(ctx.getString(keyRes), value)

    fun putStringPref(key: String, value: String?) =
        sharedPreferences.edit().putString(key, value).apply()


    fun getBooleanPref(@StringRes keyRes: Int): Boolean? =
        getBooleanPref(ctx.getString(keyRes))

    fun getBooleanPref(key: String): Boolean? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getBoolean(key, false)
        else
            null

    fun putBooleanPref(@StringRes keyRes: Int, value: Boolean?) =
        putBooleanPref(ctx.getString(keyRes), value)

    fun putBooleanPref(key: String, value: Boolean?) =
        if (value == null)
            sharedPreferences.edit().remove(key).apply()
        else
            sharedPreferences.edit().putBoolean(key, value).apply()


    fun getIntPref(@StringRes keyRes: Int): Int? =
        getIntPref(ctx.getString(keyRes))

    fun getIntPref(key: String): Int? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getInt(key, 0)
        else
            null

    fun putIntPref(@StringRes keyRes: Int, value: Int?) =
        putIntPref(ctx.getString(keyRes), value)

    fun putIntPref(key: String, value: Int?) =
        if (value == null)
            sharedPreferences.edit().remove(key).apply()
        else
            sharedPreferences.edit().putInt(key, value).apply()


    fun getLongPref(@StringRes keyRes: Int): Long? =
        getLongPref(ctx.getString(keyRes))

    fun getLongPref(key: String): Long? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getLong(key, 0L)
        else
            null

    fun putLongPref(@StringRes keyRes: Int, value: Long) =
        putLongPref(ctx.getString(keyRes), value)

    fun putLongPref(key: String, value: Long?) =
        if (value == null)
            sharedPreferences.edit().remove(key).apply()
        else
            sharedPreferences.edit().putLong(key, value).apply()


    fun getStringRes(@StringRes resId: Int) =
        ctx.resources.getString(resId)

    fun getBooleanRes(@BoolRes resId: Int) =
        ctx.resources.getBoolean(resId)

    fun getIntegerRes(@IntegerRes resId: Int) =
        ctx.resources.getInteger(resId)

}