package edu.upi.cs.drake.anithings.repository.api

import com.squareup.moshi.Json
import edu.upi.cs.drake.anithings.repository.model.AnimeData

/**
 * Created by drake on 3/27/2018.
 *
 */

class KitsuAnimeResponse(@Json(name = "data") val data: List<AnimeData>,
                         @Json(name = "included") val included: List<GenreResponse>)

class GenreResponse(@Json(name = "data") val id: Int, @Json(name = "attributes") val genreattresponse: GenreAttResponse)

class GenreAttResponse(@Json(name = "name") val name: String)