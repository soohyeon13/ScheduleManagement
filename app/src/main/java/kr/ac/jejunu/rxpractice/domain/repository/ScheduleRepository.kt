package kr.ac.jejunu.rxpractice.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.model.Schedule

interface ScheduleRepository {
    fun loadSchedules() : Single<Schedule>
    fun insertSchedule() : Completable
    fun deleteSchedule() : Completable
}