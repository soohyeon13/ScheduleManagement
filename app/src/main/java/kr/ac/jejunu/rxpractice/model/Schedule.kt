package kr.ac.jejunu.rxpractice.model

import androidx.room.*
import java.sql.Date

@Entity(
    tableName = "schedule", indices = [Index("user_id")], foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("user_id"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Schedule(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "user_id") var userId: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "time") var time: String
)