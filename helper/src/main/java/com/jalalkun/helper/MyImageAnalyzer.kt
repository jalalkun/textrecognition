package com.jalalkun.helper

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.jalalkun.helper.Static.TAG
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
class MyImageAnalyzer(private val b: Bitmap, private val context: Context) : ImageAnalysis.Analyzer {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.Builder().build())
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromBitmap(b, imageProxy.imageInfo.rotationDegrees)
            // Pass image to an ML Kit Vision API
            // ...
            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        val blockCornerPoints = block.cornerPoints
                        val blockFrame = block.boundingBox
                        Log.e(TAG, "analyze: blocktext $blockText")
//                        for (line in block.lines) {
//                            val lineText = line.text
//                            val lineCornerPoints = line.cornerPoints
//                            val lineFrame = line.boundingBox
//                            Log.e(TAG, "analyze: linetext $lineText")
//                            for (element in line.elements) {
//                                val elementText = element.text
//                                Log.e(TAG, "analyze: elementText $elementText")
//                                val elementCornerPoints = element.cornerPoints
//                                val elementFrame = element.boundingBox
//                            }
//                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "analyze: e ${e.localizedMessage}")
                }
        }
    }


    fun processBitmap(result: (String) -> Unit) {
        Log.e(TAG, "processBitmap: ...")
        val result = recognizer.process(InputImage.fromBitmap(b, 0))
            .addOnSuccessListener { visionText ->
                if (visionText.textBlocks.isEmpty()) Toast.makeText(
                    context,
                    "No text found",
                    Toast.LENGTH_SHORT
                ).show()

//                for (block in visionText.textBlocks) {
//                    val blockText = block.text
//                    val blockCornerPoints = block.cornerPoints
//                    val blockFrame = block.boundingBox
//                    Log.e(TAG, "processBitmap: blocktext $blockText")
//                    result(blockText)
////                    for (line in block.lines) {
////                        val lineText = line.text
////                        val lineCornerPoints = line.cornerPoints
////                        val lineFrame = line.boundingBox
////                        Log.e(TAG, "processBitmap: linetext $lineText")
////                        for (element in line.elements) {
////                            val elementText = element.text
////                            Log.e(TAG, "processBitmap: elementText $elementText")
////                            val elementCornerPoints = element.cornerPoints
////                            val elementFrame = element.boundingBox
////                        }
////                    }
//                }
                if (visionText.textBlocks.isNotEmpty()) {
                    Log.e(TAG, "processBitmap: result ${visionText.textBlocks[0].text}")
                    result(visionText.textBlocks[0].text)
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "processBitmap: e ${e.localizedMessage}")
            }
            .addOnCompleteListener {
                Log.e(TAG, "processBitmap: complete ")
            }

        Log.e(TAG, "processBitmap: isCanceled ${result.isCanceled}")
        Log.e(TAG, "processBitmap: isComplete ${result.isComplete}")
        Log.e(TAG, "processBitmap: isSuccessful ${result.isSuccessful}")
    }
}
