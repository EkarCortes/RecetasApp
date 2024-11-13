package com.example.recetasapp.ui.home

import android.content.Intent
import android.net.Uri
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
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.ui.detail.RecipeDetailActivity
import com.example.recetasapp.viewmodel.RecetaViewModel

class HomeFragment : Fragment() {

    private val recetaViewModel: RecetaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recetaViewModel.todaslasRecetas()
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
        val firstImageUrl = receta.imagenes.split(",").firstOrNull()
        if (!firstImageUrl.isNullOrEmpty()) {
            val uri = Uri.parse(firstImageUrl)
            Glide.with(view.context)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground)
        }
        view.setOnClickListener {
            val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
            intent.putExtra("RECETA_ID", receta.id)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
        return view
    }
}