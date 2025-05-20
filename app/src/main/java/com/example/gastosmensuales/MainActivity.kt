package com.example.gastosmensuales

import androidx.compose.foundation.layout.fillMaxSize
import com.example.gastosmensuales.ui.theme.MedicionViewModel
import com.example.gastosmensuales.ui.theme.navigation.NavGraph
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import data.AppDatabase
import data.Medicion
import data.MedicionRepository
import data.TipoMedidor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppMedidores()
        }
    }
}

@Composable
fun AppMedidoresTheme(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AppMedidoresPreview() {
    AppMedidoresTheme {
        AppMedidores()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppMedidores() {
    val context = LocalContext.current

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "mediciones.db"
        ).build()
    }

    val repository = remember { MedicionRepository.getInstance(db.medicionDao()) }

    val viewModel: MedicionViewModel = viewModel(
        factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return MedicionViewModel(repository) as T
            }
        }
    )

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            if (repository.mediciones.value.isEmpty()) {
                listOf(
                    Medicion(
                        tipo = TipoMedidor.AGUA,
                        valor = 1900.0,
                        fecha = LocalDate.of(2024, 1, 13)
                    ),
                    Medicion(
                        tipo = TipoMedidor.LUZ,
                        valor = 15900.0,
                        fecha = LocalDate.of(2024, 1, 13)
                    ),
                    Medicion(
                        tipo = TipoMedidor.AGUA,
                        valor = 1800.0,
                        fecha = LocalDate.of(2024, 1, 12)
                    )
                ).forEach { repository.insert(it) }
            }
        }
    }

    AppMedidoresTheme {
        NavGraph(viewModel = viewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun IconosPreview() {
    Column {
        IconoMedicion(Medicion(tipo = TipoMedidor.AGUA, valor = 0.0, fecha = LocalDate.now()))
        IconoMedicion(Medicion(tipo = TipoMedidor.LUZ, valor = 0.0, fecha = LocalDate.now()))
        IconoMedicion(Medicion(tipo = TipoMedidor.GAS, valor = 0.0, fecha = LocalDate.now()))
    }
}

@Composable
fun IconoMedicion(medicion: Medicion) {
    when(medicion.tipo) {
        TipoMedidor.AGUA -> Icon(
            imageVector = Icons.Filled.Water,
            contentDescription = "Agua",
            tint = Color.Blue
        )
        TipoMedidor.LUZ -> Icon(
            imageVector = Icons.Filled.Lightbulb,
            contentDescription = "Luz",
            tint = Color.Yellow
        )
        TipoMedidor.GAS -> Icon(
            imageVector = Icons.Filled.LocalFireDepartment,
            contentDescription = "Gas",
            tint = Color.Red
        )
    }
}
