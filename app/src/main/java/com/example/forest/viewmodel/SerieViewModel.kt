package com.example.forest.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import com.example.forest.adapter.SerieAdapter
import com.example.forest.adapter.SerieLoadStateAdapter
import com.example.forest.domain.model.Serie
import com.example.forest.repository.ISerieRepository
import com.example.forest.repository.ISharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SerieViewModel @Inject constructor(
    private val repository: ISerieRepository,
    private val sharedPreferences: ISharedPreferences
) : ViewModel() {

    fun getPagedSerie(filterQuery: String?): LiveData<PagingData<Serie>> {
        var listSerie = repository.getSerie().cachedIn(viewModelScope)

        if (!filterQuery.isNullOrEmpty()) {
            listSerie = listSerie.map { pagingData ->
                pagingData.filter { serie ->
                    if (serie.status != null) {
                        serie.status == filterQuery
                    } else {
                        false
                    }
                }
            }
        }
        return listSerie
    }

    fun loadDarkModeData(): Boolean {
        return sharedPreferences.getBooleanValue(SWITCH_DM_BOOLEAN_KEY, false)
    }

    fun setSpanSizeLookup(gridLayoutManager: GridLayoutManager, adapter: SerieAdapter, footerAdapter: SerieLoadStateAdapter): GridLayoutManager {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == adapter.itemCount  && footerAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        return gridLayoutManager
    }

    companion object {
        @VisibleForTesting
        const val SWITCH_DM_BOOLEAN_KEY = "SWITCH_DM_BOOLEAN_KEY"
    }
}