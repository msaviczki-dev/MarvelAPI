package com.msaviczki.marvel_api.helper.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadFromUrl(url: String?) {
    Glide
        .with(this)
        .load(url)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}