package pasu.vadivasal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.lang.reflect.Field;

import pasu.vadivasal.R;

/**
 * Created by Admin on 24-12-2017.
 */


public class MyBottomNavigationView extends BottomNavigationView {


    public static void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
//                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BottomNav", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BottomNav", "Unable to change value of shift mode", e);
        }
    }


    public MyBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //  centerMenuIcon();
//        removeShiftMode(this);
    }

    private void centerMenuIcon() {
        BottomNavigationMenuView menuView = getBottomMenuView();
        if (menuView != null) {
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView menuItemView = (BottomNavigationItemView) menuView.getChildAt(i);
                android.widget.TextView smallText = menuItemView.findViewById(R.id.large);
                smallText.setVisibility(View.INVISIBLE);
                //TextView largeText = (TextView) menuItemView.findViewById(R.id.largeLabel);
//                ImageView icon = (ImageView) menuItemView.findViewById(R.id.icon);
//                FrameLayout.LayoutParams params = (LayoutParams) icon.getLayoutParams();
//                params.gravity = Gravity.CENTER;
//                menuItemView.setShiftingMode(false);
            }
        }
    }

    private BottomNavigationMenuView getBottomMenuView() {
        Object menuView = null;
        try {
            Field field = BottomNavigationView.class.getDeclaredField("mMenuView");
            field.setAccessible(true);
            menuView = field.get(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (BottomNavigationMenuView) menuView;
    }
}