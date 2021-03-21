package com.msaviczki.marvel_api.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.msaviczki.marvel_api.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list_hero.*
import org.koin.android.viewmodel.ext.android.viewModel


class ListHeroFragment : Fragment(R.layout.fragment_list_hero), FavoriteClickListener,
    HeroItemClickListener {

    private lateinit var adapter: HerosAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
        viewModel.requestHeroesList()
    }

    private fun onObserve() = onStateChanged(viewModel) { state ->
        onLoading(state.loading)
        onSuccess(state.data)
        onError(state.error)
    }

    private fun onSuccess(data: MutableList<HeroItem>) {
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
        if (error.isNotEmpty()) {
            llError.isVisible = true
            btnTryAgain.setOnClickListener {
                viewModel.requestHeroesList()
            }
        }
    }

    private fun onLoading(state: Boolean) {
        loading.isVisible = state
        llError.isVisible = false
    }

    override fun favoriteOnClickListener(state: Boolean, item: HeroItem) {
        viewModel.saveFavorite(state, item)
    }

    override fun heroItemCliclListener(id: String) {
        requireActivity().launch<HeroDetailActivity> {
            putExtra(HeroDetailActivity.HERO_DETAIL_ID, id)
        }
    }
}