package com.msaviczki.marvel_api.presentation.viewstate

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.`interface`.ViewState

data class FavoritesViewState(
    val emptyList: String = "",
    val data: MutableList<HeroItem> = mutableListOf()
) : ViewState

fun FavoritesViewState.onDataReceived(data: MutableList<HeroItem>): FavoritesViewState =
    this.copy(emptyList = "", data = data)

fun FavoritesViewState.onEmptyList(): FavoritesViewState =
    this.copy(emptyList = "Você não tem nenhum herói favorito =/", data = mutableListOf())