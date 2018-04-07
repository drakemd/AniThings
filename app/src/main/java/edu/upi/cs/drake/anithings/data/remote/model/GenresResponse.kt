package edu.upi.cs.drake.anithings.data.remote.model

import com.squareup.moshi.Json

/**
 * these classes purpose is to hold the response data from the API
 */
class KitsuGenresResponse(@Json(name = "data") val data: List<GenreDataResponse>)

class GenreDataResponse(@Json(name = "id") val id: Int,
                        @Json(name = "attributes") val attributes: GenreAttResponse)

class GenreAttResponse(@Json(name = "name") val name: String)