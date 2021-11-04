package com.geermank.userslist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.geermank.userslist.data.model.UserDto

private const val CURRENT_DATABASE_VERSION = 1
private const val DATABASE_NAME = "AppDatabase"

@Database(
    entities = [UserDto::class],
    version = CURRENT_DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context) = sInstance ?: synchronized(this) {
            val newInstance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
            sInstance = newInstance
            return newInstance
        }
    }

    abstract fun usersDao(): UsersDao
}