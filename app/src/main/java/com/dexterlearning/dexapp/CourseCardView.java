package com.dexterlearning.dexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*A course item consists of a card view which contains a linear layout.
That linear layout houses the imageview and the text you see in the card
 */
public class CourseCardView extends CardView {

    Context context;

    public CourseCardView(Context context, String title, String url){
        super(context);
        this.context = context;
        this.setLayoutParams(new ViewGroup.LayoutParams(toPixels(110),
               toPixels(160)));
        initialize(title, url);
    }


    private int toPixels(int value){
        float factor = context.getResources().getDisplayMetrics().density;
        return (int)(value * factor);
    }

    private void initialize(final String title, final String url){

        this.setClickable(true);
        this.setForeground(getResources().getDrawable(R.drawable.custom_ripple_border));
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,
                        com.dexterlearning.dexapp.CourseActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);

                context.startActivity(intent);
            }
        });


        LayoutInflater lInflator = LayoutInflater.from(context);
        LinearLayout linearLayout = (LinearLayout) lInflator.inflate(R.layout.course_item, null);

        //Set course image and title dynamically
        ImageView courseImage = (ImageView) linearLayout.findViewById(R.id.imgvCourseImg);
        courseImage.setBackground(getResources().getDrawable(R.drawable.circlebackground));
        courseImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_apps_black_24dp));
        TextView courseTitle = (TextView) linearLayout.findViewById((R.id.tvCourseTitle));
        courseTitle.setText(title);

        this.addView(linearLayout);
    }
}
