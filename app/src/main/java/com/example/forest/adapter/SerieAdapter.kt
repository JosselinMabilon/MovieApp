package com.example.forest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.forest.databinding.ItemSerieViewBinding
import com.example.forest.domain.model.Serie
import com.example.forest.viewholder.SerieViewHolder

class SerieAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Serie, SerieViewHolder>(SERIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerieViewHolder {
        val binding = ItemSerieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SerieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SerieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            if (position != RecyclerView.NO_POSITION) {
                holder.itemView.setOnClickListener {
                    listener.onItemClick(currentItem)
                }
            }
            holder.bind(currentItem)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(serie: Serie)
    }

    companion object {
        private val SERIE_COMPARATOR = object : DiffUtil.ItemCallback<Serie>() {
            override fun areItemsTheSame(oldItem: Serie, newItem: Serie) =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Serie, newItem: Serie) =
                    oldItem == newItem
        }
    }
}