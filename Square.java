package com.fidku.jeloubeta.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Square extends ImageView
{
    public Square(Context context)
    {
        super(context);
    }

    public Square(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Square(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}