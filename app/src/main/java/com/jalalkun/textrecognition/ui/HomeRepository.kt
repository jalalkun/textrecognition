package com.jalalkun.textrecognition.ui

import com.jalalkun.helper.Proses
import com.jalalkun.mymodel.Calculation
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllCalculation(): Flow<Proses>

    suspend fun insertCalculation(calculation: Calculation): Flow<Proses>
}