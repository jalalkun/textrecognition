package com.jalalkun.textrecognition.di

import com.jalalkun.textrecognition.ui.HomeRepoImpl
import com.jalalkun.textrecognition.ui.HomeRepository
import com.jalalkun.capturecamera.CaptureRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> {
        HomeRepoImpl(get())
    }
    single {
        CaptureRepository(get())
    }
}