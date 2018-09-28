package com.example.sauravrp.listings.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

public class IntentHelper {

    static public void launchPhone(Context ctx, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        launchActivity(ctx, intent);
    }

    static public void launchMaps(Context ctx, String title, String street, String city, String state) {
         Uri uri = Uri.parse("https://www.google.com/maps/search/?api=1"
                + "&" + "query="
                 + title
                 + " ,"
                 + street
                + ","
                + city
                + ","
                + state);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        launchActivity(ctx, intent);
    }

    static public void launchActivity(Context ctx, Intent intent) {
        PackageManager pm = ctx.getPackageManager();
        if(intent.resolveActivity(pm) != null) {
            ctx.startActivity(intent);
        }
    }

   static public Intent getSettingsIntent(String packageName)  {

       Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
        } else {
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
