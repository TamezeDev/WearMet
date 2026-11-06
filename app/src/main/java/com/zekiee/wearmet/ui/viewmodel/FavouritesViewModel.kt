package com.zekiee.wearmet.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.data.AppDatabase
import com.zekiee.wearmet.local.repository.FavouritesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavouritesViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = FavouritesRepository(AppDatabase.get(app).favouriteDao())

    val favourites: StateFlow<List<Location>> = repo.favourites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}