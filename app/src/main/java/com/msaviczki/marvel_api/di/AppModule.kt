package com.msaviczki.marvel_api.di

import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSource
import com.msaviczki.marvel_api.datasource.FavoriteHeroesDataSourceManagerImpl
import com.msaviczki.marvel_api.domain.repository.HeroDetailRepository
import com.msaviczki.marvel_api.domain.repository.HeroDetailRepositoryImpl
import com.msaviczki.marvel_api.domain.repository.MainRepository
import com.msaviczki.marvel_api.domain.repository.MainRepositoryImpl
import com.msaviczki.marvel_api.domain.service.HeroDetailApi
import com.msaviczki.marvel_api.domain.service.MainApi
import com.msaviczki.marvel_api.domain.usecase.GetFavoriteUseCase
import com.msaviczki.marvel_api.domain.usecase.HeroDetailUseCase
import com.msaviczki.marvel_api.domain.usecase.MainUseCase
import com.msaviczki.marvel_api.domain.usecase.SaveFavoriteUseCase
import com.msaviczki.marvel_api.network.ApiClient
import com.msaviczki.marvel_api.presentation.viewmodel.FavoritesViewModel
import com.msaviczki.marvel_api.presentation.viewmodel.HeroDetailViewModel
import com.msaviczki.marvel_api.presentation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val mainModule = module {
    viewModel { MainViewModel(mainUseCase = get(), saveFavoriteUseCase = get()) }
    factory { MainUseCase(repository = get(), dataSource = get()) }
    factory<MainRepository> { MainRepositoryImpl(api = get()) }
    factory { ApiClient().client.create(MainApi::class.java) }
    factory { SaveFavoriteUseCase(dataSource = get()) }
    factory { GetFavoriteUseCase(dataSource = get()) }
    factory<FavoriteHeroesDataSource> { FavoriteHeroesDataSourceManagerImpl(context = androidContext()) }
}

val favoriteModule = module {
    viewModel { FavoritesViewModel(getFavoriteUseCase = get(), saveFavoriteUseCase = get()) }
}

val detailModule = module {
    viewModel { HeroDetailViewModel(useCase = get(), saveFavoriteUseCase = get()) }
    factory { HeroDetailUseCase(repository = get(), dataSource = get()) }
    factory<HeroDetailRepository> { HeroDetailRepositoryImpl(api = get()) }
    factory { ApiClient().client.create(HeroDetailApi::class.java) }
}

val initModule = listOf(mainModule, favoriteModule, detailModule)