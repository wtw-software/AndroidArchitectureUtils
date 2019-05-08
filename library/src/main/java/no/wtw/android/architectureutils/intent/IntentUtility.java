package no.wtw.android.architectureutils.intent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

public class IntentUtility {

    public static Intent getLinkIntent(@NonNull String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(link));
        return intent;
    }

    public static Intent getNotificationSettingIntent(Context context) {
        Intent intent = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
        }
        return intent;
    }

    public static Intent getAppIntent(Context context, String packageId) throws Exception {
        try {
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageId);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("market://details?id=" + packageId));
            }
            return intent;
        } catch (Exception e) {
            throw new Exception("Missing package");
        }
    }

    public static boolean isPackageInstalled(Context context, String packageId) {
        try {
            context.getPackageManager().getPackageInfo(packageId, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static Intent getInternetSettingsIntent(Context context) {
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY);
*/
        return null;
    }

}
