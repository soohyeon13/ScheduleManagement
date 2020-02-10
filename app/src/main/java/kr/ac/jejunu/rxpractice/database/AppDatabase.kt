package kr.ac.jejunu.rxpractice.database

import android.content.Context
import androidx.room.*
import kr.ac.jejunu.rxpractice.model.Schedule
import kr.ac.jejunu.rxpractice.model.User
import kr.ac.jejunu.rxpractice.util.Converters

@Database(entities = [User::class, Schedule::class],version = 6)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun scheduleDao() : ScheduleDao


    companion object {
        private var instance : AppDatabase? = null

        @Synchronized
        fun getInstance(context : Context) : AppDatabase? {
            if(instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "app.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
//        private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            if (INSTANCE == null) {
//                synchronized(this) {
//                    INSTANCE = Room.databaseBuilder(
//                        context,
//                        AppDatabase::class.java, "app.db"
//                    )
//                        .build()
//                }
//            }
//            return INSTANCE as AppDatabase
//        }
//
//        fun destroyInstance() {
//            INSTANCE == null
//        }
    }
}