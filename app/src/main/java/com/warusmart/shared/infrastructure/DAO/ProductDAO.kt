package com.warusmart.shared.infrastructure.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.warusmart.shared.domain.model.Entities.Product
import androidx.room.Update

/**
 * DAO for accessing Product entities in the database.
 * Provides methods for insert, query, delete, and update operations.
 */
@Dao
interface ProductDAO {
    /**
     * Inserts a product into the database.
     */
    @Insert
    fun insertProduct(product: Product)

    /**
     * Gets all products by sowingId.
     */
    @Query("SELECT * FROM products WHERE sowingId = :sowingId")
    fun getProductsBySowingId(sowingId: Int): List<Product>

    /**
     * Deletes a product by its ID.
     */
    @Query("DELETE FROM products WHERE id = :productId")
    fun deleteProduct(productId: Int)

    /**
     * Updates a product in the database.
     */
    @Update
    fun updateProduct(product: Product)
}
