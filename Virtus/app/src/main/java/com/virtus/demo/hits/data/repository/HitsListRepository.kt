package com.virtus.demo.hits.data.repository

import com.virtus.demo.hits.data.local.HitsListDao
import com.virtus.demo.hits.data.remote.HitsListRemoteDataSource
import com.virtus.demo.hits.utils.performGetOperation
import javax.inject.Inject

class HitsListRepository @Inject constructor(
    private val remoteDataSource: HitsListRemoteDataSource,
    private val localDataSource: HitsListDao
) {

    fun getHits() = performGetOperation(
        databaseQuery = { localDataSource.getAllHits() },
        networkCall = { remoteDataSource.getHits() },
        saveCallResult = { localDataSource.insertAll(it.hits) }
    )
}