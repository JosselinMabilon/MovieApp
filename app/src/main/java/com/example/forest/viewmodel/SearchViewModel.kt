package com.example.forest.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.example.forest.domain.model.SearchSerie
import com.example.forest.repository.ISerieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ISerieRepository,
    state: SavedStateHandle
) : ViewModel() {

    @VisibleForTesting
    val _series: MutableLiveData<List<SearchSerie>> = MutableLiveData()
    val serie: LiveData<List<SearchSerie>> get() = _series

    @VisibleForTesting
    val _errorState: MutableLiveData<String> = MutableLiveData(null)
    val errorState: LiveData<String> get() = _errorState

    @VisibleForTesting
    val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    fun changeQuery(query: String) {
        currentQuery.value = query
    }

    fun searchSerie() {
        viewModelScope.launch {
            try {
                val result = repository.searchSerie(
                    query = currentQuery.value
                )
                _errorState.value = null
                _series.value = result
            } catch (e: IOException) {
                _errorState.value = e.toString()
            }
        }
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }
}