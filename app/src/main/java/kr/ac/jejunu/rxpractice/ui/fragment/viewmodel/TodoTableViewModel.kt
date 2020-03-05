package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.fragment.viewmodel.itemviewmodel.TodoTableItemViewModel

class TodoTableViewModel(application: Application) : BaseViewModel<Schedule>(application) {
    private val itemViewModel = TodoTableItemViewModel()
    private var schedules :List<Schedule> = listOf()
    private val repository : RoomRepository by lazy {
        RoomRepository(application)
    }
    init {

    }

    fun getSchedule(selectDay : String) {
        schedules = repository.getSchedules(selectDay)
        for (i in 0..7) {

        }
    }
}