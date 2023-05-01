package com.jalalkun.textrecognition.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.jalalkun.textrecognition.ui.screen.HomeScreen
import com.jalalkun.nav.NavRoute.homeRoute

fun NavGraphBuilder.navHome(navHostController: NavHostController){

    composable(homeRoute){ HomeScreen(navHostController = navHostController) }
}