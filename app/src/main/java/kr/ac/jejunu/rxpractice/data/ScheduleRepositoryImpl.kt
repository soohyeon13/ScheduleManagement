package kr.ac.jejunu.rxpractice.data

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import kr.ac.jejunu.rxpractice.database.AppDatabase
import kr.ac.jejunu.rxpractice.domain.repository.ScheduleRepository
import kr.ac.jejunu.rxpractice.model.Schedule

class ScheduleRepositoryImpl(val database:AppDatabase) : ScheduleRepository{

    private val scheduleSubject = BehaviorSubject.create<Schedule>()
    override fun loadSchedules(): Single<Schedule> {
    }

    override fun insertSchedule(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteSchedule(): Completable {
        TODO("Not yet implemented")
    }

}