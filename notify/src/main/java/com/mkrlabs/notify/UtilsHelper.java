package com.mkrlabs.notify;

import android.content.Context;

import androidx.annotation.NonNull;

public class UtilsHelper {

    public static String getStringResourceByKey(@NonNull Context context, @NonNull String resourceKey) {
        int resId = context.getResources().getIdentifier(resourceKey, "string", context.getPackageName());
        return context.getResources().getString(resId);
    }
}