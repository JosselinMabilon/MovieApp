package com.example.forest.viewholder

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.forest.R
import com.example.forest.databinding.ItemSerieViewBinding
import com.example.forest.domain.model.Serie

class SerieViewHolder(
    private val binding: ItemSerieViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(serie: Serie) {
        binding.apply {
            Glide.with(itemView.context)
                    .load(serie.imgUrl?.original?.toUri()?.buildUpon()?.scheme("https")?.build())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(RequestOptions().error(R.drawable.default_serie_image))
                    .into(serieCardImageView)

            serieCardTitle.text = serie.name ?: R.string.serie_no_name.toString()
            ratingValue.text = serie.rating?.average?.toString() ?: "-"
        }
    }
}