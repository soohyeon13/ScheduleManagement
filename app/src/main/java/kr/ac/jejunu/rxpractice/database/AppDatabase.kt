package kr.ac.jejunu.rxpractice.database

import android.content.Context
import androidx.room.*
import kr.ac.jejunu.rxpractice.model.Description
import kr.ac.jejunu.rxpractice.model.Sales
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.util.Converters

@Database(entities = [User::class, Schedule::class, Sales::class,Description::class], version = 18)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun salesDao(): SalesDao
}