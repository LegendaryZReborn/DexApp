package com.dexterlearning.dexapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseContentFragment extends LabeledFragment {
    private PDFView pdfView;

    private StorageReference mStorageRef;

    public CourseContentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Activity activity = getActivity();
        String pdfFileName = activity.getIntent().getStringExtra("file");
        pdfFileName = "3D Printing Day 1.pdf";
        loadCoursePdf(pdfFileName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_course_content, container, false);
        pdfView = (PDFView) rootView.findViewById(R.id.pdfView);

        /*Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.text1)).setText(
                args.getString("msg"));*/
        return rootView;
    }

    public static Fragment newInstance(String title) {
        CourseContentFragment f = new CourseContentFragment();
        f.setLabel("COURSE_CONTENT_FRAG");
        Bundle args = new Bundle();
        args.putString("msg", title);
        f.setArguments(args);

        return f;
    }

    private void loadCoursePdf(String pdfFileName) {
        mStorageRef.child("courses/" + pdfFileName).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                File localFile = null;
                // Use the bytes to display the image
                try {
                    localFile = File.createTempFile("temp", ".pdf");
                    String absPath = localFile.getAbsolutePath();
                    com.google.common.io.Files.write(bytes, localFile);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(localFile != null) {
                    pdfView.fromFile(localFile)
                            .enableSwipe(true) // allows to block changing pages using swipe
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .defaultPage(0)
                            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                            .password(null)
                            .scrollHandle(null)
                            .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                            // spacing between pages in dp. To define spacing color, set view background
                            .spacing(0)
                            .load();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                //TODO:HANDLE THIS
            }
        });
    }

}
