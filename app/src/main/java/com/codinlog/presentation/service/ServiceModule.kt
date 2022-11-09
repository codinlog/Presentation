package com.codinlog.presentation.service

import android.app.Service
import android.content.Context
import android.view.Display
import com.codinlog.presentation.PresentationDialog
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import javax.inject.Qualifier

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */
@Qualifier
annotation class ServiceContextQualifier

@Module
@InstallIn(ServiceComponent::class)
object ServiceProvide{
    @Provides
    fun provideServiceContextDialog(
        @ServiceContextQualifier context: Context,
        display: Display
    ): PresentationDialog = PresentationDialog(context, display)
}

@Module
@InstallIn(ServiceComponent::class)
abstract class ServiceBinds{
    @Binds
    @ServiceContextQualifier
    abstract fun bindPresentationServiceContext(service: Service): Context
}