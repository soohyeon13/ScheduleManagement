package kr.ac.jejunu.rxpractice.domain.model

import java.util.*

data class TimeSchedule(
    val time : String,
    val name : String? = "",
    val date : Date? = null,
    val content : String? = ""
)