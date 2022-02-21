package com.example.forest.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.forest.R
import com.example.forest.databinding.FragmentDetailsBinding
import com.example.forest.domain.model.Serie
import com.example.forest.viewmodel.DetailsViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private val args by navArgs<DetailsFragmentArgs>()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun layoutRes(): Int {
        return R.layout.fragment_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        val serie = args.series
        checkList(serie)
        setAllImage(serie)
        setGenres(serie)
        setAllText(serie)
    }

    private fun checkList(serie: Serie) {
        serie.summary = viewModel.cleanSummary(serie.summary)
    }

    private fun setGenres(serie: Serie) {
        binding.genresTextView.apply {
            for (index in serie.genres) {
                val chip = Chip(this.context)
                chip.text = index
                chip.isClickable = false
                chip.isCheckable = false
                addView(chip)
            }
        }
    }

    private fun setAllText(serie: Serie) {
        binding.textViewName.text = serie.name
        binding.textViewSummary.text = serie.summary
    }

    private fun setAllImage(serie: Serie) {
        binding.apply {
            Glide.with(this@DetailsFragment)
                .load(serie.imgUrl?.original)
                .error(R.drawable.default_serie_image)
                .into(imageViewBackground)

            Glide.with(this@DetailsFragment)
                .load(serie.imgUrl?.original)
                .error(R.drawable.default_serie_image)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewName.isVisible = true
                        textViewSummary.isVisible = true
                        return false
                    }
                })
                .into(imageCardView)
        }
    }
}