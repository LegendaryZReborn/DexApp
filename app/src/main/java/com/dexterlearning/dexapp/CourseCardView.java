package com.dexterlearning.dexapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
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
        initialize(title, url);
    }


    private int toDp(int value){
        float factor = context.getResources().getDisplayMetrics().density;
        return (int)(value * factor);
    }

    private void initialize(final String title, final String url){

        this.setClickable(true);
        // TypedValue outVal = new TypedValue();
        // context.getTheme().resolveAttribute(R.attr.selectableItemBackground, outVal, true);
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

        //Configure linear layout to house the image and title
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);


        //configure item image
        ImageView courseImage = new ImageView(context);
        int dimensions = toDp(80);
        courseImage.setLayoutParams(new LinearLayout.LayoutParams(dimensions, dimensions));
        courseImage.setBackground(getResources().getDrawable(R.drawable.circlebackground));
        courseImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_apps_black_24dp));

        //Configure item title
        TextView courseTitle = new TextView(context);
        LinearLayout.LayoutParams cTitleParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        cTitleParams.setMargins(0, toDp( 5), 0, 0);
        courseTitle.setLayoutParams(cTitleParams);
        courseTitle.setText(title);
        courseTitle.setTypeface(null, Typeface.BOLD);
        courseTitle.setGravity(Gravity.CENTER);

        //Add linear layout of views in courseItem card
        linearLayout.addView(courseImage);
        linearLayout.addView(courseTitle);
        this.addView(linearLayout);
    }
}
