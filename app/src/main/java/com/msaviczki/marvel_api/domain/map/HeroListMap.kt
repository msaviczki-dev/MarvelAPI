package com.msaviczki.marvel_api.domain.map

import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.data.HeroResponse

private const val IMAGE_DIMEN = "standard_medium"
private const val HTTP = "http"
private const val HTTPS = "https"

fun List<HeroResponse>.map(): MutableList<HeroItem> {
    return if (isNullOrEmpty()) {
        mutableListOf()
    } else {
        val list = mutableListOf<HeroItem>()
        forEach { response ->
            val imgUrl = response.image?.path.orEmpty().replace(HTTP, HTTPS)
            val ext = response.image?.extension.orEmpty()
            val item = HeroItem(
                id = response.id.toString(),
                name = response.name.orEmpty(),
                urlImage = "$imgUrl/$IMAGE_DIMEN.$ext",
                decription = response.description.orEmpty()
            )
            list.add(item)
        }
        list
    }
}

fun MutableList<HeroItem>.map(favoriteList: MutableList<HeroItem>): MutableList<HeroItem> {
    return if (favoriteList.isEmpty()) this
    else {
        favoriteList.forEach { item ->
            val itemContained = this.find { original ->
                original.id == item.id
            }
            itemContained?.let { contained ->
                val index = indexOf(contained)
                this[index].isFavorite = true
            }
        }
        this
    }
}