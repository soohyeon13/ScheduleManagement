package kr.ac.jejunu.rxpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "sales")
data class Sales(
    @PrimaryKey
    val id : Int,
    var money : Int,
    var month : String
):Serializable