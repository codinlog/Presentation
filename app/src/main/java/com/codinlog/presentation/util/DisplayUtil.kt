package com.codinlog.presentation.util

import android.content.Context
import android.hardware.display.DisplayManager
import android.media.MediaRouter
import android.view.Display

val Context.presentationDisplay:Display?
    get() = obtainPresentationDisplay1(this)

fun obtainPresentationDisplay1(ctx:Context):Display?{
    val displayManager = ctx.getSystemService(DisplayManager::class.java)
    val displays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION)
    return if (displays.isNotEmpty()) {
        displays[0]
    } else {
        null
    }
}

fun obtainPresentationDisplay2(ctx:Context):Display?{
    val mediaRouter = ctx.getSystemService(MediaRouter::class.java)
    val routeInfo = mediaRouter.getSelectedRoute(MediaRouter.ROUTE_TYPE_LIVE_VIDEO)
    return routeInfo?.presentationDisplay
}