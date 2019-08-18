package com.tff.dev.appetiserapp.adapters

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tff.dev.appetiserapp.R

/**
 * Binding Adapter
 *
 * This Binding adapter is used here to load image more efficiently. It used together with databinding and image URLs.
 * Glide is the library used to load images.
 *
 * @param imageView the name assinged to the ImageView to laod image
 * @param url the url/source of the image
 */

@BindingAdapter("imgUrl")
fun loadImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .skipMemoryCache(false)
        .placeholder(R.drawable.ic_noimg_placeholder)
        .dontAnimate()
        .into(imageView)
}
