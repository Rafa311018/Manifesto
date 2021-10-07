package com.example.manifesto.data.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GuestEntity::class], version = 1, exportSchema = false)
abstract class GuestDatabase : RoomDatabase() {

    abstract val guestDatabaseDao: GuestDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GuestDatabase? = null
        fun getInstanceGuestDB(context: Context) : GuestDatabase{
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GuestDatabase::class.java,
                        "guest_history_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}