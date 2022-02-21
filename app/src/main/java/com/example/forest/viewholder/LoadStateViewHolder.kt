package com.example.forest.viewholder

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.forest.databinding.SerieLoadStateFooterBinding

class LoadStateViewHolder(
        private val binding: SerieLoadStateFooterBinding,
        retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.buttonRetry.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        val isLoadingState = loadState is LoadState.Loading
        binding.apply {
            progressBar.isVisible = isLoadingState
            buttonRetry.isVisible = !isLoadingState
            textViewError.isVisible = !isLoadingState
        }
    }
}