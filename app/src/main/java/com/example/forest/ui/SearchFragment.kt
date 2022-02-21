package com.example.forest.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.forest.R
import com.example.forest.adapter.SearchSerieAdapter
import com.example.forest.databinding.FragmentSearchBinding
import com.example.forest.domain.model.SearchSerie
import com.example.forest.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

const val GRIDLAYOUT_NB_COLUMN_VALUE = 2

@AndroidEntryPoint
class SearchFragment : BaseFragment(), SearchSerieAdapter.OnItemClickListener {

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun layoutRes(): Int {
        return R.layout.fragment_search
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchBinding.bind(view)
        val adapter = SearchSerieAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = GridLayoutManager(context, GRIDLAYOUT_NB_COLUMN_VALUE, GridLayoutManager.VERTICAL, false)
        }
        viewModel.serie.observe(viewLifecycleOwner, { serieList ->
            if (serieList.isEmpty()) {
                binding.recyclerView.isVisible = false
                binding.textViewEmpty.isVisible = true
                binding.buttonRetry.isVisible = false
                binding.textViewError.isVisible = false
                binding.progressBar.isVisible = false
            } else {
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
                binding.textViewEmpty.isVisible = false
                binding.buttonRetry.isVisible = false
                binding.textViewError.isVisible = false
                adapter.submitList(serieList)
            }
        })
        watchForResearch()
        watchErrorState()
        setHasOptionsMenu(true)
    }

    private fun watchErrorState() {
        viewModel.errorState.observe(viewLifecycleOwner, { errorState ->
            if (errorState != null) {
                binding.recyclerView.isVisible = false
                binding.buttonRetry.isVisible = true
                binding.textViewError.isVisible = true
                binding.textViewEmpty.isVisible = false
                Toast.makeText(context, viewModel.errorState.value, Toast.LENGTH_LONG).show()
                binding.buttonRetry.setOnClickListener {
                    viewModel.searchSerie()
                }
            }
        })
    }

    private fun watchForResearch() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.changeQuery(query)
                    viewModel.searchSerie()
                    binding.searchBar.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onItemClick(searchSerie: SearchSerie) {
        val action = SearchFragmentDirections.actionNavigationSearchToSearchDetailsFragment(searchSerie)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}