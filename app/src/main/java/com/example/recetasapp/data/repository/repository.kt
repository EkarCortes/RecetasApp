package com.example.recetasapp.data.repository

import androidx.lifecycle.LiveData
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.data.database.RecetaDao

class RecetaRepository(private val recetaDao: RecetaDao) {

    suspend fun insertar(receta: Receta) {
        recetaDao.insertReceta(receta)
    }
    fun obtenerRecetas(): LiveData<List<Receta>> {
        return recetaDao.getAllRecetas()
    }

}