package com.jalalkun.helper

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.FileDescriptor
import java.io.IOException

fun uriToBitmap(selectedFileUri: Uri, contentResolver: ContentResolver): Bitmap? {
    try {
        val parcelFileDescriptor = contentResolver.openFileDescriptor(selectedFileUri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        Log.e(ContentValues.TAG, "uriToBitmap: return bitmap", )
        return image
    } catch (e: IOException) {
        Log.e(ContentValues.TAG, "uriToBitmap: error bitmap", )
        e.printStackTrace()
    }
    return null
}