package com.example.warusmart.crops.interfaces

import com.example.warusmart.shared.application.DB.AppDataBase
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.warusmart.crops.application.adapters.ProductAdapter
import com.example.warusmart.shared.domain.model.Entities.Product
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import com.example.warusmart.sowings.interfaces.GeneralCropInfo
import com.example.warusmart.R
import com.example.warusmart.shared.interfaces.BaseActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Activity for displaying and managing products for a sowing.
 */
class ProductsActivity : BaseActivity() {
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var appDB: AppDataBase

    // Activity setup and initial data loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_products, findViewById(R.id.container))
        enableEdgeToEdge()

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        productRecyclerView = findViewById(R.id.productRecyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)

        appDB = AppDataBase.getDatabase(this)

        val sowingId = intent.getIntExtra("SOWING_ID", 1)
        Log.d("ProductsActivity", "Received sowingId: $sowingId")
        if (sowingId != -1) {
            fetchProductsBySowingId(sowingId)
        } else {
            Log.e("ProductsActivity", "Invalid sowing ID")
            Toast.makeText(this, "Invalid sowing ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        findViewById<Button>(R.id.addProductButton).setOnClickListener {
            showAddProductDialog()
        }

        setupSpinner()
    }

    // Fetches products from the database for a given sowing ID
    private fun fetchProductsBySowingId(sowingId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val products = appDB.productDAO().getProductsBySowingId(sowingId)
                withContext(Dispatchers.Main) {
                    productAdapter = ProductAdapter(products, ::onEditClick, ::onDeleteClick)
                    productRecyclerView.adapter = productAdapter
                }
            } catch (e: Exception) {
                Log.e("ProductsActivity", "Error fetching products: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductsActivity, "Error fetching products: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Shows dialog to add a new product
    private fun showAddProductDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_product, null)
        val productNameEditText = dialogView.findViewById<EditText>(R.id.edittext_product_name)
        val productQuantityEditText = dialogView.findViewById<EditText>(R.id.edittext_product_quantity)
        val productTypeSpinner = dialogView.findViewById<Spinner>(R.id.spinner_product_type)
        val addProductButton = dialogView.findViewById<Button>(R.id.addProductButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        val productTypes = arrayOf("Fertilizer", "Pesticide", "Herbicide", "Fungicide", "Insecticide", "None", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productTypeSpinner.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Add Product")
            .create()

        addProductButton.setOnClickListener {
            val sowingId = intent.getIntExtra("SOWING_ID", -1)
            val productName = productNameEditText.text.toString()
            val productQuantity = productQuantityEditText.text.toString().toFloat()
            val productType = productTypeSpinner.selectedItem.toString()
            val date = Date()
            addProduct(sowingId, productName, productType, productQuantity, date)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // Adds a new product to the database
    private fun addProduct(sowingId: Int, productName: String, productType: String, productQuantity: Float, date: Date) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val newProduct = Product(
                    id = 0,
                    sowingId = sowingId,
                    name = productName,
                    type = productType,
                    quantity = productQuantity,
                    date = date
                )
                appDB.productDAO().insertProduct(newProduct)
                fetchProductsBySowingId(sowingId)
            } catch (e: Exception) {
                Log.e("ProductsActivity", "Error adding product: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductsActivity, "Error adding product: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Handles edit action for a product
    private fun onEditClick(product: Product) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_product, null)
        val productNameEditText = dialogView.findViewById<EditText>(R.id.edittext_product_name)
        val productQuantityEditText = dialogView.findViewById<EditText>(R.id.edittext_product_quantity)
        val productTypeSpinner = dialogView.findViewById<Spinner>(R.id.spinner_product_type)
        val addProductButton = dialogView.findViewById<Button>(R.id.addProductButton)
        val cancelButton = dialogView.findViewById<Button>(R.id.button_cancel)

        val productTypes = arrayOf("Fertilizer", "Pesticide", "Herbicide", "Fungicide", "Insecticide", "None", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, productTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productTypeSpinner.adapter = adapter

        productNameEditText.setText(product.name)
        productQuantityEditText.setText(product.quantity.toString())
        productTypeSpinner.setSelection((productTypeSpinner.adapter as ArrayAdapter<String>).getPosition(product.type))

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Edit Product")
            .create()

        addProductButton.setOnClickListener {
            product.name = productNameEditText.text.toString()
            product.quantity = productQuantityEditText.text.toString().toFloat()
            product.type = productTypeSpinner.selectedItem.toString()
            updateProduct(product)
            dialog.dismiss()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    // Updates a product in the database
    private fun updateProduct(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDB.productDAO().updateProduct(product)
                fetchProductsBySowingId(product.sowingId)
            } catch (e: Exception) {
                Log.e("ProductsActivity", "Error updating product: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductsActivity, "Error updating product: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Handles delete action for a product
    private fun onDeleteClick(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDB.productDAO().deleteProduct(product.id)
                fetchProductsBySowingId(product.sowingId)
            } catch (e: Exception) {
                Log.e("ProductsActivity", "Error deleting product: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProductsActivity, "Error deleting product: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Sets up the spinner for navigation between crop info screens
    private fun setupSpinner() {
        val spinner: Spinner = findViewById(R.id.dropdown_menu)
        ArrayAdapter.createFromResource(
            this,
            R.array.crop_info_options,
            R.layout.spinner_item_white_text
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_item_white_text)
            spinner.adapter = adapter
        }

        var isFirstSelection = true

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (isFirstSelection) {
                    isFirstSelection = false
                    return
                }
                view?.let {
                    val cropId = intent.getIntExtra("CROP_ID", -1)
                    val sowingId = intent.getIntExtra("SOWING_ID", -1)
                    when (position) {
                        0 -> startActivity(Intent(this@ProductsActivity, GeneralCropInfo::class.java).apply {
                            putExtra("SOWING_ID", sowingId)
                        })
                        1 -> startActivity(Intent(this@ProductsActivity, DeviceActivity::class.java).apply {
                            putExtra("SOWING_ID", sowingId)
                            putExtra("CROP_ID", cropId)
                        })
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }
        val productPosition = resources.getStringArray(R.array.crop_info_options).indexOf("Products Used")
        spinner.setSelection(productPosition)
    }
}
