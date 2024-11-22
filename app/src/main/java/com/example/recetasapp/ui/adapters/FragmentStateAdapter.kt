package com.example.recetasapp.ui.adapters


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.recetasapp.IngredientsFragment
import com.example.recetasapp.InstructionsFragment

class RecipePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val ingredients: String,
    private val instructions: String
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IngredientsFragment.newInstance(ingredients)
            1 -> InstructionsFragment.newInstance(instructions)
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }
}