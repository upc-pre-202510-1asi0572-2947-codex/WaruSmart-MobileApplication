/**
 * Main Room database class. Holds DAOs for Sowing, Control, and Product entities.
 * Uses singleton pattern to provide a single database instance.
 */

package com.warusmart.shared.application.DB

import com.warusmart.shared.infrastructure.DAO.ControlDAO
import com.warusmart.shared.infrastructure.DAO.ProductDAO
import com.warusmart.shared.infrastructure.DAO.SowingDAO
import com.warusmart.shared.domain.model.Entities.Sowing
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.warusmart.shared.domain.model.Entities.Control
import com.warusmart.shared.domain.model.Entities.Product
import com.warusmart.sowings.domain.model.DateConverter

@Database(entities = [Sowing::class, Control::class, Product::class], version = 5, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    /**
     * Returns DAO for Sowing entity.
     */
    abstract fun sowingDAO(): SowingDAO
    /**
     * Returns DAO for Control entity.
     */
    abstract fun controlDAO(): ControlDAO
    /**
     * Returns DAO for Product entity.
     */
    abstract fun productDAO(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        /**
         * Returns the singleton database instance.
         */
        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration() // Allow destructive migrations
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}