package kr.ac.jejunu.rxpractice.di

import androidx.room.Room
import kr.ac.jejunu.rxpractice.data.ScheduleRepositoryImpl
import kr.ac.jejunu.rxpractice.database.AppDatabase
import kr.ac.jejunu.rxpractice.domain.repository.ScheduleRepository
import kr.ac.jejunu.rxpractice.ui.todo.viewmodel.TodoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

var dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "app.db").build()
    }
    single<ScheduleRepository> {
        ScheduleRepositoryImpl(get())
    }
}
var viewModelModule = module {
    viewModel { TodoViewModel(get()) }
}

var modules = listOf(viewModelModule, dataModule)