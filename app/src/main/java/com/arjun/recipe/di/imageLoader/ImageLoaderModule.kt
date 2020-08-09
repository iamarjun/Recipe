package com.arjun.recipe.di.imageLoader

import android.app.Application
import coil.ImageLoader
import coil.util.CoilUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(ApplicationComponent::class)
object ImageLoaderModule {


    @Provides
    fun provideImageLoader(
        application: Application,
        okHttpClientBuilder: OkHttpClient.Builder
    ): ImageLoader {
        return ImageLoader.Builder(application)
            .okHttpClient {
                okHttpClientBuilder
                    .cache(CoilUtils.createDefaultCache(application))
                    .build()
            }
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }

}