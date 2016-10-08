package uk.co.mezpahlan.jamesedwardtaylorstheguardian.theguardian;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.mezpahlan.jamesedwardtaylorstheguardian.R;

/**
 * Application for TheGuardian. Sets up a custom typeface throughout the app.
 */
public class TheGuardianApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Merriweather-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
