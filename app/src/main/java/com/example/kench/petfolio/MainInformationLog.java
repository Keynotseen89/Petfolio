package com.example.kench.petfolio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Quinatzin on 1/19/2018.
 */

public class MainInformationLog extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /* public MainInformationLog() {
     }
 */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        // TODO add information toolbar in information_history.xml
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_information);

        //Tab id
        mTabLayout = (TabLayout) findViewById(R.id.tabsInformation);
        mTabLayout.setupWithViewPager(mViewPager);

        // add the tab
        mTabLayout.addTab(mTabLayout.newTab().setText("Information"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Setup  the ViewPager with the selection adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_Information);
        PagerInformation adapter = new PagerInformation(getSupportFragmentManager(), mTabLayout.getTabCount());
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setScrollPosition(position, 0, true);
                mTabLayout.setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    Log.v("Tab 1", "action goes here");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * A Placeholder fragment containing a simple view
     */
    public static class PlaceholderInformationFragment extends Fragment {
        /**
         * The Fragment argument representing the section number for this fragment
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderInformationFragment() {

        }

        /**
         * Return a new instance of this fragment for the given section
         */
        public static PlaceholderInformationFragment newInstance(int sectionNum) {
            PlaceholderInformationFragment fragment = new PlaceholderInformationFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNum);
            fragment.setArguments(args);

            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hello World");

            return rootView;
            //return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int postion) {
            return PlaceholderInformationFragment.newInstance(postion + 1);
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
