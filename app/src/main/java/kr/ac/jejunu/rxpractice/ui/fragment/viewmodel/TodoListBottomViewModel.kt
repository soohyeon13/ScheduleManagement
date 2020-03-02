package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.ui.adapter.TodoListAdapter

class TodoListBottomViewModel(application: Application) : AndroidViewModel(application) {
    val adapter = TodoListAdapter()
    private val repository:RoomRepository by lazy {
        RoomRepository(application)
    }

    fun getScheduleData(date :String) {
        adapter.setSchedules(repository.getSchedules(date))
    }
}