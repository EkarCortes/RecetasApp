package com.example.recetasapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recetas")
data class Receta(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val ingredientes: String,
    val pasos: String,
    val imagenes: String
)
