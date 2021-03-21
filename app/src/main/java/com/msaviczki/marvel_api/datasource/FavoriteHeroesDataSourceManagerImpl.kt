package com.msaviczki.marvel_api.datasource

import android.content.Context
import com.google.gson.Gson
import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.extension.fromJsonList

private const val HERO_ITEM_PREFERENCE = "HERO_ITEM_PREFERENCE"
private const val HERO_ITEM_VALUE = "HERO_ITEM_KEY"

class FavoriteHeroesDataSourceManagerImpl(private val context: Context) :
    FavoriteHeroesDataSource {

    private val sharedPreferences by lazy { context.getSharedPreferences(HERO_ITEM_PREFERENCE, 0) }
    private val editor by lazy { sharedPreferences.edit() }

    override fun save(item: MutableList<HeroItem>) {
        item.forEach { element ->
            element.isFavorite = element.isFavorite.not()
        }
        val objectList: MutableList<HeroItem> = get() ?: mutableListOf()
        objectList.addAll(item)
        val objectJsonString = Gson().toJson(objectList)
        editor.putString(HERO_ITEM_VALUE, objectJsonString)
        editor.commit()
    }

    override fun get(): MutableList<HeroItem>? {
        return sharedPreferences.getString(HERO_ITEM_VALUE, null)?.let { json ->
            return@let Gson().fromJsonList<MutableList<HeroItem>>(json)
        }
    }

    override fun remove(item: MutableList<HeroItem>): Boolean {
        val objectList: MutableList<HeroItem> = get() ?: mutableListOf()
        item.forEach { needRemove ->
            objectList.find { original ->
                original.id == needRemove.id
            }.let { removedElement ->
                objectList.remove(removedElement)
            }
        }
        val objectJsonString = Gson().toJson(objectList)
        editor.putString(HERO_ITEM_VALUE, objectJsonString)
        editor.commit()
        return objectList.isEmpty()
    }
}