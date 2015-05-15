package com.example.administrator.hamburger.viwe;

import android.animation.TypeEvaluator;

/**
 * Created by Administrator on 15-5-15.
 */
public class LineEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        float height;
        height = (Float) startValue
                + ((Float) endValue - (Float) startValue)
                * fraction;
        return height;
    }
}
