package com.sys0927.flo.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind_image")
fun ImageView.bindImage(imageUrl: String?) {
    Glide.with(this.context).load(imageUrl)
        .into(this)
}
