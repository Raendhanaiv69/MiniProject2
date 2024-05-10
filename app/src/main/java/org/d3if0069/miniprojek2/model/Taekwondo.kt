package org.d3if0069.miniprojek2.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Taekwondo")
data class Taekwondo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val sabuk: String,
    val fakultas: String,
    val gender: String,
)