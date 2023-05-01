package com.jalalkun.textrecognition.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalalkun.helper.Proses
import com.jalalkun.mymodel.Calculation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {
    private val _listState = MutableStateFlow<Proses>(Proses.Loading)
    val listState = _listState.asStateFlow()

    val listCalculation = mutableListOf<Calculation>()

    fun getListCalculation(){
        viewModelScope.launch {
            homeRepository.getAllCalculation()
                .collect{
                    _listState.emit(it)
                }
        }
    }


    private val _insertState = MutableStateFlow<Proses>(Proses.Loading)
    val insertState = _insertState.asStateFlow()
    fun insertData(
        source: String,
        result: String
    ){
        viewModelScope.launch {
            homeRepository.insertCalculation(
                Calculation(
                    source = source,
                    result = result
                )
            ).collect{
                _insertState.emit(it)
            }
        }
    }

    fun dismiss(){
        viewModelScope.launch {
            _insertState.emit(Proses.Content)
            _listState.emit(Proses.Content)
        }
    }
}