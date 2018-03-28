package edu.upi.cs.drake.anithings.common.extensions

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by drake on 3/28/2018.
 *
 */

fun ViewGroup.inflate(layoutId: Int, attachToRoot:Boolean = false): View{
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImg(imgUrl: String){
    Glide.with(context).load(imgUrl).into(this)
}

inline fun <reified T: Parcelable> createParcel(
        crossinline createFromParcel: (Parcel) -> T?): Parcelable.Creator<T> = object: Parcelable.Creator<T>{

    override fun createFromParcel(source: Parcel?): T = createFromParcel(source)
    override fun newArray(size: Int): Array<out T?> = arrayOfNulls(size)
}