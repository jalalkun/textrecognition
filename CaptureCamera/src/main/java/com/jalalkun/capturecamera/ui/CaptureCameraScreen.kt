package com.jalalkun.capturecamera.ui

import android.content.ContentResolver
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.jalalkun.helper.FeatureCameraPermission
import com.jalalkun.helper.Loading
import com.jalalkun.helper.MyImageAnalyzer
import com.jalalkun.helper.calculateText
import com.jalalkun.helper.uriToBitmap
import com.jalalkun.capturecamera.CaptureViewModel
import com.jalalkun.capturecamera.startCamera
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CaptureCameraScreen(
    navHostController: NavHostController,
    viewModel: CaptureViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView: PreviewView = remember { PreviewView(context) }

    var loading by remember {
        mutableStateOf(false)
    }

    val imageCapture: MutableState<ImageCapture?> = remember {
        mutableStateOf(null)
    }
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    LaunchedEffect(previewView) {
        imageCapture.value = context.startCamera(
            lifecycleOwner = lifecycleOwner,
            cameraSelector = cameraSelector.value,
            previewView = previewView
        )
    }
    FeatureCameraPermission {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = {
                    previewView
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 45.dp)
                    .align(Alignment.TopCenter)
            )
            ElevatedButton(
                onClick = {
                    takePicture(
                        imageCapture.value,
                        Locale.getDefault(),
                        context,
                        context.contentResolver
                    ) {
                        Log.e(TAG, "CaptureCameraScreen: bitmap")
                        MyImageAnalyzer(it, context).processBitmap { res ->
                            Log.e(TAG, "CaptureCameraScreen: proses bitmap $res")
                            loading = true
                            calculateText(
                                context = context,
                                string = res,
                                result = { resource, result ->
                                    viewModel.insertData(resource, result)
                                    loading = false
                                    navHostController.popBackStack()
                                },
                                onError = {
                                    loading = false
                                }
                            )
//                            loading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Capture Image")
            }
        }
        if (loading) Loading()
    }
}

private fun takePicture(
    imageCapture: ImageCapture?,
    locale: Locale,
    context: Context,
    contentResolver: ContentResolver,
    result: (Bitmap) -> Unit
) {
    if (imageCapture == null) {
        Log.e(TAG, "takePicture: imageCapture null")
        return
    }
    val name = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", locale)
        .format(System.currentTimeMillis())
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    // Create output options object which contains file + metadata
    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()

    // Set up image capture listener, which is triggered after photo has
    // been taken
    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun
                    onImageSaved(output: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded: ${output.savedUri}"
                Log.e(TAG, "onImageSaved: uri ${output.savedUri}")
                output.savedUri?.let {
                    uriToBitmap(it, contentResolver)?.let { it1 ->
                        Log.e(TAG, "onImageSaved: bitmap created")
                        result(it1)
                    }
                }
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, msg)
            }
        }
    )
}