package com.dexterlearning.dexapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dexterlearning.dexapp.models.LabeledFragment;
import com.dexterlearning.dexapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContentMasterFragment extends LabeledFragment {

  // private FragmentManager manager;

    public ContentMasterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // manager = getChildFragmentManager();

        //Hide the course notes fragment
       /* manager.beginTransaction()
                .hide(manager.findFragmentById(R.id.notes_Frag))
               .commit();*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_content_master, container, false);
        //FloatingActionButton fabEdit = (FloatingActionButton) rootView.findViewById(R.id.fabEdit);
        /*fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide the course notes fragment
                toggleNotes();
            }
        });*/

        return rootView;

    }

   /* private void toggleNotes(){
        Fragment notesFrag = manager.findFragmentById(R.id.notes_Frag);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down,
                R.anim.slide_in_up, R.anim.slide_out_down);

        if(!notesFrag.isVisible()){
            transaction.show(notesFrag);
            transaction.addToBackStack(null);
        }else{
            transaction.hide(notesFrag);
        }

        transaction.commit();
    }*/

    public static Fragment newInstance(String title) {
        ContentMasterFragment f = new ContentMasterFragment();
        f.setLabel("COUSRE_CONTENT_FRAG");

        return f;
    }
}
