package data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Medicion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipo: TipoMedidor,
    val valor: Double,
    val fecha: LocalDate
)

enum class TipoMedidor {
    AGUA, LUZ, GAS
}