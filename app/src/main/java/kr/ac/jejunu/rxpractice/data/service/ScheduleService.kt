package kr.ac.jejunu.rxpractice.data.service

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.data.response.Schedule
import java.util.*

@Dao
interface ScheduleService {
    @Query("SELECT * FROM schedule WHERE date LIKE :date")
    fun getSchedule(date : Date) : Single<List<Schedule>>

    @Query("SELECT date FROM schedule ")
    fun getAllSchedule() : Single<List<Date>>

    @Insert
    fun insertSchedule(schedule: Schedule) : Completable

    @Delete
    fun deleteSchedule(schedule: Schedule) : Completable

    @Update
    fun updateSchedule(schedule: Schedule) : Completable
}