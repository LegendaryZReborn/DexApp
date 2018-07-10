package com.dexterlearning.dexapp.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class LinedEditText extends AppCompatEditText {

    private Paint paint;
    private Rect rect;

    public LinedEditText(Context context, AttributeSet attrs){
        super(context, attrs);

        paint = new Paint();
        rect = new Rect();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(android.R.color.black));
    }

    public void setLineColor(int color){
        paint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int etHeight = getHeight();
        int etLineHeight =  getLineHeight();
        int lineCount = etHeight / etLineHeight;

        if(getLineCount() > lineCount){
            lineCount = getLineCount();
        }

        int baseline = getLineBounds(0, rect);
        for(int i = 0; i < lineCount; ++i){
            canvas.drawLine(rect.left, baseline + 1, rect.right, baseline + 1, paint);

            //get ready to draw the next line
            baseline += etLineHeight;
        }

        super.onDraw(canvas);
    }
}
