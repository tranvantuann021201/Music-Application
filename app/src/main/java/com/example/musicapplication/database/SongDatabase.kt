package com.example.musicapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Created by Bkav HuyNgQe on 16/06/2022.
 */
@Database(entities = [SongTable::class], version = 1, exportSchema = false)
abstract class SongDatabase: RoomDatabase() {
      abstract val favoriteSongsDatabaseDAO: SongDatabaseDao
      companion object{
            @Volatile
            private var INSTANCE: SongDatabase? = null
            fun getInstance(context: Context): SongDatabase {
                  synchronized(this){
                        var instance = INSTANCE
                        if (instance == null){
                              instance = Room.databaseBuilder(
                                    context.applicationContext,
                                    SongDatabase::class.java,
                                    "favorite_songs_database")
                                    .fallbackToDestructiveMigration()
                                    .allowMainThreadQueries()
                                    .build()
                              INSTANCE = instance
                        }
                        return instance
                  }
            }
      }
}