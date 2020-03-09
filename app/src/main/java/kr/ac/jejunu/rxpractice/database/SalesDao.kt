package kr.ac.jejunu.rxpractice.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import kr.ac.jejunu.rxpractice.model.Sales

@Dao
interface SalesDao {
    @Query("SELECT * FROM sales WHERE month LIKE :month ")
    fun getMonthMoney(month : String) : List<Sales>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoney(vararg sales:Sales): Completable
}