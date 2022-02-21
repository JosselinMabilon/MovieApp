package com.example.forest.viewholder

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.forest.R
import com.example.forest.databinding.ItemSerieViewBinding
import com.example.forest.domain.model.SearchSerie

class SearchSerieViewHolder(private val  binding: ItemSerieViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(serie: SearchSerie) {
        binding.apply {
            Glide.with(itemView.context)
                .load(serie.show?.imgUrl?.original?.toUri()?.buildUpon()?.scheme("https")?.build())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions().error(R.drawable.default_serie_image))
                .into(serieCardImageView)

            serieCardTitle.text = serie.show?.name ?: R.string.serie_no_name.toString()
            ratingValue.text = serie.show?.rating?.average?.toString() ?: "-"
        }
    }
}