package com.example.kench.petfolio;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.kench.petfolio.data.VaccineContract;

import static com.example.kench.petfolio.R.color.colorAccent;

/**
 * Created by kench on 1/12/2018.
 */


public class MainMedLog extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        // mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //Tab id
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        // add the tab
        mTabLayout.addTab(mTabLayout.newTab().setText("Vaccine Log"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Med"));
        //mTabLayout.addTab(mTabLayout.newTab().setText("Tab3"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        //mViewPager.setAdapter(mSectionsPagerAdapter);
        Pager adapter = new Pager(getSupportFragmentManager(), mTabLayout.getTabCount());
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

        //  TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        // tabLayout.setupWithViewPager(mViewPager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMedLog.this, VaccineEditorActivity.class);
                startActivity(intent);

            }
        });
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    Log.v("Tab 1", "Use new Fab function here.");
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                    //fab.setBackgroundTintList(ColorStateList.valueOf(fab.getColor()));
                } else if (tab.getPosition() == 1) {
                    Log.v("Tab 2", "Use new Fab function here.");
                    //fab.setBackgroundColor(Color.BLACK);
                    //fab.setColorFilter(Color.BLACK);
                    //fab.setBackgroundColor(Color.BLUE);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainMedLog.this, MedEditorActivity.class);
                            startActivity(intent);

                        }
                    });
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMedLog.this, MedEditorActivity.class);
                startActivity(intent);

            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vaccine, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.insert_dummy) {
        //  return true;
        //}
        switch (item.getItemId()) {
            case R.id.insert_dummy:
                //Log.v("VaccineHistoryFragment.class", "Insert");
                insertVaccineLog();
                return true;
            case R.id.delete_all:
                deleteAll();
                return true;
        }
        //return super.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Helper method to insert dummy data into database
     */
    private void insertVaccineLog() {
        //Create a ContentValue object where column names are key
        ContentValues contentValues = new ContentValues();
        contentValues.put(VaccineContract.VaccineEntry.COLUMN_VACCINE_DATE, "09-29-2016");
        contentValues.put(VaccineContract.VaccineEntry.COLUMN_VACCINE_INFO, "DHLPP/C 1");
        contentValues.put(VaccineContract.VaccineEntry.COLUMN_VACCINE_TAG, "N/A");
        contentValues.put(VaccineContract.VaccineEntry.COLUMN_VACCINE_REDATE, "10-13-2016");

        //Uri newUri = (getContext().getContentResolver().insert(VaccineEntry.CONTENT_URI, contentValues));
        Uri newUri = getContentResolver().insert(VaccineContract.VaccineEntry.CONTENT_URI, contentValues);

    }

    private void deleteAll() {
        int rowsDeleted = getContentResolver().delete(VaccineContract.VaccineEntry.CONTENT_URI, null, null);
        Log.v(" MedHistoryActivity ", rowsDeleted + " rows deleted from vaccineLog database");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
