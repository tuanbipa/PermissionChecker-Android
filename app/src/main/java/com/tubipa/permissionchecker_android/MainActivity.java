package com.tubipa.permissionchecker_android;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tubipa.permissionhelper.PermissionChecker;
import com.tubipa.permissionhelper.PermissionListener;

public class MainActivity extends AppCompatActivity {

    String[] permissionsRequired = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
    };

    Button btGrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btGrant = findViewById(R.id.btGrant);
        btGrant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PermissionChecker.check(MainActivity.this, permissionsRequired, new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(MainActivity.this, "Granted", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
