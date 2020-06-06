package com.hsojo.ojocourse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.hsojo.ojocourse.models.UserModel;

public class SplashActivity extends Activity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, 600);
    }

    private void startApp() {
        UserModel user = new UserModel();
        if (user.loadData(getApplicationContext())) {
            MainActivity.login_user = user;
            this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        this.finish();
    }

}
