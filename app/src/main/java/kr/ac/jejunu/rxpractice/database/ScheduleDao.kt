package kr.ac.jejunu.rxpractice.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.model.Schedule
import java.sql.Date
import java.util.*

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule WHERE date LIKE :today")
    fun todayTodo(today : String) : List<Schedule>

    @Query("SELECT cal FROM schedule")
    fun getAllDay(): List<Calendar>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun todoInsert(vararg schedule: Schedule) : Completable

    @Delete
    fun todoDelete(schedule: Schedule) : Completable

    @Update
    fun  todoUpdate(vararg schedule: Schedule) : Completable

}