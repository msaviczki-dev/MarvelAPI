package com.msaviczki.marvel_api.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.msaviczki.marvel_api.R
import com.msaviczki.marvel_api.data.HeroItem
import com.msaviczki.marvel_api.helper.extension.loadFromUrl
import com.msaviczki.marvel_api.helper.extension.onStateChanged
import com.msaviczki.marvel_api.presentation.viewmodel.HeroDetailViewModel
import kotlinx.android.synthetic.main.activity_hero_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class HeroDetailActivity : AppCompatActivity() {

    private val viewModel: HeroDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_detail)
        retriveExtra()
        onObserve()
    }

    private fun onObserve() = onStateChanged(viewModel) { state ->
        onLoading(state.loading)
        onSuccess(state.data)
        onError(state.error)
    }

    private fun retriveExtra() {
        if (intent.hasExtra(HERO_DETAIL_ID)) {
            val id = intent.getStringExtra(HERO_DETAIL_ID).orEmpty()
            btnTryAgain.setOnClickListener { viewModel.requestData(id) }
            viewModel.requestData(id)
        } else {
            finish()
        }
    }

    private fun onSuccess(data: HeroItem?) {
        data?.let { item ->
            txtName.text = item.name
            txtDescription.text = item.decription
            imgHero.loadFromUrl(item.urlImage)
            btnFavorite.isSelected = item.isFavorite
            btnFavorite.setOnClickListener { view ->
                view.isSelected = view.isSelected.not()
                viewModel.saveFavorite(btnFavorite.isSelected, data)
            }
        }
    }

    private fun onLoading(state: Boolean) {
        loading.isVisible = state
        llError.isVisible = false
    }

    private fun onError(error: String) {
        if (error.isNotEmpty()) {
            llError.isVisible = true
        }
    }

    companion object {
        const val HERO_DETAIL_ID = "HERO_DETAIL_ID"
    }
}