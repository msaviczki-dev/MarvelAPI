package com.msaviczki.marvel_api.presentation.viewstate

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.`interface`.ViewState

data class HeroDetailViewState(
    val loading: Boolean = false,
    val data: HeroItem? = null,
    val error: String = ""
) : ViewState

fun HeroDetailViewState.onShowLoading() = this.copy(loading = true)
fun HeroDetailViewState.onDataReceived(data: HeroItem?) =
    this.copy(loading = false, error = "", data = data)

fun HeroDetailViewState.onError() = this.copy(
    loading = false,
    error = "Infelizmente não conseguimos concluir sua busca, tente novamente."
)