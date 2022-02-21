package com.example.forest.viewmodel

import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

    fun cleanSummary(oldSummary: String?): String? {
        val regex = Regex("<[^>]*>")
        return oldSummary?.let { regex.replace(it, "") }
    }
}