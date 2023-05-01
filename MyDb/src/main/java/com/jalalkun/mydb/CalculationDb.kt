package com.jalalkun.mydb

import com.jalalkun.mymodel.Calculation

interface CalculationDb {
    suspend fun insertCalcutalion(calculation: Calculation): Boolean

    suspend fun getAllCalculation():List<Calculation>
}