package com.example.recetasapp.ui.add

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recetasapp.R
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.viewmodel.RecetaViewModel
import com.google.android.material.textfield.TextInputEditText

class AddFragment : Fragment() {

    private val recetaViewModel: RecetaViewModel by activityViewModels()

    private var selectedImageUri: Uri? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            requireView().findViewById<TextView>(R.id.textViewImagePreview).text = "Imagen seleccionada: $it"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add, container, false)

        val editTextTitle = root.findViewById<TextInputEditText>(R.id.editTextTitle)
        val editTextIngredients = root.findViewById<TextInputEditText>(R.id.editTextIngredients)
        val editTextInstructions = root.findViewById<TextInputEditText>(R.id.editTextInstructions)

        val buttonAddImage = root.findViewById<Button>(R.id.buttonAddImage)
        val buttonSave = root.findViewById<Button>(R.id.buttonSave)

        // Configuración del botón para seleccionar imagen
        buttonAddImage.setOnClickListener {
            selectImageLauncher.launch("image/*") // Permitir solo selección de imágenes
        }

        buttonSave.setOnClickListener {
            val titulo = editTextTitle.text.toString()
            val ingredientes = editTextIngredients.text.toString()
            val instrucciones = editTextInstructions.text.toString()

            if (titulo.isNotEmpty() && ingredientes.isNotEmpty() && instrucciones.isNotEmpty()) {
                val receta = Receta(
                    titulo = titulo,
                    ingredientes = ingredientes,
                    pasos = instrucciones,
                    imagenes = selectedImageUri?.toString() ?: ""
                )
                recetaViewModel.añadirReceta(receta)

                editTextTitle.text?.clear()
                editTextIngredients.text?.clear()
                editTextInstructions.text?.clear()
                requireView().findViewById<TextView>(R.id.textViewImagePreview).text = "Imagen seleccionada:"
                selectedImageUri = null
            } else {
                requireView().findViewById<TextView>(R.id.textViewImagePreview).text = "Por favor, complet todos los campos"
            }
        }

        return root
    }
}
