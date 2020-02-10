package kr.ac.jejunu.rxpractice.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id : Int ?=null,
    @ColumnInfo(name = "user_name")
    var userName : String,
    @ColumnInfo(name = "user_sex")
    var userSex :String ?=null,
    @ColumnInfo(name = "user_phone_num")
    var userNum : String
)