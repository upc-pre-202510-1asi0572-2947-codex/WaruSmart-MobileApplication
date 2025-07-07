package com.warusmart.sowings.interfaces.activities

import com.warusmart.shared.application.DB.AppDataBase
import com.warusmart.shared.domain.model.Entities.Sowing
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import com.bumptech.glide.Glide
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.iam.interfaces.activities.SignInActivity
import com.warusmart.sowings.application.services.SowingsService
import com.warusmart.sowings.domain.model.Crop
import com.warusmart.sowings.domain.model.SowingDos
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/**
 * Activity for managing the user's sowings and crops.
 */
class SowingsManagementActivity : BaseActivity() {

    private lateinit var sowingsService: SowingsService
    private lateinit var sowingsContainer: LinearLayout
    private lateinit var addCropButton: Button
    private lateinit var crops: List<Crop>
    private lateinit var appDB: AppDataBase

    // Activity setup and initial data loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_sowings_management, findViewById(R.id.container))

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        val token = SessionManager.token
        if (token != null) {
            sowingsService = SowingsService(this, token)
        } else {
            Toast.makeText(this, "Token not found. Please log in again.", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
        sowingsContainer = findViewById(R.id.sowings_container)
        addCropButton = findViewById(R.id.button_add_crop)

        appDB = AppDataBase.getDatabase(this)

        fetchAndDisplaySowings()

        addCropButton.setOnClickListener {
            if (::crops.isInitialized) {
                showAddSowingDialog()
            } else {
                Toast.makeText(this, "Crops data is not yet loaded. Please wait.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fetches sowings from the backend and displays them
    private fun fetchAndDisplaySowings() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userId = SessionManager.profileId ?: -1
                if (userId == -1) {
                    Log.e("SowingsManagement", "User ID not found")
                    return@launch
                }

                val sowings = sowingsService.getSowingsByUserId(userId) // desde backend
                Log.d("SowingsManagement", "Sowings from backend: ${sowings.joinToString("\n")}")

                crops = sowingsService.getAllCrops()
                val cropMap = crops.associateBy { it.id }

                withContext(Dispatchers.Main) {
                    displaySowingsDos(sowings, cropMap) // nueva función adaptada para SowingDos
                }
            } catch (e: Exception) {
                Log.e("SowingsManagement", "Error fetching sowings: ${e.message}")
            }
        }
    }

    // Displays sowings using SowingDos model
    private fun displaySowingsDos(sowings: List<SowingDos>, cropMap: Map<Int, Crop>) {
        sowingsContainer.removeAllViews()
        sowings.forEach { sowing ->
            val cardView = LayoutInflater.from(this).inflate(R.layout.item_sowing, sowingsContainer, false)

            val imgCrop = cardView.findViewById<ImageView>(R.id.imgCrop)
            val txtCropName = cardView.findViewById<TextView>(R.id.txtCropName)
            val txtPhenologicalPhaseName = cardView.findViewById<TextView>(R.id.txtPhenologicalPhaseName)
            val txtStartDate = cardView.findViewById<TextView>(R.id.txtStartDate)
            val txtEndDate = cardView.findViewById<TextView>(R.id.txtEndDate)
            val txtArea = cardView.findViewById<TextView>(R.id.txtArea)

            val crop = cropMap[sowing.cropId]
            if (crop != null) {
                Glide.with(this).load(crop.imageUrl).into(imgCrop)
                txtCropName.text = crop.name
            } else {
                imgCrop.setImageResource(R.drawable.ic_launcher_background)
                txtCropName.text = "Unknown"
            }

            txtPhenologicalPhaseName.text = sowing.phenologicalPhaseName ?: "Sincronizado"
            txtStartDate.text = sowing.startDate?.substring(0, 10) ?: "-"
            txtEndDate.text = sowing.endDate?.substring(0, 10) ?: "-"
            txtArea.text = "${sowing.areaLand} m²"

            sowingsContainer.addView(cardView)
        }
    }

    // Displays sowings using Sowing model
    private fun displaySowings(sowings: List<Sowing>, cropMap: Map<Int, Crop>) {
        sowingsContainer.removeAllViews()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sowings.forEach { sowing ->
            val cardView = LayoutInflater.from(this).inflate(R.layout.item_sowing, sowingsContainer, false)

            val imgCrop = cardView.findViewById<ImageView>(R.id.imgCrop)
            val txtCropName = cardView.findViewById<TextView>(R.id.txtCropName)
            val txtPhenologicalPhaseName = cardView.findViewById<TextView>(R.id.txtPhenologicalPhaseName)
            val txtStartDate = cardView.findViewById<TextView>(R.id.txtStartDate)
            val txtEndDate = cardView.findViewById<TextView>(R.id.txtEndDate)
            val txtArea = cardView.findViewById<TextView>(R.id.txtArea)
            val imgTrashIcon = cardView.findViewById<ImageView>(R.id.imgTrashIcon)
            val imgViewIcon = cardView.findViewById<ImageView>(R.id.imgViewIcon)
            val imgEditIcon = cardView.findViewById<ImageView>(R.id.imgEditIcon)
            val imgPhaseIcon = cardView.findViewById<ImageView>(R.id.imgPhaseIcon)

            val crop = cropMap[sowing.cropId]
            if (crop != null) {
                Glide.with(this).load(crop.imageUrl).into(imgCrop)
                txtCropName.text = crop.name
            } else {
                imgCrop.setImageResource(R.drawable.ic_launcher_background)
                txtCropName.text = "Unknown"
            }

            txtPhenologicalPhaseName.text = sowing.phenologicalPhaseName
            txtStartDate.text = dateFormat.format(sowing.startDate)
            txtEndDate.text = dateFormat.format(sowing.endDate)
            txtArea.text = "${sowing.areaLand} m²"

            imgViewIcon.setOnClickListener {
                Log.d("SowingsManagement", "Viewing details for sowing ID: ${sowing.id}")
                val sowingId = sowing.id
                val cropId= sowing.cropId

                // Intent for GeneralCropInfo
                val generalCropInfoIntent = Intent(this, GeneralCropInfo::class.java).apply {
                    putExtra("SOWING_ID", sowingId)
                    putExtra("CROP_ID", cropId)
                }
                startActivity(generalCropInfoIntent)
            }

            imgTrashIcon.setOnClickListener {
                showDeleteSowingDialog(sowing.id)
            }

            imgEditIcon.setOnClickListener {
                showUpdateSowingDialog(sowing)
            }

            imgPhaseIcon.setOnClickListener {
                showUpdatePhenologicalPhaseDialog(sowing.id)
            }

            sowingsContainer.addView(cardView)
        }
    }

    // Shows dialog to add a new sowing
    private fun showAddSowingDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_sowing, null)
        val cropSpinner: Spinner = dialogView.findViewById(R.id.spinner_crop_names)
        val areaEditText: EditText = dialogView.findViewById(R.id.edittext_area)
        val cancelButton: Button = dialogView.findViewById(R.id.button_cancel)
        val addButton: Button = dialogView.findViewById(R.id.button_add)

        val cropNames = crops.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cropNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cropSpinner.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        addButton.setOnClickListener {
            val selectedCropName = cropSpinner.selectedItem.toString()
            val selectedCrop = crops.find { it.name == selectedCropName }
            val area = areaEditText.text.toString().toIntOrNull()

            if (selectedCrop != null && area != null) {
                addSowing(selectedCrop.id, area)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please select a valid crop and enter a valid area.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // Shows dialog to confirm sowing deletion
    private fun showDeleteSowingDialog(sowingId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete_sowing, null)
        val yesButton: Button = dialogView.findViewById(R.id.button_yes)
        val noButton: Button = dialogView.findViewById(R.id.button_no)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        yesButton.setOnClickListener {
            deleteSowing(sowingId)
            dialog.dismiss()
        }

        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // Shows dialog to update a sowing
    private fun showUpdateSowingDialog(sowing: Sowing) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_sowing, null)
        val cropSpinner: Spinner = dialogView.findViewById(R.id.spinner_crop_names)
        val areaEditText: EditText = dialogView.findViewById(R.id.edittext_area)
        val startDateButton: Button = dialogView.findViewById(R.id.button_start_date)
        val cancelButton: Button = dialogView.findViewById(R.id.button_cancel)
        val updateButton: Button = dialogView.findViewById(R.id.button_update)

        val cropNames = crops.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cropNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cropSpinner.adapter = adapter

        val selectedCrop = crops.find { it.id == sowing.cropId }
        cropSpinner.setSelection(cropNames.indexOf(selectedCrop?.name))
        areaEditText.setText(sowing.areaLand.toString())

        val calendar = Calendar.getInstance().apply {
            time = sowing.startDate
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        startDateButton.text = dateFormat.format(calendar.time)

        startDateButton.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                startDateButton.text = dateFormat.format(calendar.time)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        updateButton.setOnClickListener {
            val selectedCropName = cropSpinner.selectedItem.toString()
            val selectedCrop = crops.find { it.name == selectedCropName }
            val area = areaEditText.text.toString().toIntOrNull()

            if (selectedCrop != null && area != null) {
                val startDate = calendar.time
                val endDate = Calendar.getInstance().apply {
                    time = startDate
                    add(Calendar.MONTH, 6)
                }.time

                val updatedSowing = sowing.copy(
                    cropId = selectedCrop.id,
                    areaLand = area,
                    startDate = startDate,
                    endDate = endDate
                )
                updateSowing(updatedSowing)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please select a crop and enter a valid area", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    // Shows dialog to update phenological phase
    private fun showUpdatePhenologicalPhaseDialog(sowingId: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_update_phenological_phase, null)
        val yesButton: Button = dialogView.findViewById(R.id.button_yes)
        val noButton: Button = dialogView.findViewById(R.id.button_no)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        yesButton.setOnClickListener {
            updatePhenologicalPhaseBySowingId(sowingId)
            dialog.dismiss()
        }

        noButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // Updates the phenological phase of a sowing
    private fun updatePhenologicalPhaseBySowingId(sowingId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val sowing = appDB.sowingDAO().getSowingById(sowingId)
                if (sowing != null) {
                    var updatedSowing = sowing.copy(
                        phenologicalPhase = (sowing.phenologicalPhase + 1) % 5,
                        phenologicalPhaseName = when ((sowing.phenologicalPhase + 1) % 5) {
                            0 -> "Germination"
                            1 -> "Seedling"
                            2 -> "Vegetative Growth"
                            3 -> "Flowering"
                            4 -> "Harvest Ready"
                            else -> sowing.phenologicalPhaseName
                        }
                    )
                    if (updatedSowing.phenologicalPhaseName == "Harvest Ready") {
                        updatedSowing = updatedSowing.copy(status = true)
                    }
                    appDB.sowingDAO().updateSowing(updatedSowing)
                    withContext(Dispatchers.Main) {
                        fetchAndDisplaySowings()
                    }
                }
            } catch (e: Exception) {
                Log.e("SowingsManagement", "Error updating phenological phase: ${e.message}")
            }
        }
    }

    // Deletes a sowing from the database
    private fun deleteSowing(sowingId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDB.sowingDAO().deleteSowingById(sowingId)
                fetchAndDisplaySowings()
            } catch (e: Exception) {
                Log.e("SowingsManagement", "Error deleting sowing: ${e.message}")
            }
        }
    }

    // Updates a sowing in the database
    private fun updateSowing(sowing: Sowing) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDB.sowingDAO().updateSowing(sowing)
                fetchAndDisplaySowings()
            } catch (e: Exception) {
                Log.e("SowingsManagement", "Error updating sowing: ${e.message}")
            }
        }
    }

    // Adds a new sowing to the backend
    private fun addSowing(cropId: Int, area: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userId = SessionManager.profileId ?: -1
                if (userId == -1) {
                    Log.e("SowingsManagement", "User ID not found")
                    return@launch
                }

                val sowingDto = SowingDos(
                    areaLand = area,
                    cropId = cropId,
                    userId = userId
                )
                sowingsService.addSowingRemote(sowingDto)
                fetchAndDisplaySowings()
            } catch (e: Exception) {
                Log.e("SowingsManagement", "Error adding sowing: ${e.message}")
            }
        }
    }

}