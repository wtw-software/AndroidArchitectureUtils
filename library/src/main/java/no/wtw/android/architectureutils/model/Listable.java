package no.wtw.android.architectureutils.model;

import android.content.Context;
import android.support.annotation.DrawableRes;

public interface Listable {

    String getTitle(Context context);

    String getSubTitle(Context context);

    @DrawableRes
    int getIconResourceId(Context context);

}
