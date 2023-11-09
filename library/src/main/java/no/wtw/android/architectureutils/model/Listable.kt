package no.wtw.android.architectureutils.model

import android.content.Context
import androidx.annotation.DrawableRes

interface Listable {
    fun getTitle(context: Context?): String?
    fun getSubTitle(context: Context?): String?

    @DrawableRes
    fun getIconResourceId(context: Context?): Int
}