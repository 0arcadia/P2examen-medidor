package pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gastosmensuales.ui.theme.MedicionViewModel
import data.Medicion
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.example.gastosmensuales.IconoMedicion
import com.example.gastosmensuales.R
import data.TipoMedidor
import data.CurrencyTransformation
import data.formatToCLP

@Composable
fun ListaMedicionesScreen(
    viewModel: MedicionViewModel,
    onNavigateToForm: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onNavigateToForm) {
            Text(stringResource(R.string.btn_new_reading))
        }

        LazyColumn {
            items(viewModel.mediciones.value) { medicion ->
                Card(                         // Nuevo: Card para cada item
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Row(                      // Modificado: Cambio a Row
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconoMedicion(medicion)  // Nuevo: Icono agregado
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "${medicion.tipo.name}: ${medicion.valor} ${getUnitForType(medicion.tipo)}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = medicion.fecha.toString(),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun getUnitForType(tipo: TipoMedidor): String {
    return when(tipo) {
        TipoMedidor.AGUA -> stringResource(R.string.unit_water)
        TipoMedidor.LUZ -> stringResource(R.string.unit_light)
        TipoMedidor.GAS -> stringResource(R.string.unit_gas)
    }
}

@Composable
fun IconoMedicion(medicion: Medicion) {
    val icon = when(medicion.tipo) {
        TipoMedidor.AGUA -> Icons.Default.Water
        TipoMedidor.LUZ -> Icons.Default.Lightbulb
        TipoMedidor.GAS -> Icons.Default.LocalFireDepartment
    }
    Icon(icon, contentDescription = medicion.tipo.name)
}



@Composable
fun MedicionItem(medicion: Medicion) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = "${medicion.tipo.name}: $${"%,.0f".format(medicion.valor)} ${getUnitForType(medicion.tipo)}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = medicion.fecha.toString(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}