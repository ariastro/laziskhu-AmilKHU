package org.laziskhu.amilkhu.di.module

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import org.laziskhu.amilkhu.R

@GlideModule
class MyGlideModule: AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val req = RequestOptions()
            .placeholder(R.drawable.image_placholder)
            .error(R.drawable.image_placholder)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        builder.setDefaultRequestOptions(req)
    }
}