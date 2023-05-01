package com.jalalkun.textrecognition

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jalalkun.textrecognition.nav.navHome
import com.jalalkun.textrecognition.ui.theme.BelajarTextRecognitionTheme
import com.jalalkun.capturecamera.nav.cameraNav
import com.jalalkun.nav.NavRoute.homeRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BelajarTextRecognitionTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = homeRoute){
                    navHome(navController)
                    cameraNav(navController)
                }
            }
        }
    }
}



