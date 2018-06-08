package com.dexterlearning.dexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Range;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout recommendedLayout;
    TableLayout libraryTableLayout;
    TextView tvUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        //TODO: Take user's name or username and other data to configure dashboard
        String userName = getIntent().getStringExtra("user");
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(userName);

        createRecommended();
        createPersonalLibrary();
    }

    private int toDp(int value){
        float factor = DashboardActivity.this.getResources().getDisplayMetrics().density;
        return (int)(value * factor);
    }

    private void createPersonalLibrary(){
        //TODO: Get course information from the database and replace courseUrls, courseNames and courseBadge
        List<String> courseUrls = Arrays.asList(new String[]{"https://www.dexterlearning.com/python", "https://www.dexterlearning.com/scratch",
                "https://www.dexterlearning.com/minecraft", "https://www.udemy.com/learn-and-master-computer-aided-design-cad-with-tinkercad/"});
        List<String> courseNames = Arrays.asList(new String[]{"Python", "Scratch", "Minecraft 3D Printing", "TinkerCAD"});

        TableRow row = null;
        TableRow.LayoutParams tParams = null;
        tParams = new TableRow.LayoutParams(toDp(110), toDp(130));
        int margin = toDp(10);
        tParams.setMargins(margin, margin, margin, margin);

        libraryTableLayout = (TableLayout) findViewById(R.id.libraryTableLayout);

        for(int i = 0; i < courseNames.size(); ++i){
            if((i % 3) == 0){
                row = new TableRow(DashboardActivity.this);
                LayoutParams params = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(params);
                libraryTableLayout.addView(row);
            }

            CardView courseItem = createCourseItem(courseNames.get(i), courseUrls.get(i), tParams);
            row.addView(courseItem);
        }

    }

    private void createRecommended(){
        recommendedLayout = (LinearLayout) findViewById(R.id.recommendedLayout);

        LayoutParams params = null;
        params = new LayoutParams(toDp(110), toDp(130));
        int margin = toDp(10);
        params.setMargins(margin, margin, margin, margin);

        for(int i = 0; i < 5; ++i){
            recommendedLayout.addView(createCourseItem("Course Title", "https://www.google.com", params));
        }
    }

    private CourseCardView createCourseItem(final String title, final String url, ViewGroup.LayoutParams params){
        Context context = DashboardActivity.this;
        CourseCardView courseItem = new CourseCardView(DashboardActivity.this, title, url);
        courseItem.setLayoutParams(params);
        return courseItem;

    }
}
