package com.virtus.demo.hits.ui.hitslist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.virtus.demo.hits.data.repository.HitsListRepository

class HitsListViewModel @ViewModelInject constructor(
    private val repository: HitsListRepository
) : ViewModel() {
    val hits = repository.getHits()
}
