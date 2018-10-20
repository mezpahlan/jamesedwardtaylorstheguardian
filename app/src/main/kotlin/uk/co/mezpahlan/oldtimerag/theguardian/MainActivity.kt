package uk.co.mezpahlan.oldtimerag.theguardian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import uk.co.mezpahlan.oldtimerag.R

/**
 * Main UI Controller for the application.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.navHostFragment)
        setupActionBarWithNavController(navController)
        bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.navHostFragment).navigateUp()
}