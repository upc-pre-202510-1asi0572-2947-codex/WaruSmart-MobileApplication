package com.warusmart.shared.infrastructure.DAO

import com.warusmart.shared.domain.model.Entities.Sowing
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SowingDAO {
    @Query("SELECT * FROM sowing")
    fun getAllSowings(): List<Sowing>

    @Query("SELECT * FROM sowing WHERE id = :sowingId")
    fun getSowingById(sowingId: Int): Sowing?

    @Insert
    fun insertSowing(sowing: Sowing)

    @Query("DELETE FROM sowing WHERE id = :sowingId")
    fun deleteSowingById(sowingId: Int)

    @Update
    fun updateSowing(sowing: Sowing)

        @Query("SELECT * FROM sowing WHERE status = 1")
        fun getAllSowingsLive(): LiveData<List<Sowing>>

}