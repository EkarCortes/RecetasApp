package com.example.recetasapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao //el @Dao es una anotación de Room que indica que esta interfaz es un Data Access Object
interface RecetaDao {
    @Query("SELECT * FROM recetas")
    fun getAllRecetas(): LiveData<List<Receta>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceta(receta: Receta)

    @Delete
    suspend fun deleteReceta(receta: Receta)

    @Query("SELECT * FROM recetas WHERE id = :id")
    suspend fun getRecetaById(id: Int): Receta
}