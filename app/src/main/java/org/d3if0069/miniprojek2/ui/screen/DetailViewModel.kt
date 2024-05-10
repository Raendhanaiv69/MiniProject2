package org.d3if0069.miniprojek2.ui.screen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0069.miniprojek2.model.Taekwondo
import org.d3if0069.miniprojek2.database.TaekwondoDao
import java.util.logging.SimpleFormatter

class DetailViewModel(private val dao: TaekwondoDao): ViewModel() {
    fun insert(nama: String, sabuk:String, fakultas:String, gender: String) {
        val taekwondo = Taekwondo(
            nama = nama,
            sabuk = sabuk,
            fakultas = fakultas,
            gender = gender
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(taekwondo)
        }
    }

    suspend fun getTaekwondo(id: Long): Taekwondo? {
        return dao.getTaekwondoById(id)
    }

    fun update(id: Long, nama: String, sabuk: String, fakultas: String, gender:  String) {
        val taekwondo = Taekwondo(
            id = id,
            nama = nama,
            sabuk = sabuk,
            fakultas = fakultas,
            gender = gender
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(taekwondo)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deletedById(id)
        }
    }
}