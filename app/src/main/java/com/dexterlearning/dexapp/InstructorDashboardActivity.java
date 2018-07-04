package com.dexterlearning.dexapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class InstructorDashboardActivity extends AppCompatActivity {

    private ViewPager vpDashboard;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("Dashboard");

        vpDashboard = (ViewPager) findViewById(R.id.vpDashboard);
        CustomPagerAdapter dpAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        dpAdapter.addFragment(DemoObjectFragment.newInstance("Home Fragment"), "Home");
        dpAdapter.addFragment(InstructorCoursesFragment.newInstance("Courses Fragment"), "Courses");
        dpAdapter.addFragment(DemoObjectFragment.newInstance("Random Fragment"), "Random");
        dpAdapter.addFragment(DemoObjectFragment.newInstance("Settings Fragment"), "Settings");
        vpDashboard.setAdapter(dpAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tlDashboardTabs);
        tabLayout.setupWithViewPager(vpDashboard);
        createTabIcon("Home", R.drawable.ic_home_white_24dp, 0);
        createTabIcon("Courses", R.drawable.ic_library_books_white_24dp, 1);
        createTabIcon("Help", R.drawable.ic_help_help_24dp, 2);
        createTabIcon("Settings", R.drawable.ic_settings_white_24dp, 3);
    }

        private void createTabIcon(String tabTitle, int drawable, int tabIndex) {
        TextView tab = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_custom1, null);
        tab.setText(tabTitle);
        tab.setCompoundDrawablesWithIntrinsicBounds(0, drawable, 0, 0);
        tabLayout.getTabAt(tabIndex).setCustomView(tab);
    }


    public static class DemoObjectFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_demo_layout, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.text1)).setText(
                    args.getString("msg"));
            return rootView;
        }

        public static Fragment newInstance(String title) {
            Fragment f = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putString("msg", title);
            f.setArguments(args);

            return f;
        }
    }
}
