package com.example.marvellisimo.extensions

import android.widget.ImageView
import com.example.marvellisimo.R
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(context)
            .load(url)
            .into(this);
}