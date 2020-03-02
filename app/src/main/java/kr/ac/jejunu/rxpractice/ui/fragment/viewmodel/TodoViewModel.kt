package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TodoViewModel(application: Application) : BaseViewModel<List<Schedule>>(application) {
    val clickFab = SingleLiveEvent<Unit>()

    fun onFabAddClick() {
        clickFab.call()
    }
}