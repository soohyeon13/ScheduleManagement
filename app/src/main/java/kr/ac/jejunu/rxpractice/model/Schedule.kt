package kr.ac.jejunu.rxpractice.model

import androidx.room.*
import kr.ac.jejunu.rxpractice.util.Converters
import java.sql.Date
import java.util.*

@Entity(tableName = "schedule")
@TypeConverters(Converters::class)
data class Schedule(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "cal_date")
    var date: Calendar?
)