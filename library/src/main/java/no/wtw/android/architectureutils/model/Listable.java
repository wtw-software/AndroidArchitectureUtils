package no.wtw.android.architectureutils.model;

import android.support.annotation.DrawableRes;

public interface Listable {

    String getTitle();

    String getSubTitle();

    @DrawableRes
    int getIconResourceId();

}
