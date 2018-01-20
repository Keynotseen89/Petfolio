package com.example.kench.petfolio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Quinatzin on 1/19/2018.
 */

public class PagerInformation extends FragmentStatePagerAdapter {

    //Keep track of the tab
    int tabCount;

    public PagerInformation(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Log.v("PagerInformation", "This tab is working");
                InformationFragment infoTab = new InformationFragment();
                return infoTab;
                //return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
