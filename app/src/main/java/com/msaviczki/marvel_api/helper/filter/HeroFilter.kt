package com.msaviczki.marvel_api.helper.filter

import android.widget.Filter
import com.msaviczki.marvel_api.data.HeroItem


class HeroFilter(
    private val itemList: MutableList<HeroItem>,
    private val call: () -> Unit
) : Filter() {

    private val oldList: MutableList<HeroItem> = mutableListOf()
    private val filtredList: MutableList<HeroItem> = mutableListOf()

    fun refreshOldList(list: MutableList<HeroItem>) {
        oldList.addAll(list)
    }

    fun removeElementAtOldList(item: HeroItem) {
        oldList.remove(item)
    }

    override fun performFiltering(sequence: CharSequence?): FilterResults {
        if (sequence.toString().isEmpty()) {
            filtredList.addAll(oldList)
        } else {
            itemList.forEach { item ->
                if (item.name.toLowerCase().contains(sequence.toString().toLowerCase())) {
                    filtredList.add(item)
                }
            }
            oldList.forEach { item ->
                val contains = filtredList.contains(item)
                if (item.name.toLowerCase()
                        .contains(sequence.toString().toLowerCase())
                    && contains.not()
                ) {
                    filtredList.add(item)
                }
            }
        }
        val results = FilterResults()
        results.values = filtredList
        return results
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        itemList.clear()
        itemList.addAll(results?.values as Collection<HeroItem>)
        filtredList.clear()
        call.invoke()
    }
}