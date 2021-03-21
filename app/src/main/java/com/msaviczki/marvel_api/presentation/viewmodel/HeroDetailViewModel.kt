package com.msaviczki.marvel_api.presentation.viewmodel

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.domain.usecase.HeroDetailUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.helper.abstract.ViewModel
import com.msaviczki.marvel_api.presentation.viewstate.HeroDetailViewState
import com.msaviczki.marvel_api.presentation.viewstate.onDataReceived
import com.msaviczki.marvel_api.presentation.viewstate.onError
import com.msaviczki.marvel_api.presentation.viewstate.onShowLoading
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HeroDetailViewModel(
    private val useCase: HeroDetailUseCase,
    private val saveFavoriteUseCase: SaveFavoriteUseCase
) :
    ViewModel<HeroDetailViewState>() {

    override val state: HeroDetailViewState = HeroDetailViewState()

    fun requestData(id: String) {
        useCase(id.toInt())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { setState(state.onShowLoading()) }
            .subscribe({ data ->
                setState(state.onDataReceived(data))
            }, {
                setState(state.onError())
            })
            .handleDisposable()
    }

    fun saveFavorite(state: Boolean, item: HeroItem) = saveFavoriteUseCase(state, item)
}