package com.example.forest.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.example.forest.R
import com.example.forest.adapter.SerieAdapter
import com.example.forest.adapter.SerieLoadStateAdapter
import com.example.forest.databinding.FragmentSerieBinding
import com.example.forest.domain.model.Serie
import com.example.forest.viewmodel.SerieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SerieFragment : BaseFragment(), SerieAdapter.OnItemClickListener {

    private val viewModel: SerieViewModel by viewModels()

    private var _binding: FragmentSerieBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SerieAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var footerAdapter: SerieLoadStateAdapter

    override fun layoutRes(): Int {
        return R.layout.fragment_serie
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (viewModel.loadDarkModeData()) {
            true -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            false -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }
        _binding = FragmentSerieBinding.bind(view)
        adapter = SerieAdapter(this)
        setupSerieList()
        loadCheck()
        loadSeries()
        filterChip()
    }

    private fun loadCheck() {
        adapter.addLoadStateListener { loadState ->
            val isErrorState = loadState.source.refresh is LoadState.Error
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = isErrorState
                textViewError.isVisible = isErrorState
            }
        }
    }

    private fun setupSerieList() {
        binding.apply {
            recyclerView.setHasFixedSize(true)
            footerAdapter = SerieLoadStateAdapter { adapter.retry() }
            gridLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            gridLayoutManager = viewModel.setSpanSizeLookup(gridLayoutManager, adapter, footerAdapter)
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = adapter.withLoadStateFooter(
                footer = footerAdapter
            )
            buttonRetry.setOnClickListener {
                adapter.retry()
            }
        }
    }

    override fun onItemClick(serie: Serie) {
        val action = SerieFragmentDirections.actionNavigationSerieToDetailsFragment4(serie)
        findNavController().navigate(action)
    }

    private fun filterChip() {
        binding.chipStatusRunning.setOnClickListener {
            binding.chipStatusEnded.isChecked = false
            binding.chipStatusInDevelopement.isChecked = false
            if (!binding.chipStatusRunning.isChecked) {
                loadSeries()
            } else {
                loadSeries("Running")
            }
            binding.recyclerView.scrollToPosition(0)
        }
        binding.chipStatusEnded.setOnClickListener {
            binding.chipStatusRunning.isChecked = false
            binding.chipStatusInDevelopement.isChecked = false
            if (!binding.chipStatusEnded.isChecked) {
                loadSeries()
            } else {
                loadSeries("Ended")
            }
            binding.recyclerView.scrollToPosition(0)
        }
        binding.chipStatusInDevelopement.setOnClickListener {
            binding.chipStatusEnded.isChecked = false
            binding.chipStatusRunning.isChecked = false
            if (!binding.chipStatusInDevelopement.isChecked) {
                loadSeries()
            } else {
                loadSeries("In Development")
            }
            binding.recyclerView.scrollToPosition(0)
        }
    }

    private fun loadSeries(filterQuery: String? = null) {
        viewModel.getPagedSerie(filterQuery).observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}