package com.example.forest.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.forest.domain.model.Serie
import com.example.forest.network.entities.AllSerieNetworkMapper
import retrofit2.HttpException
import java.io.IOException

private const val SERIE_STARTING_PAGE_INDEX = 1

class SeriePagingSource(
    private val serieApi: SerieApi,
    private val mapper: AllSerieNetworkMapper
) : PagingSource<Int, Serie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Serie> {
        val position = params.key ?: SERIE_STARTING_PAGE_INDEX

        return try {
            val response = mapper.fromEntityList(serieApi.getAllSerie(position))
            LoadResult.Page(
                data = response,
                prevKey = if (position == SERIE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Serie>): Int? {
        return state.anchorPosition
    }
}