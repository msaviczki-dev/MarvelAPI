package com.msaviczki.marvel_api.presentation.viewstate

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.`interface`.ViewState

data class MainViewState(
    val loading: Boolean = false,
    val error: String = "",
    val data: MutableList<HeroItem> = mutableListOf()
) : ViewState

fun MainViewState.onShowLoading() = copy(loading = true, error = "")

fun MainViewState.onShowError() = copy(loading = false, error = "Infelizmente não conseguimos concluir sua busca, tente novamente.")

fun MainViewState.onDataReceived(data: MutableList<HeroItem>) =
    copy(loading = false, error = "", data = data)


