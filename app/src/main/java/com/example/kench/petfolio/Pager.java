package com.example.kench.petfolio;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
/**
 * Created by Quinatzin on 1/12/2018.
 */

public class Pager extends FragmentStatePagerAdapter{

    //Keep track of the tabs
    int tabCount;

    public Pager(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
            VaccineHistoryFragment tabVaccine = new VaccineHistoryFragment();
            return tabVaccine;
            case 1:
            MedHistoryFragment tabMed = new MedHistoryFragment();
            return tabMed;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
