package com.example.forest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forest.databinding.ItemSerieViewBinding
import com.example.forest.domain.model.SearchSerie
import com.example.forest.viewholder.SearchSerieViewHolder

class SearchSerieAdapter(private val listener: OnItemClickListener) :
    ListAdapter<SearchSerie, SearchSerieViewHolder>(SERIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchSerieViewHolder {
        val binding = ItemSerieViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchSerieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchSerieViewHolder, position: Int) {
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
        fun onItemClick(searchSerie: SearchSerie)
    }

    companion object {
        private val SERIE_COMPARATOR = object : DiffUtil.ItemCallback<SearchSerie>() {
            override fun areItemsTheSame(oldItem: SearchSerie, newItem: SearchSerie) =
                oldItem.show?.id == newItem.show?.id

            override fun areContentsTheSame(oldItem: SearchSerie, newItem: SearchSerie) =
                oldItem == newItem

        }
    }
}