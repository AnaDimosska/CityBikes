package com.example.citybikes.di

import com.example.citybikes.ApiKeyInterceptor
import com.example.citybikes.api.BikesApi
import com.example.citybikes.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton
import kotlin.math.log

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttp(
        interceptors : Set<@JvmSuppressWildcards Interceptor>
    ) : OkHttpClient  {
        val client = OkHttpClient.Builder()
            .apply {
                interceptors.forEach(::addInterceptor)
            }
            .build()
        return client
    }

    @Provides
    @IntoSet
    fun provideHttpLoggingInterceptor() : Interceptor  {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Provides
    @IntoSet
    fun apiKeyInterceptor(apiKeyInterceptor: ApiKeyInterceptor) : Interceptor = apiKeyInterceptor

    @Provides
    @Singleton
    fun retrofit (
        okHttpClient: OkHttpClient
    ) : Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //moshi ili kotlinx
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun bikesApi (retrofit: Retrofit) : BikesApi = retrofit.create()

}