package com.warusmart.shared.interfaces

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.warusmart.crops.interfaces.activities.ControlsActivity
import com.warusmart.crops.interfaces.activities.CropCareActivity
import com.warusmart.crops.interfaces.activities.DiseasesActivity
import com.warusmart.sowings.interfaces.activities.GeneralCropInfo
import com.warusmart.crops.interfaces.activities.ProductsActivity
import com.warusmart.R
import com.warusmart.sowings.interfaces.activities.SowingsHistoryActivity
import com.warusmart.forum.interfaces.activities.AnswersActivity
import com.warusmart.forum.interfaces.activities.ForumManagementActivity
import com.warusmart.iam.interfaces.activities.ProfileActivity
import com.warusmart.sowings.interfaces.activities.SowingsManagementActivity
import com.warusmart.stadistics.interfaces.activities.WaterStatisticsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.warusmart.sowings.interfaces.activities.DeviceActivity
import com.warusmart.stadistics.interfaces.activities.StadisticsActivity

/**
 * Base activity for main screens. Handles bottom navigation and activity switching.
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * Handles activity creation and bottom navigation setup.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        // Handles bottom navigation item selection
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (!isSowingsManagementActivity()) {
                        val intent = Intent(this, SowingsManagementActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    true
                }
                R.id.navigation_forum -> {
                    if (!isForumActivity()) {
                        val intent = Intent(this, ForumManagementActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    true
                }
                R.id.navigation_profile -> {
                    if (this !is ProfileActivity) {
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    true
                }
                R.id.navigation_history -> {
                    if (this !is SowingsHistoryActivity) {
                        val intent = Intent(this, SowingsHistoryActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    true
                }
                R.id.navigation_statistics -> {
                    if (this !is StadisticsActivity) {
                        val intent = Intent(this, StadisticsActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    true
                }
                else -> false
            }
        }
    }

    // Checks if current activity is a sowings management related activity
    /**
     * Checks if the current activity is SowingsManagementActivity.
     */
    private fun isSowingsManagementActivity(): Boolean {
        return this is SowingsManagementActivity || this is ControlsActivity || this is CropCareActivity || this is DiseasesActivity || this is ProductsActivity
                || this is GeneralCropInfo || this is DeviceActivity
    }

    // Checks if current activity is a forum related activity
    /**
     * Checks if the current activity is ForumManagementActivity or AnswersActivity.
     */
    private fun isForumActivity(): Boolean {
        return this is ForumManagementActivity || this is AnswersActivity
    }

    // Checks if current activity is the profile activity
    /**
     * Checks if the current activity is ProfileActivity.
     */
    private fun isProfileActivity(): Boolean {
        return this is ProfileActivity
    }

    // Handles bottom navigation item selection on resume
    override fun onResume() {
        super.onResume()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        when (this) {
            is SowingsManagementActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_home
            is SowingsHistoryActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_history
            is StadisticsActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_statistics
            is ProfileActivity -> bottomNavigationView.selectedItemId = R.id.navigation_profile
            is ForumManagementActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_forum
        }
    }
}