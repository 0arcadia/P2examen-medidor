package com.example.gastosmensuales.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.Medicion
import data.MedicionRepository
import data.TipoMedidor
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class MedicionViewModel(private val repository: MedicionRepository) : ViewModel() {
    val mediciones: StateFlow<List<Medicion>> = repository.mediciones

    @RequiresApi(Build.VERSION_CODES.O)
    fun agregarMedicion(tipo: TipoMedidor, valor: Double, fecha: LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            repository.insert(
                Medicion(
                    tipo = tipo,
                    valor = valor,
                    fecha = fecha
                )
            )
        }
    }
}