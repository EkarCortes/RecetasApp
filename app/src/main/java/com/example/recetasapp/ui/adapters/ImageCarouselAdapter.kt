package com.example.recetasapp.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetasapp.R

class ImageCarouselAdapter(private val images: List<String>) :
    RecyclerView.Adapter<ImageCarouselAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.carouselImage)
    }
    //inner class es una clase anidada que puede acceder a los atributos de la clase que la contiene
    //en este caso, la clase ImageViewHolder puede acceder a los atributos de la clase ImageCarouselAdapter


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image_carousel, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val uri = Uri.parse(images[position])
        Glide.with(holder.imageView.context)
            .load(uri)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = images.size
}
