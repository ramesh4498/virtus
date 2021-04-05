package com.virtus.demo.hits.di

import android.content.Context
import com.virtus.demo.hits.data.local.AppDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.virtus.demo.hits.data.local.HitsListDao
import com.virtus.demo.hits.data.remote.HitsListRemoteDataSource
import com.virtus.demo.hits.data.remote.HitsListService
import com.virtus.demo.hits.data.repository.HitsListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://hn.algolia.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHitsListService(retrofit: Retrofit): HitsListService = retrofit.create(HitsListService::class.java)

    @Singleton
    @Provides
    fun provideHitsListRemoteDataSource(hitsListService: HitsListService) = HitsListRemoteDataSource(hitsListService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideHitsDao(db: AppDatabase) = db.hitsListDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: HitsListRemoteDataSource,
                          localDataSource: HitsListDao) =
        HitsListRepository(remoteDataSource, localDataSource)
}