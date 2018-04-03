package edu.upi.cs.drake.anithings.common.extensions

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.data.Resource
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity

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

@BindingAdapter(value = ["animeres"])
fun RecyclerView.setResource(resource: Resource<*>?){
    if(adapter == null || resource?.data == null)
        return

    when(resource){
        is Resource.Success -> (adapter as AnimeAdapter).addAnime(resource.data as List<AnimeEntity>)
        is Resource.Loading -> Log.d("status","loading")
        is Resource.Error -> Log.d("status","error: "+ resource.errorMessage)
    }
}