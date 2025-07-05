package com.warusmart

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.warusmart.R
import com.warusmart.sowings.interfaces.SowingsHistoryActivity
import com.warusmart.sowings.interfaces.SowingsManagementActivity
import com.warusmart.stadistics.interfaces.WaterStatisticsActivity

/**
 * Main entry activity for the app. Handles navigation to statistics, sowings, and sowings history.
 */
class MainActivity : AppCompatActivity() {
    // Activity setup and button click listeners
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Button to go to StatisticsActivity
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            startActivity(newIntent(this))
        }

        val buttonSowings: Button = findViewById(R.id.button_sowings)
        buttonSowings.setOnClickListener {
            startActivity(newIntentSowings(this))
        }

        val buttonSowingsHistory: Button = findViewById(R.id.button_sowings_history)
        buttonSowingsHistory.setOnClickListener {
            startActivity(newIntentSowingsHistory(this))
        }
    }

    companion object {
        // Returns intent for WaterStatisticsActivity
        fun newIntent(context: Context): Intent {
            return Intent(context, WaterStatisticsActivity::class.java)
        }

        // Returns intent for SowingsManagementActivity
        fun newIntentSowings(context: Context): Intent {
            return Intent(context, SowingsManagementActivity::class.java)
        }

        // Returns intent for SowingsHistoryActivity
        fun newIntentSowingsHistory(context: Context): Intent {
            return Intent(context, SowingsHistoryActivity::class.java)
        }
    }
}