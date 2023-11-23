package com.katja.colorme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView

//class surfaceDisplay (context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {
//
//    var thread: Thread? = null
//    var running = false
//    lateinit var canvas: Canvas
//    var mholder: SurfaceHolder? = holder
//    var viewWidth = 0
//    var viewHeight = 0
//
//    init {
//        if (mholder != null) {
//            mholder?.addCallback(this)
//        }
//        setup()
//    }
//
//    private fun setup() {
//        TODO("Not yet implemented")
//    }
//
//    fun start() {
//        running = true
//        thread = Thread(this)
//        thread?.start()
//    }
//
//    fun stop() {
//        running = false
//        thread?.join()
//    }
//
//    fun draw() {
//        canvas = holder!!.lockCanvas()
//        canvas.drawColor(Color.BLUE)
////        TODO("Not yet implemented")
//        holder!!.unlockCanvasAndPost(canvas)
//    }
//
//    fun update() {
////        TODO("Not yet implemented")
//    }
//
//    override fun surfaceCreated(holder: SurfaceHolder) {
//    }
//
//    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
//        viewWidth = width
//        viewHeight = height
//        start()
//    }
//
//    override fun surfaceDestroyed(holder: SurfaceHolder) {
//        stop()
//    }
//
//    override fun run() {
//        while (running) {
//            if (mholder?.surface != null) {
//                canvas = mholder!!.lockCanvas()
//                draw()
//                mholder?.unlockCanvasAndPost(canvas)
//            }
//            update()
//            draw()
//        }
//
//
//    }
//}