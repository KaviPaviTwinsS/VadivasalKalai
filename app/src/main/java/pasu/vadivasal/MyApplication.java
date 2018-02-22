package pasu.vadivasal;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by developer on 19/12/17.
 */

public class MyApplication extends Application {
    private static MyApplication appContext;

    public static MyApplication getInstance() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        Utilities.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Fresco.initialize(getApplicationContext());
    }
}