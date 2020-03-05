package kr.ac.jejunu.rxpractice.ui.fragment.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kr.ac.jejunu.rxpractice.base.BaseViewModel
import kr.ac.jejunu.rxpractice.database.RoomRepository
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class ScheduleAddViewModel(application: Application) : BaseViewModel<List<Schedule>>(application) {
    val clickPersonEvent = SingleLiveEvent<Unit>()
    val clickDateEvent = SingleLiveEvent<Unit>()
    val clickTimeEvent = SingleLiveEvent<Unit>()
    val clickSave = SingleLiveEvent<Unit>()
    val clickCancel = SingleLiveEvent<Unit>()
    private var isCheck = true
    private var userId = 0
    private val calendar = Calendar.getInstance()
    private val repository: RoomRepository by lazy {
        RoomRepository(application)
    }

    fun onSelectPerson() {
        clickPersonEvent.call()
    }

    fun onSelectDate() {
        clickDateEvent.call()
    }

    fun onSelectTime() {
        clickTimeEvent.call()
    }

    fun onSave(
        userName: String,
        number: String,
        title: String,
        date: String,
        time: String,
        description: String
    ) {
        Log.d("test",number)
        Log.d("test",isCheck.toString())
        if (number.isEmpty()) { isCheck = false }
        userId = checkUser(userName, number)
        val c = dateToCalendar(date, time)
        val schedule = Schedule(
            userId = userId,
            name = userName,
            title = title,
            description = description,
            cal = c,
            date = date
        )

        Log.d("test1",isCheck.toString())
        if (isCheck) {
            CompositeDisposable().add(
                repository.insertSchedule(schedule)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Log.d("ViewModel",it.message.toString())
                    }
                    .subscribe()
            )
        } else {
            CompositeDisposable().add(
                repository.updateSchedule(schedule)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Log.d("ViewModel",it.message.toString())
                    }
                    .subscribe()
            )
        }
        clickSave.call()
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateToCalendar(dateStr: String, timeStr: String): Calendar {
        val d = "$dateStr $timeStr"
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
        val date = simpleDateFormat.parse(d)
        calendar.time = date
        return calendar
    }

    private fun checkUser(userName: String, number: String): Int {
        userId = repository.getUserId(userName, number)
        if (userId == 0) {
            val user = User(userName = userName, userNum = number)
            CompositeDisposable()
                .add(
                    repository.insertUser(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                )
        }
        return userId
    }

    fun onCancel() {
        clickCancel.call()
    }
}