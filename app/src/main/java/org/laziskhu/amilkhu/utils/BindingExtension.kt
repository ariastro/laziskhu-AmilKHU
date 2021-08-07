package org.laziskhu.amilkhu.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.GenericTransitionOptions
import de.hdodenhof.circleimageview.CircleImageView
import org.laziskhu.amilkhu.BuildConfig
import org.laziskhu.amilkhu.di.module.GlideApp

@BindingAdapter("setImageUrl")
fun AppCompatImageView.setImageUrl(imageUrl: String?) {
    GlideApp.with(context)
        .load(BuildConfig.IMAGE_URL + imageUrl)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

@BindingAdapter("setImageUrl")
fun CircleImageView.setImageUrl(imageUrl: String?) {
    GlideApp.with(context)
        .load(BuildConfig.IMAGE_URL + imageUrl)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}

@BindingAdapter("loadImage")
fun AppCompatImageView.loadImage(imageDrawable: Int) {
    GlideApp.with(context)
        .load(imageDrawable)
        .transition(GenericTransitionOptions.with(android.R.anim.fade_in))
        .into(this)
}