package no.wtw.android.architectureutils.model;

import android.content.Context;

import androidx.annotation.DrawableRes;

public interface Listable {

    String getTitle(Context context);

    String getSubTitle(Context context);

    @DrawableRes
    int getIconResourceId(Context context);

}
