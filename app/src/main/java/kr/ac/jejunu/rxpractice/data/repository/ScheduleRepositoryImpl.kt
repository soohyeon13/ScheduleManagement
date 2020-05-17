package kr.ac.jejunu.rxpractice.data.repository

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.data.service.ScheduleService
import kr.ac.jejunu.rxpractice.domain.model.TimeSchedule
import kr.ac.jejunu.rxpractice.domain.repository.ScheduleRepository
import java.util.*

class ScheduleRepositoryImpl(
    private val service: ScheduleService
) : ScheduleRepository {
    companion object {
        private val TAG = "ScheduleRepositoryImpl"
    }

    private val scheduleItems = BehaviorSubject.create<List<Schedule>>()
    private val allItems = BehaviorSubject.create<List<Date>>()
    override fun getSchedule(date: Date): Completable {
        return service
            .getSchedule(date)
            .subscribeOn(Schedulers.io())
            .doOnSuccess { scheduleItems.onNext(it) }
            .ignoreElement()
    }

    override fun loadSchedule(): Observable<List<Schedule>> {
        return scheduleItems.hide()
    }

    override fun insertSchedule(schedule: Schedule) {
        service.insertSchedule(schedule)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Log.d(TAG, "insert success") },
                { Log.d(TAG, "insert false") }).dispose()
    }

    override fun getItem(): Observable<List<Date>> {
        return allItems.hide()
    }

    override fun loadAllSchedule(): Completable {
        return service
            .getAllSchedule()
            .subscribeOn(Schedulers.io())
            .doOnSuccess { allItems.onNext(it) }
            .ignoreElement()
    }
}