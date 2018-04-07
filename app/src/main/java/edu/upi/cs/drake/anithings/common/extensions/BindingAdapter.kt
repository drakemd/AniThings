package edu.upi.cs.drake.anithings.common.extensions

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import edu.upi.cs.drake.anithings.common.adapter.AnimeAdapter
import edu.upi.cs.drake.anithings.data.Resource
import edu.upi.cs.drake.anithings.data.local.entities.AnimeEntity
import edu.upi.cs.drake.anithings.viewmodel.AnimeListViewModel

/**
 * extension function used as binding adapter to load image by using [Glide]
 */
@BindingAdapter(value = ["url"])
fun ImageView.loadImg(imgUrl: String?){
    Glide.with(context).load(imgUrl).into(this)
}

/**
 * this method used as binding adapter to add or display data from depending on its state
 */
@Suppress("UNCHECKED_CAST")
@BindingAdapter(value = ["animeres"])
fun RecyclerView.setResource(resource: Resource<*>?){
    if(adapter == null)
        return

    val animeAdapter: AnimeAdapter = adapter as AnimeAdapter

    when(resource){
        is Resource.Success -> {
            Log.d("status", (resource.data as List<*>).size.toString())
            if((resource.data as List<*>).isEmpty()){
                animeAdapter.showDataNotFound()
            }else{
                animeAdapter.addAnime(resource.data as List<AnimeEntity>)
            }
        }
        is Resource.Loading -> {
            Log.d("status","loading")
        }
        is Resource.Error -> {
            animeAdapter.showConnectionError()
            Log.d("status","error: "+ resource.errorMessage)
        }
    }
}