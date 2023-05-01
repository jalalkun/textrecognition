package com.jalalkun.capturecamera

import com.jalalkun.mydb.CalculationDb
import com.jalalkun.mymodel.Calculation

class CaptureRepository(private val calculationDb: CalculationDb) {
    suspend fun insertCalculation(source: String, result: String) : Boolean{
        return calculationDb.insertCalcutalion(
            Calculation(
                source = source,
                result = result
            )
        )
    }
}