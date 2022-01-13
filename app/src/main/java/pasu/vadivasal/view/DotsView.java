package pasu.vadivasal.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;


public class DotsView extends View {
    private static final int DOTS_COUNT = 7;
    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        public Float get(DotsView object) {
            return Float.valueOf(object.getCurrentProgress());
        }

        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value.floatValue());
        }
    };
    private static final int OUTER_DOTS_POSITION_ANGLE = 51;
    private int COLOR_1;
    private int COLOR_2;
    private int COLOR_3;
    private int COLOR_4;
    private ArgbEvaluator argbEvaluator;
    private int centerX;
    private int centerY;
    private final Paint[] circlePaints;
    private float currentDotSize1;
    private float currentDotSize2;
    private float currentProgress;
    private float currentRadius1;
    private float currentRadius2;
    private int height;
    private float maxDotSize;
    private float maxInnerDotsRadius;
    private float maxOuterDotsRadius;
    private int width;

    public DotsView(Context context) {
        super(context);
        this.COLOR_1 = -16121;
        this.COLOR_2 = -26624;
        this.COLOR_3 = -43230;
        this.COLOR_4 = -769226;
        this.width = 0;
        this.height = 0;
        this.circlePaints = new Paint[4];
        this.currentProgress = 0.0f;
        this.currentRadius1 = 0.0f;
        this.currentDotSize1 = 0.0f;
        this.currentDotSize2 = 0.0f;
        this.currentRadius2 = 0.0f;
        this.argbEvaluator = new ArgbEvaluator();
        init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.COLOR_1 = -16121;
        this.COLOR_2 = -26624;
        this.COLOR_3 = -43230;
        this.COLOR_4 = -769226;
        this.width = 0;
        this.height = 0;
        this.circlePaints = new Paint[4];
        this.currentProgress = 0.0f;
        this.currentRadius1 = 0.0f;
        this.currentDotSize1 = 0.0f;
        this.currentDotSize2 = 0.0f;
        this.currentRadius2 = 0.0f;
        this.argbEvaluator = new ArgbEvaluator();
        init();
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.COLOR_1 = -16121;
        this.COLOR_2 = -26624;
        this.COLOR_3 = -43230;
        this.COLOR_4 = -769226;
        this.width = 0;
        this.height = 0;
        this.circlePaints = new Paint[4];
        this.currentProgress = 0.0f;
        this.currentRadius1 = 0.0f;
        this.currentDotSize1 = 0.0f;
        this.currentDotSize2 = 0.0f;
        this.currentRadius2 = 0.0f;
        this.argbEvaluator = new ArgbEvaluator();
        init();
    }

    private void init() {
        for (int i = 0; i < this.circlePaints.length; i++) {
            this.circlePaints[i] = new Paint();
            this.circlePaints[i].setStyle(Style.FILL);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.centerX = w / 2;
        this.centerY = h / 2;
        this.maxDotSize = 5.0f;
        this.maxOuterDotsRadius = ((float) (w / 2)) - (this.maxDotSize * 2.0f);
        this.maxInnerDotsRadius = 0.8f * this.maxOuterDotsRadius;
    }

    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            canvas.drawCircle((float) ((int) (((double) this.centerX) + (((double) this.currentRadius1) * Math.cos((((double) (i * 51)) * 3.141592653589793d) / 180.0d)))), (float) ((int) (((double) this.centerY) + (((double) this.currentRadius1) * Math.sin((((double) (i * 51)) * 3.141592653589793d) / 180.0d)))), this.currentDotSize1, this.circlePaints[i % this.circlePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            canvas.drawCircle((float) ((int) (((double) this.centerX) + (((double) this.currentRadius2) * Math.cos((((double) ((i * 51) - 10)) * 3.141592653589793d) / 180.0d)))), (float) ((int) (((double) this.centerY) + (((double) this.currentRadius2) * Math.sin((((double) ((i * 51) - 10)) * 3.141592653589793d) / 180.0d)))), this.currentDotSize2, this.circlePaints[(i + 1) % this.circlePaints.length]);
        }
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();
        postInvalidate();
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }

    private void updateInnerDotsPosition() {
        if (this.currentProgress < 0.3f) {
            this.currentRadius2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0d, 0.30000001192092896d, 0.0d, (double) this.maxInnerDotsRadius);
        } else {
            this.currentRadius2 = this.maxInnerDotsRadius;
        }
        if (this.currentProgress == 0.0f) {
            this.currentDotSize2 = 0.0f;
        } else if (((double) this.currentProgress) < 0.2d) {
            this.currentDotSize2 = this.maxDotSize;
        } else if (((double) this.currentProgress) < 0.5d) {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.20000000298023224d, 0.5d, (double) this.maxDotSize, ((double) this.maxDotSize) * 0.3d);
        } else {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.5d, 1.0d, (double) (this.maxDotSize * 0.3f), 0.0d);
        }
    }

    private void updateOuterDotsPosition() {
        if (this.currentProgress < 0.3f) {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0d, 0.30000001192092896d, 0.0d, (double) (this.maxOuterDotsRadius * 0.8f));
        } else {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.30000001192092896d, 1.0d, (double) (0.8f * this.maxOuterDotsRadius), (double) this.maxOuterDotsRadius);
        }
        if (this.currentProgress == 0.0f) {
            this.currentDotSize1 = 0.0f;
        } else if (((double) this.currentProgress) < 0.7d) {
            this.currentDotSize1 = this.maxDotSize;
        } else {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.699999988079071d, 1.0d, (double) this.maxDotSize, 0.0d);
        }
    }

    private void updateDotsPaints() {
//        if (this.currentProgress < PaperOnboardingEngineDefaults.PAGER_ICON_SHAPE_ALPHA) {
//            float progress = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.0d, 0.5d, 0.0d, 1.0d);
//            this.circlePaints[0].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_1), Integer.valueOf(this.COLOR_2))).intValue());
//            this.circlePaints[1].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_2), Integer.valueOf(this.COLOR_3))).intValue());
//            this.circlePaints[2].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_3), Integer.valueOf(this.COLOR_4))).intValue());
//            this.circlePaints[3].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_4), Integer.valueOf(this.COLOR_1))).intValue());
//            return;
//        }
//        progress = (float) Utils.mapValueFromRangeToRange((double) this.currentProgress, 0.5d, 1.0d, 0.0d, 1.0d);
//        this.circlePaints[0].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_2), Integer.valueOf(this.COLOR_3))).intValue());
//        this.circlePaints[1].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_3), Integer.valueOf(this.COLOR_4))).intValue());
//        this.circlePaints[2].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_4), Integer.valueOf(this.COLOR_1))).intValue());
//        this.circlePaints[3].setColor(((Integer) this.argbEvaluator.evaluate(progress, Integer.valueOf(this.COLOR_1), Integer.valueOf(this.COLOR_2))).intValue());
    }

    public void setColors(@ColorInt int primaryColor, @ColorInt int secondaryColor) {
        this.COLOR_1 = primaryColor;
        this.COLOR_2 = secondaryColor;
        this.COLOR_3 = primaryColor;
        this.COLOR_4 = secondaryColor;
        invalidate();
    }

    private void updateDotsAlpha() {
        int alpha = (int) Utils.mapValueFromRangeToRange((double) ((float) Utils.clamp((double) this.currentProgress, 0.6000000238418579d, 1.0d)), 0.6000000238418579d, 1.0d, 255.0d, 0.0d);
        this.circlePaints[0].setAlpha(alpha);
        this.circlePaints[1].setAlpha(alpha);
        this.circlePaints[2].setAlpha(alpha);
        this.circlePaints[3].setAlpha(alpha);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.width != 0 && this.height != 0) {
            setMeasuredDimension(this.width, this.height);
        }
    }
}
