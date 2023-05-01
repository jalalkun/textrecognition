package com.jalalkun.nav

import androidx.navigation.NavHostController
import com.jalalkun.nav.NavRoute.captureCameraRoute
import com.jalalkun.nav.NavRoute.homeRoute

fun NavHostController.toHome(){
    navigate(homeRoute)
}

fun NavHostController.toCaptureCamera(){
    navigate(captureCameraRoute)
}