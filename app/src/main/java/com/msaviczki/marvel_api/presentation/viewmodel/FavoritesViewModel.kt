package com.msaviczki.marvel_api.presentation.viewmodel

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.domain.usecase.GetFavoriteUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.helper.abstract.ViewModel
import com.msaviczki.marvel_api.presentation.viewstate.FavoritesViewState
import com.msaviczki.marvel_api.presentation.viewstate.onDataReceived
import com.msaviczki.marvel_api.presentation.viewstate.onEmptyList

class FavoritesViewModel(
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase
) :
    ViewModel<FavoritesViewState>() {

    override val state: FavoritesViewState = FavoritesViewState()

    fun requestData() {
        val data = getFavoriteUseCase.invoke()
        if (data.isNotEmpty()) {
            setState(state.onDataReceived(data))
        } else {
            setState(state.onEmptyList())
        }
    }

    fun removeFavoriteHero(item: HeroItem) {
        val listIsEmpty = saveFavoriteUseCase.invoke(false, item)
        if (listIsEmpty) {
            setState(state.onEmptyList())
        }
    }
}