package com.dexterlearning.dexapp.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dexterlearning.dexapp.R;
import com.dexterlearning.dexapp.activities.CourseActivity;

/*A course item consists of a card view which contains a linear layout.
That linear layout houses the imageview and the text you see in the card
 */
public class CourseCardView extends CardView {

    Context context;

    public CourseCardView(Context context, String title, String pdfFileName){
        super(context);
        this.context = context;
        this.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        initialize(title, pdfFileName);
    }


    private int toPixels(int value){
        float factor = context.getResources().getDisplayMetrics().density;
        return (int)(value * factor);
    }

    private void initialize(final String title, final String pdfFileName){

        this.setClickable(true);
        this.setForeground(getResources().getDrawable(R.drawable.custom_ripple_border));
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        CourseActivity.class);

                View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
                TextView tvUserName = (TextView) rootView.findViewById(R.id.tvUserName);

                intent.putExtra("title", title);
                intent.putExtra("file", pdfFileName);
                intent.putExtra("user", tvUserName.getText().toString());

                context.startActivity(intent);
            }
        });


        LayoutInflater lInflator = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) lInflator.inflate(R.layout.item_course, this, false);

        //Set course image and title dynamically
        ImageView courseImage = (ImageView) linearLayout.findViewById(R.id.imgvCourseImg);
        courseImage.setBackground(getResources().getDrawable(R.drawable.circlebackground));
        courseImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_apps_black_24dp));
        TextView courseTitle = (TextView) linearLayout.findViewById((R.id.tvCourseTitle));
        courseTitle.setText(title);

        this.addView(linearLayout);
    }
}
