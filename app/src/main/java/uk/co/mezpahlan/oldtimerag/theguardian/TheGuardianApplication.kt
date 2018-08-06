package uk.co.mezpahlan.oldtimerag.theguardian


import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Application for TheGuardian. Sets up a custom typeface throughout the app.
 */
class TheGuardianApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Merriweather-Regular.ttf")
                .build()
        )
    }
}
