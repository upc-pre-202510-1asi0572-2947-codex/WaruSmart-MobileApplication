// SowingsHistoryActivity.kt
package com.warusmart.sowings.interfaces.activities

import com.warusmart.shared.application.DB.AppDataBase
import com.warusmart.shared.domain.model.Entities.Sowing
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.sowings.application.services.SowingsService
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity for displaying the history of all sowings.
 * Handles UI setup and loading of sowing records from the database and backend.
 */
class SowingsHistoryActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: AppDataBase
    private lateinit var sowingsService: SowingsService
    private var sowings: List<Sowing> = listOf()

    // Activity setup and initial data loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_sowings_history, findViewById(R.id.container))
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_history

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = AppDataBase.getDatabase(this)
        val token = SessionManager.token ?: ""
        sowingsService = SowingsService(this, token)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        observeSowings()
    }

    // Observes sowings from the database
    private fun observeSowings() {
        database.sowingDAO().getAllSowingsLive().observe(this, Observer { sowings ->
            sowings?.let {
                displaySowings(it)
            }
        })
    }

    // Displays the list of sowings in the RecyclerView
    private fun displaySowings(sowings: List<Sowing>) {
        recyclerView.adapter = object : RecyclerView.Adapter<SowingViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SowingViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sowing_history, parent, false)
                return SowingViewHolder(view)
            }

            override fun onBindViewHolder(holder: SowingViewHolder, position: Int) {
                val sowing = sowings[position]
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                holder.txtStartDate.text = "Start Date: ${dateFormat.format(sowing.startDate)}"
                holder.txtEndDate.text = "End Date: ${dateFormat.format(sowing.endDate)}"

                CoroutineScope(Dispatchers.IO).launch {
                    val crop = sowingsService.getCropById(sowing.cropId)
                    withContext(Dispatchers.Main) {
                        holder.txtCropName.text = crop?.name ?: "Unknown Crop"
                        Glide.with(holder.itemView.context).load(crop?.imageUrl).into(holder.imgCrop)
                    }
                }

                holder.btnSee.setOnClickListener {
                    showSowingDetailsDialog(sowing)
                }
            }

            override fun getItemCount(): Int {
                return sowings.size
            }
        }
    }

    // Shows a dialog with sowing details
    private fun showSowingDetailsDialog(sowing: Sowing) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_sowing_details, null)
        val txtCropName: TextView = dialogView.findViewById(R.id.txtCropName)
        val imgCrop: ImageView = dialogView.findViewById(R.id.imgCrop)
        val txtStartDate: TextView = dialogView.findViewById(R.id.txtStartDate)
        val txtEndDate: TextView = dialogView.findViewById(R.id.txtEndDate)
        val txtDescription: TextView = dialogView.findViewById(R.id.txtDescription)
        val txtPhenologicalPhase: TextView = dialogView.findViewById(R.id.txtPhenologicalPhase)
        val btnClose: Button = dialogView.findViewById(R.id.btnClose)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        txtStartDate.text = "Start Date: ${dateFormat.format(sowing.startDate)}"
        txtEndDate.text = "End Date: ${dateFormat.format(sowing.endDate)}"
        txtPhenologicalPhase.text = "Phenological Phase: ${sowing.phenologicalPhaseName}"

        CoroutineScope(Dispatchers.IO).launch {
            val crop = sowingsService.getCropById(sowing.cropId)
            withContext(Dispatchers.Main) {
                txtCropName.text = crop?.name ?: "Unknown Crop"
                txtDescription.text = crop?.description ?: "No description available"
                Glide.with(this@SowingsHistoryActivity).load(crop?.imageUrl).into(imgCrop)
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // ViewHolder for sowing items
    class SowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCropName: TextView = itemView.findViewById(R.id.txtCropName)
        val imgCrop: ImageView = itemView.findViewById(R.id.imgCrop)
        val txtStartDate: TextView = itemView.findViewById(R.id.txtStartDate)
        val txtEndDate: TextView = itemView.findViewById(R.id.txtEndDate)
        val btnSee: Button = itemView.findViewById(R.id.btnSee)
    }
}