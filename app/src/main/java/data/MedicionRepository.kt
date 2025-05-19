package data


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn



class MedicionRepository(private val medicionDao: MedicionDao) {
    private val _mediciones = MutableStateFlow<List<Medicion>>(emptyList())
    val mediciones = _mediciones.asStateFlow()

    private val repositoryScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    init {
        medicionDao.getAll()
            .onEach { list ->
                _mediciones.update { list }
            }
            .launchIn(repositoryScope)
    }


    suspend fun insert(medicion: Medicion) {
        medicionDao.insert(medicion)
    }

    companion object {
        @Volatile
        private var instance: MedicionRepository? = null

        fun getInstance(medicionDao: MedicionDao): MedicionRepository {
            return instance ?: synchronized(this) {
                MedicionRepository(medicionDao).also { instance = it }
            }
        }
    }
}
