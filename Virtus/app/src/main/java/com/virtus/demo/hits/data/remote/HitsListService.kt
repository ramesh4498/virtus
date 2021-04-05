package com.virtus.demo.hits.data.remote

import com.virtus.demo.hits.data.entities.Hits
import com.virtus.demo.hits.data.entities.HitsList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HitsListService {
    @GET("api/v1/search_by_date?query=android")
    suspend fun getAllHits() : Response<HitsList>
}
