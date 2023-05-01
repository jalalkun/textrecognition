package com.jalalkun.textrecognition.ui.screen

import android.Manifest
import android.content.ContentValues
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.jalalkun.helper.Loading
import com.jalalkun.helper.MyImageAnalyzer
import com.jalalkun.helper.Proses
import com.google.gson.Gson
import com.jalalkun.helper.Static.TAG
import com.jalalkun.helper.calculateText
import com.jalalkun.helper.uriToBitmap
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.jalalkun.textrecognition.BuildConfig
import com.jalalkun.textrecognition.ui.HomeViewModel
import com.jalalkun.mymodel.Calculation
import com.jalalkun.nav.toCaptureCamera
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val list = remember {
        viewModel.listCalculation
    }

    var popupInput by remember {
        mutableStateOf(false)
    }

    when (
        val state = viewModel.listState.collectAsState().value
    ) {
        is Proses.Loading -> {
            Loading()
        }

        is Proses.Success<*> -> {
            if (state.data is List<*>) {
                Log.e(TAG, "HomeScreen: state size ${(state.data as List<*>).size}")
                Log.e(TAG, "HomeScreen: state ${Gson().toJson(state.data)}")
                viewModel.listCalculation.clear()
                (state.data as List<*>).forEach {
                    if (it is Calculation) viewModel.listCalculation.add(it)
                }
            }
            viewModel.dismiss()
        }

        is Proses.Error -> {

        }

        else -> {

        }
    }

    when(
        viewModel.insertState.collectAsState().value
    ){
        is Proses.Loading -> {
            Loading()
        }
        is Proses.Error -> {

        }
        is Proses.Content -> {

        }
        is Proses.Success<*> -> {
            viewModel.getListCalculation()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(
                    bottom = 56.dp,
                    end = 18.dp,
                    start = 18.dp,
                    top = 18.dp
                )
        ) {
            items(
                items = list,
                key = {
                    it.id
                },
                itemContent = {
                    ItemCalculation(calculation = it)
                }
            )
        }

        ElevatedButton(
            onClick = {
                popupInput = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Add Input")
        }
    }
    if (popupInput) PopUpChoice(navHostController = navHostController, homeViewModel = viewModel) {
        popupInput = !popupInput
    }
    LaunchedEffect(Unit) {
        viewModel.getListCalculation()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PopUpChoice(navHostController: NavHostController, homeViewModel: HomeViewModel, dismisRequest: () -> Unit) {
    val context = LocalContext.current
    val storagePermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    var showStorage by remember {
        mutableStateOf(false)
    }
    var isPickImage = false
    val pickImage =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            isPickImage = false
            if (uri != null) {
                uriToBitmap(uri, context.contentResolver)?.let { it1 ->
                    MyImageAnalyzer(it1, context).processBitmap { res ->
                        Log.e(ContentValues.TAG, "onImageSaved: bitmap created")
                        calculateText(
                            context = context,
                            string = res,
                            result = { resource, result ->
                                homeViewModel.insertData(resource, result)
                                navHostController.popBackStack()
                                dismisRequest()
                            },
                            onError = {
                                dismisRequest()
                            }
                        )
                    }
                }
            }
        }
    AlertDialog(
        onDismissRequest = {
            dismisRequest()
        },
        confirmButton = {},
        dismissButton = {},
        text = {
            Column {
                when(BuildConfig.PICK_SOURCE){
                    "ALL" -> {
                        Button(onClick = { navHostController.toCaptureCamera() }) {
                            Text(text = "Camera")
                        }
                        Button(onClick = {
                            if (storagePermissionState.status.isGranted) {
                                if (!isPickImage) {
                                    pickImage.launch("image/*")
                                    isPickImage = true
                                }
                            } else {
                                showStorage = true
                            }
                        }) {
                            Text(text = "Storage")
                        }
                    }
                    "CAMERA" -> {
                        Button(onClick = { navHostController.toCaptureCamera() }) {
                            Text(text = "Camera")
                        }
                    }
                    "STORAGE" -> {
                        Button(onClick = {
                            if (storagePermissionState.status.isGranted) {
                                if (!isPickImage) {
                                    pickImage.launch("image/*")
                                    isPickImage = true
                                }
                            } else {
                                showStorage = true
                            }
                        }) {
                            Text(text = "Storage")
                        }
                    }
                }

            }
        }
    )

    if (showStorage) {
        Dialog(onDismissRequest = { showStorage = false }) {
            ElevatedCard {
                Column {
                    val textToShow = if (storagePermissionState.status.shouldShowRationale) {
                        "The storage is important for this app. Please grant the permission."
                    } else {
                        "Storage permission required for this feature to be available. " +
                                "Please grant the permission"
                    }
                    Text(textToShow)
                    Button(onClick = { storagePermissionState.launchPermissionRequest() }) {
                        Text("Request permission")
                        showStorage = false
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCalculation(calculation: Calculation) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "input: ${calculation.source}")
            Text(text = "result: ${calculation.result}")
        }
    }

}