package com.codinlog.presentation

import android.content.Context
import android.view.Display
import com.codinlog.presentation.service.PresentationService
import com.codinlog.presentation.util.presentationDisplay
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

@Module
@InstallIn(SingletonComponent::class)
object AppProvide {
    @Provides
    fun providePresentationDisplay(@ApplicationContext context: Context): Display = context.presentationDisplay
}
