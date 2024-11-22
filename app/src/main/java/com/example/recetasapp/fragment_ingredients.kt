package com.example.recetasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingredients = arguments?.getString("ingredients")
        view.findViewById<TextView>(R.id.TextIngredients).text = ingredients
    }

    companion object {
        fun newInstance(ingredients: String): IngredientsFragment {
            val fragment = IngredientsFragment()
            val args = Bundle()
            args.putString("ingredients", ingredients)
            fragment.arguments = args
            return fragment
        }
    }
}