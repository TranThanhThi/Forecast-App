package vn.edu.ntu.forecastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vn.edu.ntu.forecastapp.data.db.current.CurrentDao
import vn.edu.ntu.forecastapp.data.db.current.CurrentDatabase
import vn.edu.ntu.forecastapp.data.db.location.LocationDao
import vn.edu.ntu.forecastapp.data.db.location.LocationDatabase


@Database(entities = [CurrentDatabase::class,LocationDatabase::class],version = 1,exportSchema = false)
abstract class DBCurrent:RoomDatabase() {
    abstract val currentDao: CurrentDao
    abstract val locationDao:LocationDao

    companion object{
        @Volatile
        private var INSTANCE: DBCurrent?=null
        fun getInstance(context: Context): DBCurrent {
            synchronized(this){
                var instance =
                    INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DBCurrent::class.java,
                        "db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}