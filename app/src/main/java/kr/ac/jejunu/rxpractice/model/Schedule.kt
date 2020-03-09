package kr.ac.jejunu.rxpractice.model

import androidx.room.*
import kr.ac.jejunu.rxpractice.util.Converters
import java.io.Serializable
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
    @ColumnInfo(name = "descriptions")
    var descriptions: List<Description>,
    @ColumnInfo(name = "cal")
    var cal: Calendar?,
    @ColumnInfo(name = "date")
    var date: String
):Serializable

@Entity(tableName = "description")
data class Description(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    var description: String
):Serializable