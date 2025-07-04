/**
 * Main Room database class. Holds DAOs for Sowing, Control, and Product entities.
 * Uses singleton pattern to provide a single database instance.
 */

package DB

import DAO.ControlDAO
import DAO.ProductDAO
import DAO.SowingDAO
import Entities.Sowing
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import Entities.Control
import Entities.Product
import com.example.waruSmart_appmovil_android.sowingsManagement.domain.model.DateConverter

@Database(entities = [Sowing::class, Control::class, Product::class], version = 5, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {
    // Returns DAO for Sowing entity
    abstract fun sowingDAO(): SowingDAO
    // Returns DAO for Control entity
    abstract fun controlDAO(): ControlDAO
    // Returns DAO for Product entity
    abstract fun productDAO(): ProductDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        // Returns the singleton database instance.
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