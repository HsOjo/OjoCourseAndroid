package com.hsojo.ojocourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hsojo.ojocourse.adapters.ContentFragmentPagerAdapter;
import com.hsojo.ojocourse.models.UserModel;

public class MainActivity extends AppCompatActivity {
    public static UserModel login_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp_content = findViewById(R.id.vp_content);
        TabLayout tl_control = findViewById(R.id.tl_control);

        vp_content.setAdapter(new ContentFragmentPagerAdapter(this.getSupportFragmentManager(), this.getResources()));
        tl_control.setupWithViewPager(vp_content);
    }
}
