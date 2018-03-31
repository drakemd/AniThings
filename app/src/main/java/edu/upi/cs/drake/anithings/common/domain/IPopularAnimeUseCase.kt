package edu.upi.cs.drake.anithings.common.domain

import edu.upi.cs.drake.anithings.data.local.NewAnimeData
import io.reactivex.Single

/**
 * Created by drake on 3/27/2018.
 * interface use case for popular anime
 */
interface IPopularAnimeUseCase {
    fun getPopularAnimeByPage(page: Int): Single<List<NewAnimeData>>
}