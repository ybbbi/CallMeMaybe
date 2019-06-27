package com.ybbbi.googlestore.animate;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * ybbbi
 * 2019-06-27 17:13
 */
public class AnimateHeight {
    private ValueAnimator animator;

    public AnimateHeight(int start, int end, final View target){
        animator=ValueAnimator.ofInt(start,end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams params = target.getLayoutParams();

                params.height=animatedValue;
                target.setLayoutParams(params);

            }
        });
    }
    public void start(long duration){
        animator.setDuration(duration).start();
    }


}
