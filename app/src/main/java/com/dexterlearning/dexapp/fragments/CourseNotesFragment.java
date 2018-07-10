package com.dexterlearning.dexapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dexterlearning.dexapp.R;
import com.dexterlearning.dexapp.models.LinedEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseNotesFragment extends Fragment {


    public CourseNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_course_notes, container, false);
         LinedEditText etNotes = (LinedEditText) rootView.findViewById(R.id.etNotes);
         etNotes.clearFocus();

         return rootView;
    }

    public static Fragment newInstance(String title) {
        CourseNotesFragment f = new CourseNotesFragment();

        return f;
    }
}
