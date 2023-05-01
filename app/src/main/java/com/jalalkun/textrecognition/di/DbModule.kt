package com.jalalkun.textrecognition.di

import com.jalalkun.mydb.CalculationDb
import com.jalalkun.mydb.CalculationImpl
import org.koin.dsl.module

val dbModule = module {
    single<CalculationDb> {
        CalculationImpl()
    }
}