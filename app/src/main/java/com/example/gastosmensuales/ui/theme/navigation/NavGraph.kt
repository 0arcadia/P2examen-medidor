package com.example.gastosmensuales.ui.theme.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gastosmensuales.ui.theme.MedicionViewModel
import pantallas.FormularioMedicionScreen
import pantallas.ListaMedicionesScreen



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(viewModel: MedicionViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "lista"
    ) {
        composable("lista") {
            ListaMedicionesScreen(
                viewModel = viewModel,
                onNavigateToForm = { navController.navigate("formulario") }
            )
        }
        composable("formulario") {
            FormularioMedicionScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}