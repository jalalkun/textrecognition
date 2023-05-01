package com.jalalkun.textrecognition.di

import com.jalalkun.textrecognition.ui.HomeViewModel
import com.jalalkun.capturecamera.CaptureViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { CaptureViewModel(get()) }
}