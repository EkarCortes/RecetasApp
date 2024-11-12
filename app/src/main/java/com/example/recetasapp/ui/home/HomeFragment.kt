package com.example.recetasapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.gridlayout.widget.GridLayout
import com.bumptech.glide.Glide
import com.example.recetasapp.R
import com.example.recetasapp.viewmodel.RecetaViewModel
import com.example.recetasapp.data.database.Receta

class HomeFragment : Fragment() {

    private val recetaViewModel: RecetaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val gridLayout = root.findViewById<GridLayout>(R.id.gridLayout)

        recetaViewModel.todaslasRecetas().observe(viewLifecycleOwner) { recetas: List<Receta> ->
            gridLayout.removeAllViews()

            // Agrega cada receta al GridLayout
            recetas.forEach { receta ->
                val itemView = createRecipeView(receta, inflater, gridLayout)
                gridLayout.addView(itemView)
            }
        }

        return root
    }

    private fun createRecipeView(receta: Receta, inflater: LayoutInflater, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.item_receta, parent, false)

        val textTitle = view.findViewById<TextView>(R.id.textTitleRecipe)
        val imageView = view.findViewById<ImageView>(R.id.textImage)

        textTitle.text = receta.titulo

        // Cargar la imagen desde la URI usando Glide
        Glide.with(imageView.context)
            .load(receta.imagenes) // URI desde la base de datos
            .placeholder(R.drawable.ic_launcher_foreground) // Imagen temporal mientras se carga
            .error(R.drawable.ic_launcher_background) // Imagen en caso de error
            .into(imageView)
        return view
    }
}
