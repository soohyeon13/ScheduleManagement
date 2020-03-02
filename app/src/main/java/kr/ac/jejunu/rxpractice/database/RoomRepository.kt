package kr.ac.jejunu.rxpractice.database

import android.app.Application
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import java.util.*

class RoomRepository(application:Application) {
    private var scheduleDao : ScheduleDao
    private var userDao : UserDao

    init {
        val database = AppDatabase.getInstance(application)
        scheduleDao = database!!.scheduleDao()
        userDao = database.userDao()
    }

    fun insertUser(user : User): Completable {return userDao.insertUser(user)}
    fun deleteUser(user : User): Completable {return userDao.deleteUser(user)}
    fun updateUser(user : User): Completable {return userDao.updateUser(user)}
    fun getUser(userName : String) : Single<List<User>> {
        return userDao.getUsers(userName)
    }
    fun getUserId(userName:String,userPhone:String) : Int {
        return userDao.getUserId(userName,userPhone)
    }

    fun insertSchedule(schedule: Schedule) : Completable { return scheduleDao.todoInsert(schedule)}
    fun deleteSchedule(schedule: Schedule) : Completable {return scheduleDao.todoDelete(schedule)}
    fun updateSchedule(schedule: Schedule) : Completable {return scheduleDao.todoUpdate(schedule)}
    fun getSchedules(today : String) : List<Schedule> {
        return scheduleDao.todayTodo(today)
    }
    fun getAllSchedules() : List<Calendar> {
        return scheduleDao.getAllDay()
    }
}