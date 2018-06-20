package com.dexterlearning.dexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout recommendedLayout;
    LinearLayout libraryLayout;
    TextView tvUserName;
    TextView tvTitle;
    Toolbar toolbar;
    int width, height;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

         db = FirebaseFirestore.getInstance();

        //TODO: Take user's name or username and other data to configure dashboard
        String userName = getIntent().getStringExtra("user");
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        userName = "acrenshaw";
        tvUserName.setText(userName);

        //Test firebase firestore
        getUserData(userName);
        getUserCourses(userName);

        //TODO:Remove: will load courses dynamically from cloud stoarage pdfs
        List<String> courseUrls = Arrays.asList(new String[]{"https://www.dexterlearning.com/python", "https://www.dexterlearning.com/scratch",
                "https://www.dexterlearning.com/minecraft", "https://www.udemy.com/learn-and-master-computer-aided-design-cad-with-tinkercad/"});
        List<String> courseNames = Arrays.asList(new String[]{"Python", "Scratch", "Minecraft 3D Printing", "TinkerCAD"});

        createRecommended();
        createPersonalLibrary(courseNames, courseUrls);
    }

    private int toPixels(int value){
        float factor = DashboardActivity.this.getResources().getDisplayMetrics().density;
        return (int)(value * factor);
    }

    //TODO:Remove later. Don't need more user data in this view
    private void getUserData(String user) {
        DocumentReference docRef = db.collection("users")
                .document(user);
        Task<DocumentSnapshot> task = docRef.get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DexUser dUser = task.getResult().toObject(DexUser.class);

                        }else{
                            Log.e("Error: ", "Unable to get user data");
                        }
                    }
                }
        );

    }

    private void getUserCourses(String user){
        CollectionReference coursesRef = db.collection("users").document(user)
                .collection("courses");
        Task<QuerySnapshot> task = coursesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    ArrayList<Course> courses = new ArrayList<Course>();
                    Course course = null;
                    for (DocumentSnapshot d : task.getResult()) {
                        if (d.exists()) {
                            course = d.toObject(Course.class);
                            courses.add(course);

                            //TODO:Pass course information to function to create library
                        } else {
                            Log.e("Error: ", "Unable to get user course data");
                        }
                    }
                }else{
                    Log.e("Error: ", "Task unsuccessful");

                }
            }
        });

    }

    private void createPersonalLibrary(List<String> courseNames, List<String> courseUrls){
        libraryLayout = (LinearLayout) findViewById(R.id.libraryLayout);

        for(int i = 0; i < courseNames.size(); ++i){
            CardView courseItem = createCourseItem(courseNames.get(i), courseUrls.get(i));
            libraryLayout.addView(courseItem);

        }
    }


    private void createRecommended(){
        recommendedLayout = (LinearLayout) findViewById(R.id.recommendedLayout);

        for(int i = 0; i < 5; ++i){
            recommendedLayout.addView(createCourseItem("Course Title AND There she blows down the hole",
                    "https://www.google.com"));
        }
    }

    private CourseCardView createCourseItem(final String title, final String url){
        Context context = DashboardActivity.this;
        CourseCardView courseItem = new CourseCardView(DashboardActivity.this, title, url);

        return courseItem;

    }
}
