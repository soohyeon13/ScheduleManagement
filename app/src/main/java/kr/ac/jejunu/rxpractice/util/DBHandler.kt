package kr.ac.jejunu.rxpractice.util

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.database.AppDatabase
import kr.ac.jejunu.rxpractice.model.Schedule
import java.sql.Date
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DBHandler(private val context : Context) {
    fun getSchedule(query:String):Single<List<Schedule>> {
        Thread.sleep(1000)
        return AppDatabase.getInstance(context)!!.scheduleDao().todayTodo(query)
    }
}