package br.com.leandro.podcast.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.leandro.podcast.core.database.dao.HistoryDao
import br.com.leandro.podcast.core.database.entity.History

/**
 * The Room database for this app
 *
 * This class represents the database itself. It is an abstract class that extends RoomDatabase and
 * has a companion object that provides a getInstance method to get the database instance.
 */
@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance!!
        }

        private const val DATABASE_NAME = "app-database.db"
    }
}