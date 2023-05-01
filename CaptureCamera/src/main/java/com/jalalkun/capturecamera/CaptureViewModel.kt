package com.jalalkun.capturecamera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalalkun.helper.Static.TAG
import kotlinx.coroutines.launch

class CaptureViewModel(private val captureRepository: CaptureRepository) : ViewModel(){
    fun insertData(
        source: String,
        result: String
    ){
        viewModelScope.launch {
            Log.e(TAG, "insertData: ${captureRepository.insertCalculation(source, result)}")
        }
    }
}