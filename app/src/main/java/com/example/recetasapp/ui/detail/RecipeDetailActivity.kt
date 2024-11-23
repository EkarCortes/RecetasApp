package com.example.recetasapp.ui.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.recetasapp.R
import com.example.recetasapp.ui.adapters.ImageCarouselAdapter
import com.example.recetasapp.ui.adapters.RecipePagerAdapter
import com.example.recetasapp.viewmodel.RecetaViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var recetaViewModel: RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_recipe_detail)
        val handler = Handler(Looper.getMainLooper())
        var currentPage = 0
        fun startAutoSlide(totalPages: Int) {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    currentPage = (currentPage + 1) % totalPages // 0, 1, 2, 0, 1, 2, ...
                    findViewById<ViewPager2>(R.id.carouselView).currentItem = currentPage
                    handler.postDelayed(this, 3000)
                }
            }, 3000)
        }
        startAutoSlide(8) //aquí de una vez delimito el número de imágenes que se van a
        // mostrar

        val recetaId = intent.getIntExtra("RECETA_ID", -1)
        // el -1 es para que si no se encuentra el valor, se use el valor por defecto
        // en este caso, -1 indica que no se encontró el valor osea, el id de la receta

        recetaViewModel = ViewModelProvider(this).get(RecetaViewModel::class.java)

        val recipeTitleTextView = findViewById<TextView>(R.id.textRecipe)
        val carouselView = findViewById<ViewPager2>(R.id.carouselView)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)


        recetaViewModel.getRecetaById(recetaId).observe(this) { receta ->
            val title = receta.titulo
            val ingredients = receta.ingredientes
            val instructions = receta.pasos


            recipeTitleTextView.text = title
            val imagenesUris = receta.imagenes.split(",")
            // separamos las imágenes por comas ya que es la url de las imágenes
            carouselView.adapter = ImageCarouselAdapter(imagenesUris)

            val adapter = RecipePagerAdapter(this, ingredients, instructions)
            viewPager.adapter = adapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Ingredientes"
                    1 -> "Instrucciones"
                    else -> null
                }
            }.attach()
        }
    }
}