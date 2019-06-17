package com.tubipa.permissionhelper;

import android.app.Activity;
import android.content.Intent;

public class PermissionChecker {

    public static void check(Activity context, String[] permissions, PermissionListener permissionListener){
        PermissionProvider permissionProvider = new PermissionProvider(context, permissions);
        if (permissionProvider.checkPermission()){
            permissionListener.onPermissionGranted();
            return;
        }

        PermissionActivity.handler = permissionListener;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PermissionActivity.EXTRA_PERMISSIONS, permissions);
        context.startActivity(intent);
    }
}
