package kr.ac.jejunu.rxpractice.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kr.ac.jejunu.rxpractice.data.response.Schedule
import java.util.*

@Parcelize
data class TimeSchedule(
    val time : String,
    val schedule : Schedule? = null
):Parcelable