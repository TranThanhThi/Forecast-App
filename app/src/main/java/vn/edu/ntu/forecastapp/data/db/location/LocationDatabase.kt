package vn.edu.ntu.forecastapp.data.db.location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

const val LOCATION_ID = 0

@Entity(tableName = "location_database")
data class LocationDatabase(
    @PrimaryKey(autoGenerate = false)
    var id: Int = LOCATION_ID,
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtimeEpoch: Long,
    val lon: Double,
    val name: String,
    val region: String,
    val timezoneId: String,
    val utcOffset: String
){
    val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(localtimeEpoch)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant,zoneId)
        }
}
