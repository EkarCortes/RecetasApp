package com.example.recetasapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class InstructionsFragment : Fragment(R.layout.fragment_instructions) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val instructions = arguments?.getString("instructions")
        view.findViewById<TextView>(R.id.TextInstructions).text = instructions
    }

    companion object {
        fun newInstance(instructions: String): InstructionsFragment {
            val fragment = InstructionsFragment()
            val args = Bundle()
            args.putString("instructions", instructions)
            fragment.arguments = args
            return fragment
        }
    }
}