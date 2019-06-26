package com.ybbbi.googlestore.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * ybbbi
 * 2019-06-26 13:57
 */
public class DrawableUtil {
    public static GradientDrawable createShape(){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(ColorUtil.randomColor());
        drawable.setCornerRadius(25);
        return drawable;
    }
    public static StateListDrawable createSelector(Drawable pressed,Drawable normal){
        StateListDrawable drawable=new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed},pressed);
        drawable.addState(new int[]{},normal);
        return drawable;
    }
}
