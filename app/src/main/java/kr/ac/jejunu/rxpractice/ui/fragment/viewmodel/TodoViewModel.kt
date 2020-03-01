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
    private val _items = MutableLiveData<List<Schedule>>()
    val items: LiveData<List<Schedule>> get() = _items
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date
    val clickDate = SingleLiveEvent<Unit>()
    val clickFab = SingleLiveEvent<Unit>()

    private val repository: RoomRepository by lazy {
        RoomRepository(application)
    }
    private val todos: Single<List<Schedule>> by lazy {
        repository.getSchedules(getToday())
    }

    init {
        _date.value = getToday()
    }

    private fun getToday() : String {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return today.format(formatter)
    }

//    fun getSchedule(todayDate: String) {
//        addDisposable(repository.getSchedules(todayDate),
//            object : DisposableSingleObserver<List<Schedule>>() {
//                override fun onSuccess(t: List<Schedule>) {
//                    todoAdapter.setSchedules(t)
//                    todoAdapter.notifyDataSetChanged()
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.d("TodoViewModel",e.message)
//                }
//            })
//    }
    fun onFabAddClick() {
        clickFab.call()
    }
}