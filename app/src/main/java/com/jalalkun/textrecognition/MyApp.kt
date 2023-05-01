package com.jalalkun.textrecognition

import android.app.Application
import com.jalalkun.textrecognition.di.dbModule
import com.jalalkun.textrecognition.di.repositoryModule
import com.jalalkun.textrecognition.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    dbModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}