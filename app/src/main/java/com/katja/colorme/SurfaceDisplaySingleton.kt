package com.katja.colorme

import android.content.Context

object SurfaceDisplaySingleton {
    private var surfaceDisplayInstance: surfaceDisplay? = null

    fun getInstance(context: Context): surfaceDisplay {
        if (surfaceDisplayInstance == null) {
            surfaceDisplayInstance = surfaceDisplay(context)
        }
        return surfaceDisplayInstance!!
    }
}