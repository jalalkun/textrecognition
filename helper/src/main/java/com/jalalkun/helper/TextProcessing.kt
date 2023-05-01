package com.jalalkun.helper

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.jalalkun.helper.Static.TAG


fun calculateText(
    context: Context,
    string: String,
    result: (resource: String, result: String) -> Unit,
    onError: () -> Unit
) {
    var first = ""
    var last = ""
    val operators = listOf('/', 'x', '+', '-')

    var operator = ""
    val list = string.toList()
    list.forEach {
        operators.forEach { op ->
            if (it == op) operator = op.toString()
        }
        when (
            operator.isEmpty()
        ) {
            true -> {
                if (it.isDigit() || it == '.') first += it
            }

            else -> {
                if (it.isDigit() || it == '.') last += it
            }
        }
    }
    Log.e(TAG, "CalculateText: first $first")
    Log.e(TAG, "CalculateText: last $last")
    Log.e(TAG, "CalculateText: operator $operator")
    if (first.isEmpty() || last.isEmpty() || operator.isEmpty()) {
        Toast.makeText(context, "Cant Calculate text", Toast.LENGTH_SHORT).show()
        onError()
        return
    }
    result(
        "$first $operator $last",
        when (operator) {
            "/" -> first.toFloat().div(last.toFloat())
            "+" -> first.toFloat().plus(last.toFloat())
            "-" -> first.toFloat().minus(last.toFloat())
            "x" -> first.toFloat().times(last.toFloat())
            else -> first.toFloat().div(last.toFloat())
        }.toString()
    )
}

fun ByteArray.convertString(): String{
    return String(
        Base64.encode(this, Base64.DEFAULT)
    )
}