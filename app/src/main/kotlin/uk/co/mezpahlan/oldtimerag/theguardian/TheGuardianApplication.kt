package uk.co.mezpahlan.oldtimerag.theguardian


import android.app.Application
import org.koin.android.ext.android.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.mezpahlan.oldtimerag.theguardian.di.appModules

/**
 * Application for TheGuardian. Sets up a custom typeface throughout the app.
 */
class TheGuardianApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, appModules)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Merriweather-Regular.ttf")
                .build()
        )
    }
}
