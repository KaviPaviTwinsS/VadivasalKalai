package pasu.vadivasal.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.VectorDrawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.List;

import pasu.vadivasal.R;

public class Utils {
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return (((value - fromLow) / (fromHigh - fromLow)) * (toHigh - toLow)) + toLow;
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

//    public static int getScreenHeight(Context c) {
//        if (screenHeight == 0) {
//            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            screenHeight = size.y;
//        }}
    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static List<Icon> getIcons() {
        List<Icon> icons = new ArrayList();
//        icons.add(new Icon(R.drawable.heart_on, R.drawable.heart_off, IconType.Heart));
//        icons.add(new Icon(R.drawable.star_on, R.drawable.star_off, IconType.Star));
//        icons.add(new Icon(R.drawable.thumb_on, R.drawable.thumb_off, IconType.Thumb));
        return icons;
    }

    public static Drawable resizeDrawable(Context context, Drawable drawable, int width, int height) {
        return new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(getBitmap(drawable, width, height), width, height, true));
    }

    public static Bitmap getBitmap(Drawable drawable, int width, int height) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable, width, height);
        }
        if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable, width, height);
        }
        throw new IllegalArgumentException("Unsupported drawable type");
    }

    @TargetApi(21)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap getBitmap(VectorDrawableCompat vectorDrawable, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static float dipToPixels(Context context, float dipValue) {
        return TypedValue.applyDimension(1, dipValue, context.getResources().getDisplayMetrics());
    }
}
