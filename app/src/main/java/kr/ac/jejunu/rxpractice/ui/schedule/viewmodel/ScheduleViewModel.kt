package kr.ac.jejunu.rxpractice.ui.schedule.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.google.gson.annotations.Until
import io.reactivex.BackpressureStrategy
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.domain.repository.ScheduleRepository
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.util.*

class ScheduleViewModel(
    private val repository: ScheduleRepository
) : BaseViewModel() {
    companion object {
        private val TAG = "ScheduleViewModel"
    }

    val scheduleLiveData: LiveData<List<Date>> =
        repository.getItem().toFlowable(BackpressureStrategy.BUFFER).toLiveData()
    val dayScheduleLiveData: LiveData<List<Schedule>> =
        repository.loadSchedule().toFlowable(BackpressureStrategy.BUFFER).toLiveData()

    fun getMonthSchedule() {
        repository.loadAllSchedule().subscribe({}, {
            Log.d(TAG, "${it.message}")
        }).let { addDisposable(it) }
    }

    fun getDaySchedule(date: Date) {
        repository.getSchedule(date).subscribe({}, {
            Log.d(TAG, "day schedule error : ${it.message}")
        }).let { addDisposable(it) }
    }

    val fabClick = SingleLiveEvent<Unit>()
    val fabSchedule = SingleLiveEvent<Until>()
    fun onFabClick() = fabClick.call()
    fun onFabSchedule() = fabSchedule.call()
}