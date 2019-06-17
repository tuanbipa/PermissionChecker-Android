package com.tubipa.permissionhelper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PermissionActivity extends AppCompatActivity {

    static final String EXTRA_PERMISSIONS = "permissions";

    PermissionProvider permissionProvider;
    static PermissionListener handler;

    Button btGrant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra(EXTRA_PERMISSIONS)){
            finish();
            return;
        }

        String[] permissionsRequired = intent.getStringArrayExtra(EXTRA_PERMISSIONS);

        btGrant = findViewById(R.id.btGrant);
        btGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isHasPermission()){
                    return;
                }
                proceedAfterPermission();
            }
        });

        permissionProvider = new PermissionProvider(this, permissionsRequired);
    }

    private boolean isHasPermission(){
        return permissionProvider.checkPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PermissionProvider.PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean all_granted = false;

            for (int grant : grantResults){
                if(grant== PackageManager.PERMISSION_GRANTED){
                    all_granted = true;
                } else {
                    all_granted = false;
                    break;
                }
            }

            if(all_granted){
                proceedAfterPermission();
                return;
            }

            if( permissionProvider.shouldShowRequestPermissionRationale()) {
                permissionProvider.checkPermission();
                return;
            }
            showSnackbar("Unable to get permissions. Please open settings to grant permissions");
        }
    }

    private void showSnackbar(String msg){
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG).show();
    }

    //region business
    private void proceedAfterPermission() {
        finish();
        if (handler != null){
            handler.onPermissionGranted();
        }
    }

}
