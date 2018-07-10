package com.dexterlearning.dexapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexterlearning.dexapp.models.LabeledFragment;
import com.dexterlearning.dexapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentsFragment extends LabeledFragment {

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_students, container, false);
        /*Fragment Management*/
        if(getActivity().findViewById(R.id.activity_course_port) != null){
            FragmentManager manager = getChildFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.sl_frag))
                    .hide(manager.findFragmentById(R.id.sp_frag))
                    .commit();
        }


        if(getActivity().findViewById(R.id.activity_course_land) != null){
            FragmentManager manager = getChildFragmentManager();
            manager.beginTransaction()
                    .show(manager.findFragmentById(R.id.sl_frag))
                    .show(manager.findFragmentById(R.id.sp_frag))
                    .commit();
        }

         return rootView;
    }

    public static Fragment newInstance(String title) {
        StudentsFragment f = new StudentsFragment();
        f.setLabel("STUDENTS_FRAG");
        Bundle args = new Bundle();
        args.putString("msg", title);
        f.setArguments(args);

        return f;
    }


}
