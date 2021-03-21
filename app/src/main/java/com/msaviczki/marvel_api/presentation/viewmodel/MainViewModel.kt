package com.msaviczki.marvel_api.presentation.viewmodel

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.domain.usecase.MainUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.helper.abstract.ViewModel
import com.msaviczki.marvel_api.presentation.viewstate.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val mainUseCase: MainUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase
) : ViewModel<MainViewState>() {

    override val state: MainViewState = MainViewState()

    fun requestHeroesList() {
        mainUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setState(state.onShowLoading()) }
            .subscribe({ response ->
                setState(state.onDataReceived(response))
            }, {
                setState(state.onShowError())
            })
            .handleDisposable()
    }

    fun saveFavorite(state: Boolean, item: HeroItem) = saveFavoriteUseCase(state, item)
}