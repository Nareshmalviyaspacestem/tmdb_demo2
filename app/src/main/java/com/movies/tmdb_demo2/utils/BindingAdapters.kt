package com.movies.tmdb_demo2.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.movies.tmdb_demo2.R

@BindingAdapter("glide")
fun glides(view: ImageView, url: String?) {
    if (url == null) return
    Glide.with(view).load("https://image.tmdb.org/t/p/w200/$url").error(R.drawable.ic_launcher_background).into(view)
}