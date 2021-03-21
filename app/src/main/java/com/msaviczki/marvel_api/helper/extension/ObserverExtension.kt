package com.msaviczki.marvel_api.helper.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.msaviczki.marvel_api.helper.`interface`.ViewState
import com.msaviczki.marvel_api.helper.abstract.ViewModel

inline fun <reified State : ViewState> AppCompatActivity.onStateChanged(
    viewModel: ViewModel<State>,
    crossinline handle: (State) -> Unit
) {
    viewModel.viewModelState.observe(this, Observer { state -> handle(state as State) })
}

inline fun <reified State : ViewState> Fragment.onStateChanged(
    viewModel: ViewModel<State>,
    crossinline handle: (State) -> Unit
) {
    viewModel.viewModelState.observe(this, Observer { state -> handle(state as State) })
}