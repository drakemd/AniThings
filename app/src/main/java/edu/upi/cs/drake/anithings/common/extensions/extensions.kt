package edu.upi.cs.drake.anithings.common.extensions

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

fun ImageView.loadImg(imgUrl: String){
    Glide.with(context).load(imgUrl).into(this)
}

fun ImageView.loadImgTransition(imgUrl: String, requestListener: RequestListener<Drawable>){
    Glide.with(context)
            .load(imgUrl)
            .listener(requestListener)
            .into(this)
}

fun Parcel.writeIntList(input:List<Int>) {
    writeInt(input.size) // Save number of elements.
    input.forEach(this::writeInt) // Save each element.
}

fun Parcel.createIntList() : List<Int> {
    val size = readInt()
    val output = ArrayList<Int>(size)
    for (i in 0 until size) {
        output.add(readInt())
    }
    return output
}