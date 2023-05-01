package com.jalalkun.textrecognition.ui

import com.jalalkun.helper.Proses
import com.jalalkun.mydb.CalculationDb
import com.jalalkun.mymodel.Calculation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepoImpl(private val calculationDb: CalculationDb): HomeRepository{
    override suspend fun getAllCalculation() = flow {
        emit(Proses.Loading)
        try {
            val result = calculationDb.getAllCalculation()
            emit(Proses.Success(result))
        }catch (e: Exception){
            emit(Proses.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertCalculation(calculation: Calculation) = flow {
        emit(Proses.Loading)
        calculationDb.insertCalcutalion(calculation)
        emit(Proses.Success(""))
    }.flowOn(Dispatchers.IO)

}