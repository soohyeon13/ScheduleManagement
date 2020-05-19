package kr.ac.jejunu.rxpractice.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import kr.ac.jejunu.rxpractice.util.Converters
import java.util.*

@Entity(tableName = "schedule")
@TypeConverters(Converters::class)
@Parcelize
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id : Int? =1,
    var name: String? = "",
    var phoneNum : String? ="",
    var date : Date? = null,
    var time : Date? = null,
    var month : String? = "",
    @ColumnInfo(name = "reservation_content")
    var reservationContent : String? = "",
    var memo : String? = ""
): Parcelable