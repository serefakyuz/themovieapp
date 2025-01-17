package com.serefa.themovieapp.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(android.R.drawable.ic_menu_gallery)
        .into(this)
}