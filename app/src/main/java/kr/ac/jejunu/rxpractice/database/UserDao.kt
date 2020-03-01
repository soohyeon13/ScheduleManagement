package kr.ac.jejunu.rxpractice.database

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kr.ac.jejunu.rxpractice.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE user_name LIKE :userName")
    fun getUsers(userName: String): Single<List<User>>

    @Query("SELECT id FROM user WHERE user_name LIKE:userName AND user_phone_num LIKE:userPhone")
    fun getUserId(userName:String,userPhone:String) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(vararg user: User) : Completable

    @Delete
    fun deleteUser(user: User) : Completable

    @Update
    fun updateUser(vararg user: User) : Completable
}