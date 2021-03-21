package com.msaviczki.marvel_api.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.msaviczki.marvel_api.R
import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.extension.launch
import com.msaviczki.marvel_api.helper.extension.onStateChanged
import com.msaviczki.marvel_api.presentation.`interface`.FavoriteClickListener
import com.msaviczki.marvel_api.presentation.`interface`.HeroItemClickListener
import com.msaviczki.marvel_api.presentation.adapter.HerosAdapter
import com.msaviczki.marvel_api.presentation.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorites_hero.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteHeroFragment : Fragment(R.layout.fragment_favorites_hero), FavoriteClickListener,
    HeroItemClickListener {

    private lateinit var adapter: HerosAdapter
    private val viewModel: FavoritesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestData()
    }

    private fun onObserve() = onStateChanged(viewModel) { state ->
        onSuccess(state.data)
        onError(state.emptyList)
    }

    private fun onSuccess(data: MutableList<HeroItem>) {
        recyclerview.isVisible = true
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = HerosAdapter(data, this, this)
        adapter.refreshOldList(data)
        recyclerview.adapter = adapter
        adapter.notifyDataSetChanged()

        searchHero.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText.toString())
                return false
            }
        })
    }

    private fun onError(error: String) {
        if (error.isEmpty().not()) {
            txtError.text = error
            txtError.isVisible = true
            recyclerview.isVisible = false
        }
    }

    override fun favoriteOnClickListener(state: Boolean, item: HeroItem) {
        adapter.removeElement(item)
        viewModel.removeFavoriteHero(item)
    }

    override fun heroItemCliclListener(id: String) {
        requireActivity().launch<HeroDetailActivity> {
            putExtra(HeroDetailActivity.HERO_DETAIL_ID, id)
        }
    }
}