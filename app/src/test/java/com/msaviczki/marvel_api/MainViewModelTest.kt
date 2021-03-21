package com.msaviczki.marvel_api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.msaviczki.marvel_api.domain.usecase.MainUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.presentation.viewmodel.MainViewModel
import com.msaviczki.marvel_api.presentation.viewstate.MainViewState
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness

@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val saveFavoriteUseCase: SaveFavoriteUseCase = mock()
    private val mainUseCase: MainUseCase = mock()
    private var observeState: Observer<MainViewState> = mock()

    @InjectMocks
    lateinit var viewModel: MainViewModel

    @Test
    fun `Should request heroes list When server return Success`() {
        //GIVEN
        whenever(mainUseCase.invoke()).doReturn(Single.just(MOCKED_HERO_ITEM_LIST))
        viewModel.viewModelState.observeForever(observeState)

        //THEN
        viewModel.requestHeroesList()

        //SHOULD
        inOrder(observeState) {
            verify(observeState).onChanged(MainViewState(true, ""))
            verify(observeState).onChanged(
                MainViewState(
                    loading = false,
                    error = "",
                    data = MOCKED_HERO_ITEM_LIST
                )
            )
        }
    }

    @Test
    fun `Should request heroes list When server return Error`() {
        //GIVEN
        whenever(mainUseCase.invoke()).doReturn(Single.error(Throwable()))
        viewModel.viewModelState.observeForever(observeState)

        //THEN
        viewModel.requestHeroesList()

        //SHOULD
        inOrder(observeState) {
            verify(observeState).onChanged(MainViewState(true, ""))
            verify(observeState).onChanged(
                MainViewState(
                    loading = false,
                    error = "Infelizmente não conseguimos concluir sua busca, tente novamente."
                )
            )
        }
    }
}