package com.example.waruSmart_appmovil_android.shared.interfaces

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.waruSmart_appmovil_android.crops.interfaces.ControlsActivity
import com.example.waruSmart_appmovil_android.crops.interfaces.CropCareActivity
import com.example.waruSmart_appmovil_android.crops.interfaces.DiseasesActivity
import com.example.waruSmart_appmovil_android.sowings.interfaces.GeneralCropInfo
import com.example.waruSmart_appmovil_android.crops.interfaces.ProductsActivity
import com.example.waruSmart_appmovil_android.R
import com.example.waruSmart_appmovil_android.sowings.interfaces.SowingsHistoryActivity
import com.example.waruSmart_appmovil_android.forum.interfaces.AnswersActivity
import com.example.waruSmart_appmovil_android.forum.interfaces.ForumManagementActivity
import com.example.waruSmart_appmovil_android.iam.interfaces.ProfileActivity
import com.example.waruSmart_appmovil_android.sowings.interfaces.SowingsManagementActivity
import com.example.waruSmart_appmovil_android.stadistics.interfaces.WaterStatisticsActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Base activity for main screens. Handles bottom navigation and activity switching.
 */
open class BaseActivity : AppCompatActivity() {
    // Handles activity creation and bottom navigation setup
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
                    if (this !is WaterStatisticsActivity) {
                        val intent = Intent(this, WaterStatisticsActivity::class.java)
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
    private fun isSowingsManagementActivity(): Boolean {
        return this is SowingsManagementActivity || this is ControlsActivity || this is CropCareActivity || this is DiseasesActivity || this is ProductsActivity
                || this is GeneralCropInfo
    }

    // Checks if current activity is a forum related activity
    private fun isForumActivity(): Boolean {
        return this is ForumManagementActivity || this is AnswersActivity
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
            is WaterStatisticsActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_statistics
            is ProfileActivity -> bottomNavigationView.selectedItemId = R.id.navigation_profile
            is ForumManagementActivity -> bottomNavigationView.selectedItemId =
                R.id.navigation_forum
        }
    }
}