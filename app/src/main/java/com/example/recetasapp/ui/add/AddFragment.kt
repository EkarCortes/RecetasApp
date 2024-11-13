package com.example.recetasapp.ui.add

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.recetasapp.R
import com.example.recetasapp.data.database.Receta
import com.example.recetasapp.viewmodel.RecetaViewModel
import com.google.android.material.textfield.TextInputEditText
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class AddFragment : Fragment() {

    private val recetaViewModel: RecetaViewModel by activityViewModels()

    private val selectedImagesUris: MutableList<Uri> = mutableListOf()


    private val selectMultipleImagesLauncher =
        registerForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris: List<Uri>? ->
            uris?.let {
                selectedImagesUris.clear()
                selectedImagesUris.addAll(it)
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                if (selectedImagesUris.isNotEmpty()) {
                    val firstImageUri = selectedImagesUris[0]
                    val imageView = requireView().findViewById<ImageView>(R.id.imageViewPreview)
                    Glide.with(this)
                        .load(firstImageUri)
                        .transform(RoundedCorners(16))
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView)
                }
            } ?: Toast.makeText(requireContext(), "No seleccionaste imágenes", Toast.LENGTH_SHORT).show()
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

        buttonAddImage.setOnClickListener {
            selectMultipleImagesLauncher.launch(arrayOf("image/*")) // Permitir solo selección de imágenes
        }

        buttonSave.setOnClickListener {
            val titulo = editTextTitle.text.toString()
            val ingredientes = editTextIngredients.text.toString()
            val instrucciones = editTextInstructions.text.toString()

            if (titulo.isNotEmpty() && ingredientes.isNotEmpty() && instrucciones.isNotEmpty()) {
                if (selectedImagesUris.isNotEmpty()) {
                    val imagenes = selectedImagesUris.joinToString(",") { it.toString() }
                    val receta = Receta(
                        titulo = titulo,
                        ingredientes = ingredientes,
                        pasos = instrucciones,
                        imagenes = imagenes
                    )
                    recetaViewModel.añadirReceta(receta)

                    clearFields()
                    requireView().findViewById<ImageView>(R.id.imageViewPreview).setImageResource(R.drawable.ic_launcher_foreground)
                    Toast.makeText(requireContext(), "Receta guardada exitosamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Por favor selecciona al menos una imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        return root
    }
    fun clearFields() {
        requireView().findViewById<TextInputEditText>(R.id.editTextTitle).setText("")
        requireView().findViewById<TextInputEditText>(R.id.editTextIngredients).setText("")
        requireView().findViewById<TextInputEditText>(R.id.editTextInstructions).setText("")
        requireView().findViewById<ImageView>(R.id.imageViewPreview).setImageResource(R.drawable.ic_launcher_foreground)
    }
}
