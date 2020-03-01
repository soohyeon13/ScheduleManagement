package kr.ac.jejunu.rxpractice.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.model.Schedule
import java.sql.Date

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedule WHERE cal_date LIKE :today")
    fun todayTodo(today : String) : Single<List<Schedule>>

    @Query("SELECT cal_date FROM schedule")
    fun getAllDay(): LiveData<List<Schedule>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun todoInsert(vararg schedule: Schedule) : Completable

    @Delete
    fun todoDelete(schedule: Schedule) : Completable

    @Update
    fun  todoUpdate(vararg schedule: Schedule) : Completable

}