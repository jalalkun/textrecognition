package com.jalalkun.helper

sealed class Proses {
    object Loading : Proses()
    class Success<T>(val data: T) : Proses()
    class Error(val e: Throwable) : Proses()
    object Content : Proses()
}