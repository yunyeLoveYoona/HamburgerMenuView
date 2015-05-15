package com.example.administrator.hamburger.viwe;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 15-5-15.
 */
class HamburgerView extends View implements View.OnClickListener {
    private static final int WIDTH = 50;
    private int MARGIN = 10;
    private Paint paint;
    private float moveHeight = 0.0f;
    private static final int RELEASE_FLAG = 0;
    private static final int PUT_FLAG = 1;
    private static final int NULL_FLAG = -1;
    private int flag = NULL_FLAG;

    public HamburgerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6.0f);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getMeasuredHeight() == 0) {
            setMeasuredDimension(WIDTH * 2, WIDTH * 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine((getMeasuredWidth() - WIDTH) / 2, getMeasuredHeight() / 2,
                (getMeasuredWidth()
                        - WIDTH) / 2 + WIDTH, getMeasuredHeight() / 2 + moveHeight, paint);
        canvas.drawLine((getMeasuredWidth() - WIDTH) / 2,
                getMeasuredHeight() / 2 + moveHeight + MARGIN * 2,
                (getMeasuredWidth() - WIDTH) / 2 + WIDTH, getMeasuredHeight() / 2
                        + MARGIN * 2, paint);
        if (flag == NULL_FLAG) {
            canvas.drawLine((getMeasuredWidth() - WIDTH) / 2,
                    getMeasuredHeight() / 2 + MARGIN,
                    (getMeasuredWidth() - WIDTH) / 2 + WIDTH, getMeasuredHeight() / 2 + MARGIN,
                    paint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            RectF oval = new RectF();
            oval.left = (getMeasuredWidth() - WIDTH) / 2 - WIDTH / 3;
            oval.right = (getMeasuredWidth() - WIDTH) / 2 + WIDTH + WIDTH / 3;
            oval.top = getMeasuredHeight() / 2 - WIDTH / 5;
            oval.bottom = getMeasuredHeight() / 2 + 60 + WIDTH / 5;
            canvas.drawArc(oval, 315, moveHeight * 6, false, paint);
        }
    }


    @Override
    public void onClick(View v) {
        switch (flag) {
            case NULL_FLAG:
                flag = PUT_FLAG;
            case PUT_FLAG:
                ValueAnimator putAnimator = ValueAnimator.ofObject(new LineEvaluator(), 0.0f, 60.0f);
                putAnimator.setDuration(500);
                putAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        moveHeight = (float) animation.getAnimatedValue();
                        invalidate();
                    }
                });
                MARGIN = 0;
                putAnimator.start();
                flag = RELEASE_FLAG;
                break;
            case RELEASE_FLAG:
                ValueAnimator valueAnimator = ValueAnimator.ofObject(new LineEvaluator(), 60.0f, 0.0f);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        moveHeight = (float) animation.getAnimatedValue();
                        if (moveHeight <= 20) {
                            MARGIN = 10;
                            flag = NULL_FLAG;
                        }
                        invalidate();
                    }
                });
                valueAnimator.start();
                break;
        }


    }
}
