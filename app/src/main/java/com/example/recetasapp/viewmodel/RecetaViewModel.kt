package com.example.recetasapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.data.repository.RecetaRepository
import kotlinx.coroutines.launch

class RecetaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecetaRepository
    private val allRecetas: LiveData<List<Receta>>

    init {
        val recetaDao = com.example.recetasapp.data.database.RecetaDatabase
            .getDatabase(application)
            .recetaDao()
        repository = RecetaRepository(recetaDao)
        allRecetas = repository.obtenerRecetas()
    }

    fun añadirReceta(receta: Receta) {
        viewModelScope.launch {
            repository.insertar(receta)
        }
    }
   fun todaslasRecetas() = repository.obtenerRecetas()
}