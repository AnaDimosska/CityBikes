package com.example.citybikes.di

import android.content.Context
import androidx.room.Room
import com.example.citybikes.db.CompanyBikesDao
import com.example.citybikes.db.CompanyBikesDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): CompanyBikesDatabase =
        Room.databaseBuilder(context, CompanyBikesDatabase::class.java, "comapanies_db.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Reusable
    fun provideCmeDao(database: CompanyBikesDatabase): CompanyBikesDao = database.getCompanyBikesDao()
}