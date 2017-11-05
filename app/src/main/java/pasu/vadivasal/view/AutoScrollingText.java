package pasu.vadivasal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import java.io.FileNotFoundException;

public class AutoScrollingText extends TextView {
    private static final float DEFAULT_SPEED = 45.0f;
    public boolean continuousScrolling = true;
    public Scroller scroller;
    public float speed = DEFAULT_SPEED;

    public AutoScrollingText(Context context) throws FileNotFoundException {
        super(context);
        scrollerInstance(context);
    }

    public AutoScrollingText(Context context, AttributeSet attributes) throws FileNotFoundException {
        super(context, attributes);
        scrollerInstance(context);
    }

    public void scrollerInstance(Context context) {
        this.scroller = new Scroller(context, new LinearInterpolator());
        setScroller(this.scroller);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.scroller.isFinished()) {
            scroll();
        }
    }

    public void scroll() {
        int visibleHeight = (getHeight() - getPaddingBottom()) - getPaddingTop();
        int distance = visibleHeight + (getLineCount() * getLineHeight());
        this.scroller.startScroll(0, visibleHeight * -1, 0, distance, (int) (((float) distance) * this.speed));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.scroller.isFinished() && this.continuousScrolling) {
            scroll();
        }
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setContinuousScrolling(boolean continuousScrolling) {
        this.continuousScrolling = continuousScrolling;
    }

    public boolean isContinuousScrolling() {
        return this.continuousScrolling;
    }
}
