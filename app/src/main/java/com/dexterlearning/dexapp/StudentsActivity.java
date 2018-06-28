package com.dexterlearning.dexapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class StudentsActivity extends AppCompatActivity
        implements StudentListFragment.OnFragmentInteractionListener {

    private FirebaseFirestore db;
    private String [] students = new String []{"John Doe", "Mya Komeada",
            "Alexis Voltair", "Caroline Wilhelmina", "Osmosis Jones",
            "Caleb Brown", "Alice Nano", "Sir Victoreeem", "Sakure The Beautifuru",
            "Snoop Dog", "G Lite 10", "Mr.Tough Guy", "Mr. Nice Guy"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);


    }

    @Override
    public void onFragmentInteraction(int position) {
        //TODO:Change data on student profile to the correct student and show profile
        //Read student's data from firestore
        //Populate student profile fragment with data

        TextView tvProfileName = (TextView) findViewById(R.id.tvUserName);
        tvProfileName.setText(students[position]);

        if (findViewById(R.id.students_activity_port) != null) {
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction()
                    .hide(manager.findFragmentById(R.id.sl_frag))
                    .show(manager.findFragmentById(R.id.sp_frag))
                    .addToBackStack(null)
                    .commit();

        }
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
