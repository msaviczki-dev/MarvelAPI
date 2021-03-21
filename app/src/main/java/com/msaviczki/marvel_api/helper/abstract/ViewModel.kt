package com.msaviczki.marvel_api.helper.abstract

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.msaviczki.marvel_api.helper.`interface`.ViewState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ViewModel<State : ViewState> : ViewModel() {

    private val disposable = CompositeDisposable()
    protected abstract val state : State
    val viewModelState: MutableLiveData<State> = MutableLiveData()

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    protected fun setState(state: State) {
        viewModelState.value = state
    }

    protected fun Disposable.handleDisposable(): Disposable = apply { disposable.add(this) }
}