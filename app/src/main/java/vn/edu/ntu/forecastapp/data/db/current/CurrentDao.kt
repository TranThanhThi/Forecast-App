package vn.edu.ntu.forecastapp.data.db.current

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vn.edu.ntu.forecastapp.data.model.CurrentModel

@Dao
interface CurrentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(currentDatabase: CurrentDatabase)

    @Query("select * from current_database where id =0 ")
    fun getAll():LiveData<CurrentModel>
}