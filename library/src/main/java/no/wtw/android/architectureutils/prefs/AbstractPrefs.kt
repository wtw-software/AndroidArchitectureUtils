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

    fun putStringPref(@StringRes keyRes: Int, value: String) =
        sharedPreferences.edit().putString(ctx.getString(keyRes), value).apply()


    fun getBooleanPref(@StringRes keyRes: Int): Boolean? =
        getBooleanPref(ctx.getString(keyRes))

    fun getBooleanPref(key: String): Boolean? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getBoolean(key, false)
        else
            null

    fun putBooleanPref(@StringRes keyRes: Int, value: Boolean) =
        sharedPreferences.edit().putBoolean(ctx.getString(keyRes), value).apply()


    fun getIntPref(@StringRes keyRes: Int): Int? =
        getIntPref(ctx.getString(keyRes))

    fun getIntPref(key: String): Int? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getInt(key, 0)
        else
            null

    fun putIngPref(@StringRes keyRes: Int, value: Int) =
        sharedPreferences.edit().putInt(ctx.getString(keyRes), value).apply()


    fun getLongPref(@StringRes keyRes: Int): Long? =
        getLongPref(ctx.getString(keyRes))

    fun getLongPref(key: String): Long? =
        if (sharedPreferences.contains(key))
            sharedPreferences.getLong(key, 0L)
        else
            null

    fun putLongPref(@StringRes keyRes: Int, value: Long) =
        sharedPreferences.edit().putLong(ctx.getString(keyRes), value).apply()


    fun getStringRes(@StringRes resId: Int) =
        ctx.resources.getString(resId)

    fun getBooleanRes(@BoolRes resId: Int) =
        ctx.resources.getBoolean(resId)

    fun getIntegerRes(@IntegerRes resId: Int) =
        ctx.resources.getInteger(resId)

}