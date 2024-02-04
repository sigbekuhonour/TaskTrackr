package com.example.tasktrackr

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun NavComp(viewModel: MyViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "LandingPage") {
        composable("LandingPage") { WelcomeUiPage(navController) }
        composable("TodoList") { EditNote(navController, viewModel) }
        composable("TextDisplay") { TaskDisplay(viewModel = viewModel, navigation = navController) }
    }
}
