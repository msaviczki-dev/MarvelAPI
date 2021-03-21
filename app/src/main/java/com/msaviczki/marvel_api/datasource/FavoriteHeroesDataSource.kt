package com.msaviczki.marvel_api.datasource

import com.msaviczki.marvel_api.data.HeroItem

interface FavoriteHeroesDataSource : DataSourceManager<MutableList<HeroItem>>