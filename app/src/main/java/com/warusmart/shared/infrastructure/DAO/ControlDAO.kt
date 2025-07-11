package com.warusmart.shared.infrastructure.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.warusmart.shared.domain.model.Entities.Control
import androidx.room.Update

/**
 * DAO for accessing Control entities in the database.
 * Provides methods for insert, query, delete, update, and fetch all operations.
 */
@Dao
interface ControlDAO {
    /**
     * Inserts a control into the database.
     */
    @Insert
    fun insertControl(control: Control)

    /**
     * Gets all controls by sowingId.
     */
    @Query("SELECT * FROM controls WHERE sowingId = :sowingId")
    fun getControlsBySowingId(sowingId: Int): List<Control>

    /**
     * Deletes a control by its ID.
     */
    @Query("DELETE FROM controls WHERE id = :controlId")
    fun deleteControl(controlId: Int)

    /**
     * Updates a control in the database.
     */
    @Update
    fun updateControl(control: Control)

    /**
     * Gets all controls from the database.
     */
    @Query("SELECT * FROM controls")
    fun getAllControls(): List<Control>
}