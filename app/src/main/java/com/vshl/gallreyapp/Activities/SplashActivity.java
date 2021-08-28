package com.vshl.gallreyapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.vshl.gallreyapp.Key;
import com.vshl.gallreyapp.Preferences;
import com.vshl.gallreyapp.R;

public class SplashActivity extends AppCompatActivity {
    boolean IS_LOGGED_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        IS_LOGGED_IN = Preferences.getBoolean(SplashActivity.this, Key.IS_LOGGED_IN);

        if (IS_LOGGED_IN) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    finish();

                    checkPerm();
                }
            }, 1000);

        } else {




            new Handler().postDelayed(new Runnable() {
                public void run() {
                    checkPerm();
                }
            }, 1000);
        }


    }


    public void checkPerm() {

        if (IS_LOGGED_IN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (IS_LOGGED_IN) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();

                    } else {

                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }


                } else {
                    checkPerm();
                }
                return;
            }

        }
    }
}