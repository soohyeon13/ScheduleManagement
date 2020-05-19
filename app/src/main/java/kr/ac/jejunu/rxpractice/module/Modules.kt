package kr.ac.jejunu.rxpractice.module

import androidx.room.Room
import kr.ac.jejunu.rxpractice.data.repository.ScheduleRepositoryImpl
import kr.ac.jejunu.rxpractice.domain.repository.ScheduleRepository
import kr.ac.jejunu.rxpractice.room.AppDatabase
import kr.ac.jejunu.rxpractice.ui.activity.viewmodel.TodoViewModel
import kr.ac.jejunu.rxpractice.ui.addschedule.viewmodel.AddScheduleViewModel
import kr.ac.jejunu.rxpractice.ui.schedule.adapter.TimeAdapter
import kr.ac.jejunu.rxpractice.ui.schedule.viewmodel.ScheduleViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var dataModules = module {
    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "todo_db")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().scheduleService() }
    single<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
}

var viewModelModules = module {
    viewModel { TodoViewModel() }
    viewModel { ScheduleViewModel(get()) }
    viewModel { AddScheduleViewModel(get()) }
}

var adapterModules = module {
    factory { TimeAdapter() }
}

val appModules = listOf(viewModelModules, dataModules, adapterModules)