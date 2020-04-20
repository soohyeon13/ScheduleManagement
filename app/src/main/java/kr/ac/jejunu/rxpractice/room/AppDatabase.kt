package kr.ac.jejunu.rxpractice.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.ac.jejunu.rxpractice.data.response.Schedule
import kr.ac.jejunu.rxpractice.data.service.ScheduleService
import kr.ac.jejunu.rxpractice.util.Converters

@Database(entities = [Schedule::class],version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun scheduleService() : ScheduleService
}