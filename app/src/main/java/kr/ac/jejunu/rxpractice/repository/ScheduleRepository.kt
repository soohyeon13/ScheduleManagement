package kr.ac.jejunu.rxpractice.repository

import io.reactivex.Completable
import kr.ac.jejunu.rxpractice.data.response.Schedule
import io.reactivex.Observable
import java.util.*

interface ScheduleRepository {
    fun getSchedule(date:Date) : Completable
    fun loadSchedule(): Observable<List<Schedule>>
    fun insertSchedule(schedule: Schedule)
    fun getItem(): Observable<List<Date>>
    fun loadAllSchedule() : Completable
}