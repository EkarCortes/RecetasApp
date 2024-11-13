package com.example.recetasapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.data.repository.RecetaRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecetaRepository
    val todasLasRecetas: LiveData<List<Receta>>

    init {
        val recetaDao = com.example.recetasapp.data.database.RecetaDatabase
            .getDatabase(application)
            .recetaDao()
        repository = RecetaRepository(recetaDao)
        todasLasRecetas = repository.obtenerRecetas()
    }

    }
