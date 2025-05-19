package pantallas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.TipoMedidor
import data.CurrencyTransformation
import com.example.gastosmensuales.ui.theme.MedicionViewModel
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.res.stringResource
import com.example.gastosmensuales.R


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioMedicionScreen(
    viewModel: MedicionViewModel,
    onBack: () -> Unit
) {
    val (tipo, setTipo) = remember { mutableStateOf(TipoMedidor.AGUA) }
    val (valor, setValor) = remember { mutableStateOf("") }
    val (medidor, setMedidor) = remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = stringResource(R.string.title_add_reading),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(stringResource(R.string.label_meter_type))
        TipoMedidor.values().forEach { currentType ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipo == currentType,
                    onClick = { setTipo(currentType) }
                )
                Text(
                    text = currentType.name,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = medidor,
            onValueChange = setMedidor,
            label = { Text(stringResource(R.string.hint_meter_number)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = valor,
            onValueChange = { newValue ->
                // Permite solo números y máximo un punto decimal
                if (newValue.matches(Regex("^\\d*\\.?\\d*$")) && newValue.length <= 10) {
                    setValor(newValue)
                }
            },
            label = { Text(stringResource(R.string.hint_value)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            prefix = { Text("$") },
            visualTransformation = CurrencyTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val valorNumerico = valor.toDoubleOrNull() ?: 0.0
                viewModel.agregarMedicion(tipo, valorNumerico)
                onBack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_save))
        }
    }
}