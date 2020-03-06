package kr.ac.jejunu.rxpractice.model

import java.io.Serializable

data class Sales(
    val id : Int,
    var money : Int,
    var month : String
):Serializable