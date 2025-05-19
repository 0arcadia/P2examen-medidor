package data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicionDao {
    @Query("SELECT * FROM Medicion ORDER BY fecha DESC")
    fun getAll(): Flow<List<Medicion>>

    @Insert
    suspend fun insert(medicion: Medicion)
}