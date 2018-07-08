package com.dexterlearning.dexapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class CourseActivity extends AppCompatActivity
    implements StudentListFragment.OnFragmentInteractionListener{

    private TextView tvTitle;
    private ViewPager vpCourseView;
    private TabLayout tabLayout;
    private CustomPagerAdapter cVAdapter;

    private String [] students = new String []{"John Doe", "Mya Komeada",
            "Alexis Voltair", "Caroline Wilhelmina", "Osmosis Jones",
            "Caleb Brown", "Alice Nano", "Sir Victoreeem", "Sakure The Beautifuru",
            "Snoop Dog", "G Lite 10", "Mr.Tough Guy", "Mr. Nice Guy"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getStringExtra("courseTitle");
        if(title == null){
            title = "Course View";
        }
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        vpCourseView = (ViewPager) findViewById(R.id.vpCourseView);
        cVAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        cVAdapter.addFragment(CourseContentFragment.newInstance("Content Fragment"), "Content");
        cVAdapter.addFragment(CourseContentFragment.newInstance("Notes Fragment"), "Notes");
        cVAdapter.addFragment(StudentsFragment.newInstance("Students Fragment"), "Students");
        cVAdapter.addFragment(CourseContentFragment.newInstance("Forum Fragment"), "Forum");
        vpCourseView.setAdapter(cVAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tlDashboardTabs);
        tabLayout.setupWithViewPager(vpCourseView);

        createTabIcon("Content", R.drawable.ic_library_books_white_24dp, 0);
        createTabIcon("Notes", R.drawable.ic_note_white_24dp, 1);
        createTabIcon("Students", R.drawable.ic_group_white_24dp, 2);
        createTabIcon("Forum", R.drawable.ic_forum_white_24dp, 3);


    }

    public void onFragmentInteraction(int position) {
        //TODO:Change data on student profile to the correct student and show profile
        //Read student's data from firestore
        //Populate student profile fragment with data

        TextView tvProfileName = (TextView) findViewById(R.id.tvUserName);
        tvProfileName.setText(students[position]);

        if (findViewById(R.id.activity_course_port) != null) {
            FragmentManager manager = getSupportFragmentManager();

            for(Fragment f : manager.getFragments()){
                LabeledFragment lFragment = (LabeledFragment) f;
                if((lFragment.getLabel() == "STUDENTS_FRAG")){

                    FragmentManager childManager = lFragment
                            .getChildFragmentManager();

                    childManager.beginTransaction()
                            .hide(childManager.findFragmentById(R.id.sl_frag))
                            .show(childManager.findFragmentById(R.id.sp_frag))
                            .addToBackStack(null)
                            .commit();
                    return;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
       for(Fragment f : getSupportFragmentManager().getFragments()){
           if(f.isVisible()){
               FragmentManager childManager = f.getChildFragmentManager();
               if(childManager.getBackStackEntryCount() > 0){
                    childManager.popBackStack();
                    return;
               }
           }
        }

        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
    }

    private void createTabIcon(String tabTitle, int drawable, int tabIndex) {
        TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_custom1, null);
        tab.setText(tabTitle);
        tab.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0);
        tabLayout.getTabAt(tabIndex).setCustomView(tab);
    }

/*
    public static boolean isConnected(Context context){
        ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(cManager != null){
            NetworkInfo info = cManager.getActiveNetworkInfo();

            return(info != null && info.isConnected());
        }
        return false;
    }
    */

}
