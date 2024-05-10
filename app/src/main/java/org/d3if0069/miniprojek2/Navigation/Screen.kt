package org.d3if0069.miniprojek2.navigation

import org.d3if0069.miniprojek2.ui.screen.KEY_ID_Taekwondo

sealed class Screen ( val route: String){
    data object Home: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_Taekwondo}") {
        fun withId(id: Long) = "detailScreen/$id"
    }
}