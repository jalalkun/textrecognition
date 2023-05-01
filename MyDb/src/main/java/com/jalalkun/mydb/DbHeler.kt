package com.jalalkun.mydb

object DbHeler {
    fun getRandomKey(seed: String): ByteArray {
        // generate a new 64-byte encryption key
        return seed.toByteArray(charset("UTF8"))
    }
}