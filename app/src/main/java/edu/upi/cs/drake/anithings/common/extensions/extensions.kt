package edu.upi.cs.drake.anithings.common.extensions

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener

/**
 * Created by drake on 3/28/2018.
 *
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot:Boolean = false): View{
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

@BindingAdapter(value = ["url"])
fun ImageView.loadImg(imgUrl: String?){
    Glide.with(context).load(imgUrl).into(this)
}