package com.wesoft.demo

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by james on 2018/7/23.
 */
class GlideImageLoader: ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context).load(path).into(imageView)
    }
}