package com.example.ollin.rollinitiativev2.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapterFragments extends FragmentPagerAdapter {

    ArrayList<Fragment> pages = new ArrayList<>();
    public PagerAdapterFragments(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        return pages.get(i);
    }


    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).toString();
    }

    public void addPage(Fragment fragment){
        pages.add(fragment);
    }
}
