package com.virtus.demo.hits.data.remote

import javax.inject.Inject

class HitsListRemoteDataSource @Inject constructor(
    private val hitsListService: HitsListService
): BaseDataSource() {

    suspend fun getHits() = getResult { hitsListService.getAllHits() }
}