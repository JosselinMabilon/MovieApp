package com.example.forest.ui

import androidx.fragment.app.viewModels
import com.example.forest.R
import com.example.forest.viewmodel.FavoriteViewModel

class FavoriteFragment : BaseFragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun layoutRes(): Int {
        return R.layout.fragment_favorite
    }
}