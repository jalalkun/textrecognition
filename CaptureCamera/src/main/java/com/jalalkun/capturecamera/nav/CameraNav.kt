package com.jalalkun.capturecamera.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jalalkun.capturecamera.ui.CaptureCameraScreen
import com.jalalkun.nav.NavRoute.captureCameraRoute

fun NavGraphBuilder.cameraNav(navHostController: NavHostController) {
    composable(captureCameraRoute) { CaptureCameraScreen(navHostController) }
}