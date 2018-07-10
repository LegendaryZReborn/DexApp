package com.dexterlearning.dexapp.activities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dexterlearning.dexapp.models.Course;
import com.dexterlearning.dexapp.models.CourseCardView;
import com.dexterlearning.dexapp.models.DexUser;
import com.dexterlearning.dexapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    LinearLayout recommendedLayout;
    LinearLayout libraryLayout;
    TextView tvUserName;
    TextView tvTitle;
    Toolbar toolbar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //TODO:Save username so it persists between activities
        String userName = getIntent().getStringExtra("user");
        if(userName == null)
            userName = "null";
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvUserName.setText(userName);

        //Test firebase firestore
        db = FirebaseFirestore.getInstance();
        getUserData(userName);
        getUserCourses(userName);

        //TODO:Remove: will load courses dynamically from cloud stoarage pdfs
       /* List<String> courseUrls = Arrays.asList(new String[]{"https://www.dexterlearning.com/python", "https://www.dexterlearning.com/scratch",
                "https://www.dexterlearning.com/minecraft", "https://www.udemy.com/learn-and-master-computer-aided-design-cad-with-tinkercad/"});
        List<String> courseNames = Arrays.asList(new String[]{"Python", "Scratch", "Minecraft 3D Printing", "TinkerCAD"});
*/
        getUsersRecommended();
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
                        } else {
                            Log.e("Error: ", "Unable to get user course data");
                        }
                    }
                    //TODO:Pass course information to function to create library
                    libraryLayout = (LinearLayout) findViewById(R.id.libraryLayout);
                    for(int i = 0; i < courses.size(); ++i){
                        CardView courseItem = createCourseItem(courses.get(i));
                        libraryLayout.addView(courseItem);
                    }

                }else{
                    Log.e("Error: ", "Task unsuccessful");

                }
            }
        });

    }

    //TODO:create algorithm to selected recommended courses in order of most to least
    private void getUsersRecommended(){
        recommendedLayout = (LinearLayout) findViewById(R.id.recommendedLayout);
        for(int i = 0; i < 5; ++i){
            Course course = new Course("New Course", "");
            CourseCardView courseItem = createCourseItem(course);
            recommendedLayout.addView(courseItem);
        }
    }

    private CourseCardView createCourseItem(Course course){
        Context context = DashboardActivity.this;
        CourseCardView courseItem = new CourseCardView(DashboardActivity.this, course.getName(), course.getPdf());

        return courseItem;

    }

}
