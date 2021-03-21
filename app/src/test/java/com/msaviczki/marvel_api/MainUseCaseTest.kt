package com.msaviczki.marvel_api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource
import com.msaviczki.marvel_api.domain.repository.MainRepository
import com.msaviczki.marvel_api.domain.usecase.MainUseCase
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
class MainUseCaseTest {
    @get:Rule
    val mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    private val mainRepository: MainRepository = mock()
    private val dataSource: FavoriteHeroesDataSource = mock()

    @InjectMocks
    lateinit var useCase: MainUseCase

    @Test
    fun `requestData complete return single When request Success Data`() {
        //GIVEN
        whenever(mainRepository.requestHeroesList()).doReturn(Single.just(MOCKED_HERO_ITEM_LIST))

        //THEN
        useCase.invoke()
            .test()
            .assertComplete()

        //VERIFY
        verify(mainRepository, times(1)).requestHeroesList()
    }

    @Test
    fun `requestData not complete return single When request Error`() {
        //GIVEN
        whenever(mainRepository.requestHeroesList()).doReturn(Single.error(Throwable()))

        //THEN
        useCase.invoke()
            .test()
            .assertNotComplete()

        //VERIFY
        verify(mainRepository, times(1)).requestHeroesList()
    }
}