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
    @Query("SELECT ")

}