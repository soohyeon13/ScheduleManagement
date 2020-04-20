package kr.ac.jejunu.rxpractice.ui.addschedule.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.repository.ScheduleRepository
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class AddScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : BaseViewModel() {
    companion object {
        private val TAG = "AddScheduleViewModel"
    }
    private val cal = Calendar.getInstance()
    val findUser = SingleLiveEvent<Unit>()
    val selectDate = SingleLiveEvent<Unit>()
    val cancel = SingleLiveEvent<Unit>()
    val save = SingleLiveEvent<Unit>()
    fun onCancel() =cancel.call()
    fun onFindUser() = findUser.call()
    fun onSelectDate()= selectDate.call()

    @SuppressLint("SimpleDateFormat")
    fun insertSchedule(
        userName: String,
        number: String,
        date: String,
        etcDes: String?
    ) {
        val dateList = date.split(" ")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd").parse(dateList[0])
        val timeFormat = SimpleDateFormat("HH:mm").parse(dateList[1])
        val calDate = dateToCalendar(date)
        val year = calDate.get(Calendar.YEAR)
        val month = calDate.get(Calendar.MONTH) +1
        val yearAndMonth = "$year-$month"
        val schedule = Schedule(
            id = null,
            name = userName,
            date = dateFormat!!,
            time = timeFormat!!,
            month =yearAndMonth,
            phoneNum = number,
            reservationContent = "테스트입니다."
        )
        scheduleRepository.insertSchedule(schedule)
        save.call()
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateToCalendar(dateStr: String): Calendar {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val date = simpleDateFormat.parse(dateStr)
        cal.time = date
        return cal
    }

}
