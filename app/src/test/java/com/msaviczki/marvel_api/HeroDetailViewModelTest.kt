package com.msaviczki.marvel_api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.msaviczki.marvel_api.domain.usecase.HeroDetailUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.presentation.viewmodel.HeroDetailViewModel
import com.msaviczki.marvel_api.presentation.viewstate.HeroDetailViewState
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit
import org.mockito.quality.Strictness

@RunWith(JUnit4::class)
class HeroDetailViewModelTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val heroDetailUseCase: HeroDetailUseCase = mock()
    private val saveFavoriteUseCase: SaveFavoriteUseCase = mock()

    private var observeState: Observer<HeroDetailViewState> = mock()

    @InjectMocks
    lateinit var viewModel: HeroDetailViewModel

    @Test
    fun `Should request heroe detail When server return Success`() {
        //GIVEN
        whenever(heroDetailUseCase.invoke(HERO_ID)).doReturn(Single.just(MOCKED_HERO_ITEM))
        viewModel.viewModelState.observeForever(observeState)

        //THEN
        viewModel.requestData(HERO_ID.toString())

        //SHOULD
        inOrder(observeState) {
            verify(observeState).onChanged(HeroDetailViewState(true))
            verify(observeState).onChanged(
                HeroDetailViewState(
                    loading = false,
                    error = "",
                    data = MOCKED_HERO_ITEM
                )
            )
        }
    }

    @Test
    fun `Should request heroe detail When server return error`() {
        //GIVEN
        whenever(heroDetailUseCase.invoke(HERO_ID)).doReturn(Single.error(Throwable()))
        viewModel.viewModelState.observeForever(observeState)

        //THEN
        viewModel.requestData(HERO_ID.toString())

        //SHOULD
        inOrder(observeState) {
            verify(observeState).onChanged(HeroDetailViewState(true))
            verify(observeState).onChanged(
                HeroDetailViewState(
                    loading = false,
                    error = "Infelizmente não conseguimos concluir sua busca, tente novamente."
                )
            )
        }
    }
}