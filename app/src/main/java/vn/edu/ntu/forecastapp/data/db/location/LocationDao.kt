package vn.edu.ntu.forecastapp.data.db.location

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(locationDatabase: LocationDatabase)

    @Query("select * from location_database")
    fun getLocation():LiveData<LocationDatabase>
}