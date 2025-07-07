package com.warusmart.shared.infrastructure.DAO

import com.warusmart.shared.domain.model.Entities.Sowing
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * DAO for accessing Sowing entities in the database.
 * Provides methods for insert, query, delete, update, and live data operations.
 */
@Dao
interface SowingDAO {
    /**
     * Gets all sowings from the database.
     */
    @Query("SELECT * FROM sowing")
    fun getAllSowings(): List<Sowing>

    /**
     * Gets a sowing by its ID.
     */
    @Query("SELECT * FROM sowing WHERE id = :sowingId")
    fun getSowingById(sowingId: Int): Sowing?

    /**
     * Inserts a sowing into the database.
     */
    @Insert
    fun insertSowing(sowing: Sowing)

    /**
     * Deletes a sowing by its ID.
     */
    @Query("DELETE FROM sowing WHERE id = :sowingId")
    fun deleteSowingById(sowingId: Int)

    /**
     * Updates a sowing in the database.
     */
    @Update
    fun updateSowing(sowing: Sowing)

    /**
     * Gets all sowings with status = 1 as LiveData.
     */
    @Query("SELECT * FROM sowing WHERE status = 1")
    fun getAllSowingsLive(): LiveData<List<Sowing>>
}