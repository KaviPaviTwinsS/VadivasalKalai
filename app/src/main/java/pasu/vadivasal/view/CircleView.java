package pasu.vadivasal.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import androidx.annotation.ColorInt;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

public class CircleView extends View {
    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {
        public Float get(CircleView object) {
            return Float.valueOf(object.getInnerCircleRadiusProgress());
        }

        public void set(CircleView object, Float value) {
            object.setInnerCircleRadiusProgress(value.floatValue());
        }
    };
    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS = new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
        public Float get(CircleView object) {
            return Float.valueOf(object.getOuterCircleRadiusProgress());
        }

        public void set(CircleView object, Float value) {
            object.setOuterCircleRadiusProgress(value.floatValue());
        }
    };
    private int END_COLOR = -16121;
    private int START_COLOR = -43230;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    private Paint circlePaint = new Paint();
    private int height = 0;
    private float innerCircleRadiusProgress = 0.0f;
    private Paint maskPaint = new Paint();
    private int maxCircleSize;
    private float outerCircleRadiusProgress = 0.0f;
    private Bitmap tempBitmap;
    private Canvas tempCanvas;
    private int width = 0;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.circlePaint.setStyle(Style.FILL);
        this.maskPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
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

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.maxCircleSize = w / 2;
        this.tempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Config.ARGB_8888);
        this.tempCanvas = new Canvas(this.tempBitmap);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.tempCanvas.drawColor(ViewCompat.MEASURED_SIZE_MASK, Mode.CLEAR);
        this.tempCanvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.outerCircleRadiusProgress * ((float) this.maxCircleSize), this.circlePaint);
        this.tempCanvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), this.innerCircleRadiusProgress * ((float) this.maxCircleSize), this.maskPaint);
        canvas.drawBitmap(this.tempBitmap, 0.0f, 0.0f, null);
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        this.innerCircleRadiusProgress = innerCircleRadiusProgress;
        postInvalidate();
    }

    public float getInnerCircleRadiusProgress() {
        return this.innerCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        this.outerCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();
        postInvalidate();
    }

    private void updateCircleColor() {
        this.circlePaint.setColor(((Integer) this.argbEvaluator.evaluate((float) Utils.mapValueFromRangeToRange((double) ((float) Utils.clamp((double) this.outerCircleRadiusProgress, 0.5d, 1.0d)), 0.5d, 1.0d, 0.0d, 1.0d), Integer.valueOf(this.START_COLOR), Integer.valueOf(this.END_COLOR))).intValue());
    }

    public float getOuterCircleRadiusProgress() {
        return this.outerCircleRadiusProgress;
    }

    public void setStartColor(@ColorInt int color) {
        this.START_COLOR = color;
        invalidate();
    }

    public void setEndColor(@ColorInt int color) {
        this.END_COLOR = color;
        invalidate();
    }
}
