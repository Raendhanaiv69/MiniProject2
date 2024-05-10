package org.d3if0069.miniprojek2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if0069.miniprojek2.model.Taekwondo
import org.d3if0069.miniprojek2.database.TaekwondoDao


class MainViewModel(dao: TaekwondoDao) : ViewModel() {
    val data: StateFlow<List<Taekwondo>> = dao.getTaekwondo().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(500L),
        initialValue = emptyList()
    )
}