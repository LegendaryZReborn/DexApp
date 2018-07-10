package com.dexterlearning.dexapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> fragmentTitleList;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentTitleList = new ArrayList<String>();
    }

    @Override
    public Fragment getItem(int position) {
        int size = fragmentArrayList.size();

        if(size == 0) return null;
        else if(position < size && position > -1) return fragmentArrayList.get(position);
        else return null;
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment pagerFragment, String title) {
        fragmentArrayList.add(pagerFragment);
        fragmentTitleList.add(title);

    }
}
