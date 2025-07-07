package com.warusmart.crops.interfaces.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.shared.domain.model.Entities.Product
import android.text.Html.fromHtml

/**
 * RecyclerView Adapter for displaying products in a list.
 */
class ProductAdapter(
    private val products: List<Product>,
    private val onEditClick: (Product) -> Unit,
    private val onDeleteClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    /**
     * Creates and returns a new ProductViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    /**
     * Returns the total number of products in the list.
     */
    override fun getItemCount(): Int {
        return products.size
    }

    /**
     * Binds the product data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        private val productTypeTextView: TextView = itemView.findViewById(R.id.productTypeTextView)
        private val productQuantityView: TextView = itemView.findViewById(R.id.productQuantityView)
        private val productDateView: TextView = itemView.findViewById(R.id.productDateView)
        private val editButton: ImageView = itemView.findViewById(R.id.editButton)
        private val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        /**
         * Binds the product data to the UI elements.
         */
        fun bind(product: Product) {
            productNameTextView.text = fromHtml("<b>Type</b> ${product.type}")
            productTypeTextView.text = fromHtml("<b>Name</b> ${product.name}")
            productQuantityView.text = fromHtml("<b>Quantity</b> ${product.quantity}")
            productDateView.text = fromHtml("<b>Date</b> ${product.date}")

            editButton.setOnClickListener { onEditClick(product) }
            deleteButton.setOnClickListener { onDeleteClick(product) }
        }
    }
}