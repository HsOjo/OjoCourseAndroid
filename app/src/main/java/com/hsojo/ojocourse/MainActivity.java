package com.hsojo.ojocourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hsojo.ojocourse.adapters.MyFragmentPagerAdapter;
import com.hsojo.ojocourse.beans.UserBean;
import com.hsojo.ojocourse.services.OjoCourseService;

public class MainActivity extends AppCompatActivity {
    public static UserBean login_user;
    OjoCourseService.User service_user;
    OjoCourseService.Course service_course;
    private ViewPager vp_content;
    private TabLayout tl_control;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.service_user = OjoCourseService.generateUser();
        this.service_course = OjoCourseService.generateCourse();

        this.vp_content = findViewById(R.id.vp_content);
        this.tl_control = findViewById(R.id.tl_control);

        vp_content.setAdapter(new MyFragmentPagerAdapter(this.getSupportFragmentManager(), this.getResources()));
        tl_control.setupWithViewPager(vp_content);
    }
}
