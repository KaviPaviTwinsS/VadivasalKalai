package pasu.vadivasal.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class LikeButton extends FrameLayout implements OnClickListener {
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(4.0f);
    private float animationScaleFactor;
    private AnimatorSet animatorSet;
    private int circleEndColor;
    private int circleStartColor;
    private CircleView circleView;
    private Icon currentIcon;
    private int dotPrimaryColor;
    private int dotSecondaryColor;
    private DotsView dotsView;
    private ImageView icon;
    private int iconSize;
    private boolean isChecked;
    private boolean isEnabled;
    private Drawable likeDrawable;
    private OnLikeListener likeListener;
    private Drawable unLikeDrawable;

    class C21061 extends AnimatorListenerAdapter {
        C21061() {
        }

        public void onAnimationCancel(Animator animation) {
            LikeButton.this.circleView.setInnerCircleRadiusProgress(0.0f);
            LikeButton.this.circleView.setOuterCircleRadiusProgress(0.0f);
            LikeButton.this.dotsView.setCurrentProgress(0.0f);
            LikeButton.this.icon.setScaleX(1.0f);
            LikeButton.this.icon.setScaleY(1.0f);
        }
    }

    public LikeButton(Context context) {
        this(context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
//        LayoutInflater.from(getContext()).inflate(R.layout.likeview, this, true);
//        this.icon = (ImageView) findViewById(R.id.icon);
//        this.dotsView = (DotsView) findViewById(R.id.dots);
//        this.circleView = (CircleView) findViewById(R.id.circle);
//        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeButton, defStyle, 0);
//        this.iconSize = array.getDimensionPixelSize(R.styleable.LikeButton_icon_size, -1);
//        if (this.iconSize == -1) {
//            this.iconSize = 40;
//        }
//        String iconType = array.getString(R.styleable.LikeButton_icon_type);
//        this.likeDrawable = getDrawableFromResource(array, R.styleable.LikeButton_like_drawable);
//        if (this.likeDrawable != null) {
//            setLikeDrawable(this.likeDrawable);
//        }
//        this.unLikeDrawable = getDrawableFromResource(array, R.styleable.LikeButton_unlike_drawable);
//        if (this.unLikeDrawable != null) {
//            setUnlikeDrawable(this.unLikeDrawable);
//        }
//        if (!(iconType == null || iconType.isEmpty())) {
//            this.currentIcon = parseIconType(iconType);
//        }
//        this.circleStartColor = array.getColor(R.styleable.LikeButton_circle_start_color, 0);
//        if (this.circleStartColor != 0) {
//            this.circleView.setStartColor(this.circleStartColor);
//        }
//        this.circleEndColor = array.getColor(R.styleable.LikeButton_circle_end_color, 0);
//        if (this.circleEndColor != 0) {
//            this.circleView.setEndColor(this.circleEndColor);
//        }
//        this.dotPrimaryColor = array.getColor(R.styleable.LikeButton_dots_primary_color, 0);
//        this.dotSecondaryColor = array.getColor(R.styleable.LikeButton_dots_secondary_color, 0);
//        if (!(this.dotPrimaryColor == 0 || this.dotSecondaryColor == 0)) {
//            this.dotsView.setColors(this.dotPrimaryColor, this.dotSecondaryColor);
//        }
//        if (this.likeDrawable == null && this.unLikeDrawable == null) {
//            if (this.currentIcon != null) {
//                setLikeDrawableRes(this.currentIcon.getOnIconResourceId());
//                setUnlikeDrawableRes(this.currentIcon.getOffIconResourceId());
//            } else {
//                this.currentIcon = parseIconType(IconType.Heart);
//                setLikeDrawableRes(this.currentIcon.getOnIconResourceId());
//                setUnlikeDrawableRes(this.currentIcon.getOffIconResourceId());
//            }
//        }
//        setEnabled(array.getBoolean(R.styleable.LikeButton_is_enabled, true));
//        Boolean status = Boolean.valueOf(array.getBoolean(R.styleable.LikeButton_liked, false));
//        setAnimationScaleFactor(array.getFloat(R.styleable.LikeButton_anim_scale_factor, IPhotoView.DEFAULT_MAX_SCALE));
//        setLiked(status);
//        setOnClickListener(this);
//        array.recycle();
    }

    private Drawable getDrawableFromResource(TypedArray array, int styleableIndexId) {
        int id = array.getResourceId(styleableIndexId, -1);
        return -1 != id ? ContextCompat.getDrawable(getContext(), id) : null;
    }

    public void onClick(View v) {
        if (this.isEnabled) {
            boolean z;
            if (this.isChecked) {
                z = false;
            } else {
                z = true;
            }
            this.isChecked = z;
            this.icon.setImageDrawable(this.isChecked ? this.likeDrawable : this.unLikeDrawable);
            if (this.likeListener != null) {
                if (this.isChecked) {
                    this.likeListener.liked(this);
                } else {
                    this.likeListener.unLiked(this);
                }
            }
            if (this.animatorSet != null) {
                this.animatorSet.cancel();
            }
            if (this.isChecked) {
                this.icon.animate().cancel();
                this.icon.setScaleX(0.0f);
                this.icon.setScaleY(0.0f);
                this.circleView.setInnerCircleRadiusProgress(0.0f);
                this.circleView.setOuterCircleRadiusProgress(0.0f);
                this.dotsView.setCurrentProgress(0.0f);
                this.animatorSet = new AnimatorSet();
                ObjectAnimator outerCircleAnimator = ObjectAnimator.ofFloat(this.circleView, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, new float[]{0.1f, 1.0f});
                outerCircleAnimator.setDuration(250);
                outerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);
                ObjectAnimator innerCircleAnimator = ObjectAnimator.ofFloat(this.circleView, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, new float[]{0.1f, 1.0f});
                innerCircleAnimator.setDuration(200);
                innerCircleAnimator.setStartDelay(200);
                innerCircleAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);
                ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(this.icon, ImageView.SCALE_Y, new float[]{0.2f, 1.0f});
                starScaleYAnimator.setDuration(350);
                starScaleYAnimator.setStartDelay(250);
                starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);
                ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(this.icon, ImageView.SCALE_X, new float[]{0.2f, 1.0f});
                starScaleXAnimator.setDuration(350);
                starScaleXAnimator.setStartDelay(250);
                starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);
                ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(this.dotsView, DotsView.DOTS_PROGRESS, new float[]{0.0f, 1.0f});
                dotsAnimator.setDuration(900);
                dotsAnimator.setStartDelay(50);
                dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);
                this.animatorSet.playTogether(new Animator[]{outerCircleAnimator, innerCircleAnimator, starScaleYAnimator, starScaleXAnimator, dotsAnimator});
                this.animatorSet.addListener(new C21061());
                this.animatorSet.start();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean isInside = false;
        if (this.isEnabled) {
            switch (event.getAction()) {
                case 0:
                    setPressed(true);
                    break;
                case 1:
                    this.icon.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                    this.icon.animate().scaleX(1.0f).scaleY(1.0f).setInterpolator(DECCELERATE_INTERPOLATOR);
                    if (isPressed()) {
                        performClick();
                        setPressed(false);
                        break;
                    }
                    break;
                case 2:
                    float x = event.getX();
                    float y = event.getY();
                    if (x > 0.0f && x < ((float) getWidth()) && y > 0.0f && y < ((float) getHeight())) {
                        isInside = true;
                    }
                    if (isPressed() != isInside) {
                        setPressed(isInside);
                        break;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    public void setLikeDrawableRes(@DrawableRes int resId) {
        this.likeDrawable = ContextCompat.getDrawable(getContext(), resId);
        if (this.iconSize != 0) {
            this.likeDrawable = Utils.resizeDrawable(getContext(), this.likeDrawable, this.iconSize, this.iconSize);
        }
    }

    public void setLikeDrawable(Drawable likeDrawable) {
        this.likeDrawable = likeDrawable;
        if (this.iconSize != 0) {
            this.likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, this.iconSize, this.iconSize);
        }
    }

    public void setUnlikeDrawableRes(@DrawableRes int resId) {
        this.unLikeDrawable = ContextCompat.getDrawable(getContext(), resId);
        if (this.iconSize != 0) {
            this.unLikeDrawable = Utils.resizeDrawable(getContext(), this.unLikeDrawable, this.iconSize, this.iconSize);
        }
        this.icon.setImageDrawable(this.unLikeDrawable);
    }

    public void setUnlikeDrawable(Drawable unLikeDrawable) {
        this.unLikeDrawable = unLikeDrawable;
        if (this.iconSize != 0) {
            this.unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, this.iconSize, this.iconSize);
        }
        this.icon.setImageDrawable(unLikeDrawable);
    }

    public void setIcon(IconType currentIconType) {
        this.currentIcon = parseIconType(currentIconType);
//        setLikeDrawableRes(this.currentIcon.getOnIconResourceId());
//        setUnlikeDrawableRes(this.currentIcon.getOffIconResourceId());
    }

    public void setIconSizeDp(int iconSize) {
        setIconSizePx((int) Utils.dipToPixels(getContext(), (float) iconSize));
    }

    public void setIconSizePx(int iconSize) {
        this.iconSize = iconSize;
        setEffectsViewSize();
        this.unLikeDrawable = Utils.resizeDrawable(getContext(), this.unLikeDrawable, iconSize, iconSize);
        this.likeDrawable = Utils.resizeDrawable(getContext(), this.likeDrawable, iconSize, iconSize);
    }

    private Icon parseIconType(String iconType) {
        for (Icon icon : Utils.getIcons()) {
//            if (icon.getIconType().name().toLowerCase().equals(iconType.toLowerCase())) {
//                return icon;
//            }
        }
        throw new IllegalArgumentException("Correct icon type not specified.");
    }

    private Icon parseIconType(IconType iconType) {
        for (Icon icon : Utils.getIcons()) {
//            if (icon.getIconType().equals(iconType)) {
//                return icon;
//            }
        }
        throw new IllegalArgumentException("Correct icon type not specified.");
    }

    public void setOnLikeListener(OnLikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public void setExplodingDotColorsRes(@ColorRes int primaryColor, @ColorRes int secondaryColor) {
        this.dotsView.setColors(ContextCompat.getColor(getContext(), primaryColor), ContextCompat.getColor(getContext(), secondaryColor));
    }

    public void setCircleStartColorRes(@ColorRes int circleStartColor) {
        this.circleStartColor = circleStartColor;
        this.circleView.setStartColor(ContextCompat.getColor(getContext(), circleStartColor));
    }

    public void setCircleEndColorRes(@ColorRes int circleEndColor) {
        this.circleEndColor = circleEndColor;
        this.circleView.setEndColor(ContextCompat.getColor(getContext(), circleEndColor));
    }

    private void setEffectsViewSize() {
        if (this.iconSize != 0) {
            this.dotsView.setSize((int) (((float) this.iconSize) * this.animationScaleFactor), (int) (((float) this.iconSize) * this.animationScaleFactor));
            this.circleView.setSize(this.iconSize, this.iconSize);
        }
    }

    public void setLiked(Boolean status) {
        if (status.booleanValue()) {
            this.isChecked = true;
            this.icon.setImageDrawable(this.likeDrawable);
            return;
        }
        this.isChecked = false;
        this.icon.setImageDrawable(this.unLikeDrawable);
    }

    public void setEnabled(boolean enabled) {
        this.isEnabled = enabled;
    }

    public void setAnimationScaleFactor(float animationScaleFactor) {
        this.animationScaleFactor = animationScaleFactor;
        setEffectsViewSize();
    }
}
