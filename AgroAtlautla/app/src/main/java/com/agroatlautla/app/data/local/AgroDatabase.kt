package com.agroatlautla.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        UserEntity::class,
        CropEntity::class,
        CalendarActivityEntity::class,
        PestEntity::class,
        ExpenseEntity::class,
        SyncQueueEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AgroDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cropDao(): CropDao
    abstract fun calendarActivityDao(): CalendarActivityDao
    abstract fun pestDao(): PestDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun syncQueueDao(): SyncQueueDao

    companion object {
        @Volatile
        private var instance: AgroDatabase? = null

        private val Migration1To2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `expenses` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `concept` TEXT NOT NULL,
                        `amount` INTEGER NOT NULL,
                        `date` TEXT NOT NULL,
                        `category` TEXT NOT NULL,
                        `needsSync` INTEGER NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): AgroDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AgroDatabase::class.java,
                    "agroatlautla.db"
                )
                    .addMigrations(Migration1To2)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
