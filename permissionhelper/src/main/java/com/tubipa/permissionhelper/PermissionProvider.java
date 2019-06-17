package com.tubipa.permissionhelper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionProvider {

    static final int PERMISSION_CALLBACK_CONSTANT = 100;

    String[] permissionsRequired;
    Activity mContext;

    public PermissionProvider(@NonNull  Activity context, @NonNull String[] permissionsRequired) {
        this.permissionsRequired = permissionsRequired;
        this.mContext = context;
    }

    boolean checkPermission() {

        List<String> missingPermission = new ArrayList<>();
        for (String eachPermission : permissionsRequired) {
            if (ContextCompat.checkSelfPermission(mContext, eachPermission) != PackageManager.PERMISSION_GRANTED) {
                missingPermission.add(eachPermission);
            }
        }

        if (!missingPermission.isEmpty()) {
            ActivityCompat.requestPermissions(mContext, permissionsRequired ,PERMISSION_CALLBACK_CONSTANT);
            return false;
        }

        return true;
    }

    boolean shouldShowRequestPermissionRationale(){
        List<String> missingPermission = new ArrayList<>();
        for (String eachPermission : permissionsRequired) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, eachPermission)) {
                missingPermission.add(eachPermission);
                break;
            }
        }

        return !missingPermission.isEmpty();
    }
}
