package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.ui.adapter.TodoListAdapter
import kr.ac.jejunu.rxpractice.util.OnItemClickListener
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent

class TodoListBottomViewModel(application: Application) : AndroidViewModel(application),OnItemClickListener<Schedule> {
    val adapter = TodoListAdapter(this)
    val clickEvent = SingleLiveEvent<Schedule>()
    val cancelClickEvent = SingleLiveEvent<Unit>()
    private val repository:RoomRepository by lazy {
        RoomRepository(application)
    }

    fun getScheduleData(date :String) {
        adapter.setSchedules(repository.getSchedules(date))
    }

    override fun onItemClick(data: Schedule) {
        clickEvent.value = data
    }

    fun onCancelClick() {
        cancelClickEvent.call()
    }
}