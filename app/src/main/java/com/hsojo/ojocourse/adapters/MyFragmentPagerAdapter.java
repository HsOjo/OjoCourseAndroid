package com.hsojo.ojocourse.adapters;


import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hsojo.ojocourse.R;
import com.hsojo.ojocourse.fragments.TableFragment;
import com.hsojo.ojocourse.fragments.PersonFragment;

import java.util.ArrayList;

/**
 * Created by goodtonny on 2020/3/26.
 */

public class MyFragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    private ArrayList<String> pages_title;
    private ArrayList<Fragment> fragments;

    public MyFragmentPagerAdapter(@NonNull androidx.fragment.app.FragmentManager fm, Resources resources) {
        super(fm);

        this.pages_title = new ArrayList<String>();
        this.pages_title.add(resources.getString(R.string.schedule));
        this.pages_title.add(resources.getString(R.string.person));

        this.fragments = new ArrayList<Fragment>();
        this.fragments.add(new TableFragment());
        this.fragments.add(new PersonFragment());
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.pages_title.get(position);
    }
}
