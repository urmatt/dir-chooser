package com.urmapps.dirchoose;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class TemplateActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CHOOSE_FILE = 100;
    public static final int REQUEST_CODE_WRITE_PERMISSION = 101;

    public void message(String message) {
        message(message, Toast.LENGTH_SHORT);
    }

    public void message(String message, int length) {
        Toast.makeText(this, message, length).show();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_PERMISSION);
                return false;
            }
        } else {
            return true;
        }
    }
}
