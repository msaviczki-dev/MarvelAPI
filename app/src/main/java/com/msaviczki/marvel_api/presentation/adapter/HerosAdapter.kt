package com.msaviczki.marvel_api.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.msaviczki.marvel_api.R
import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.extension.loadFromUrl
import com.msaviczki.marvel_api.helper.filter.HeroFilter
import com.msaviczki.marvel_api.presentation.`interface`.FavoriteClickListener
import com.msaviczki.marvel_api.presentation.`interface`.HeroItemClickListener
import kotlinx.android.synthetic.main.hero_item.view.*

class HerosAdapter(
    private val list: MutableList<HeroItem>,
    private val listener: FavoriteClickListener,
    private val heroListener: HeroItemClickListener
) :
    RecyclerView.Adapter<HerosAdapter.HerosViewHolder>(), Filterable {

    private val filter = HeroFilter(list) { notifyDataSetChanged() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HerosViewHolder =
        HerosViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.hero_item, parent, false)
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HerosViewHolder, position: Int) =
        holder.bindView(list[position], (list.size == position - 1).not())

    override fun getFilter(): Filter = filter

    fun refreshOldList(list: MutableList<HeroItem>) = filter.refreshOldList(list)

    fun removeElement(item: HeroItem) {
        list.remove(item)
        filter.removeElementAtOldList(item)
        notifyDataSetChanged()
    }

    inner class HerosViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        fun bindView(item: HeroItem, isLast: Boolean) {
            with(view) {
                txtName.text = item.name
                viewSeparator.isVisible = isLast
                txtDescription.text = item.decription
                imgHero.loadFromUrl(item.urlImage)
                btnFavorite.isSelected = item.isFavorite
                btnFavorite.setOnClickListener { view ->
                    view.isSelected = view.isSelected.not()
                    listener.favoriteOnClickListener(view.isSelected, item)
                }
                root.setOnClickListener {
                    heroListener.heroItemCliclListener(item.id)
                }
            }
        }
    }
}