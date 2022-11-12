package com.codinlog.presentation.core

import android.annotation.SuppressLint
import android.app.Application
import java.lang.reflect.InvocationTargetException

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

interface IApplicationDelegate {
    val applicationProvider: ApplicationProvider
    fun Application.setupApplication()
}

class ApplicationDelegate : IApplicationDelegate {
    override val applicationProvider = ApplicationProvider

    override fun Application.setupApplication() {
        applicationProvider.setup(this)
    }
}

object ApplicationProvider {
    private lateinit var mApplication: Application

    val application: Application
        @SuppressLint("DiscouragedPrivateApi", "PrivateApi")
        get() {
            if (::mApplication.isInitialized) return mApplication
            try {
                mApplication = Class.forName("android.app.ActivityThread")
                    .getDeclaredMethod("currentApplication").invoke(null) as Application
            } catch (ignored: IllegalAccessException) {
            } catch (ignored: InvocationTargetException) {
            } catch (ignored: NoSuchMethodException) {
            } catch (ignored: ClassNotFoundException) {
            } catch (ignored: ClassCastException) {
            }
            return mApplication
        }

    fun setup(application: Application) {
        mApplication = application
    }
}