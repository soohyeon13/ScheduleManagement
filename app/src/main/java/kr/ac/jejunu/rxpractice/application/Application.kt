package kr.ac.jejunu.rxpractice.application

import android.app.Application
import kr.ac.jejunu.rxpractice.di.modules
import org.koin.android.ext.android.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(applicationContext, modules)
    }
}