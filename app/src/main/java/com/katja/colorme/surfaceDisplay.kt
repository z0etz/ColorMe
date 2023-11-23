package com.katja.colorme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class surfaceDisplay (context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {
    var thread: Thread? = null
    var running = false
    lateinit var canvas: Canvas
    var mholder: SurfaceHolder? = holder
    var viewWidth = 0f
    var viewHeight = 0f

    var circleRadius = 50f
    var circlePaint = Paint()

    private var drawingThread: Thread? = null
    private var drawing = false


    init {
        if (mholder != null) {
            mholder?.addCallback(this)
        }
        setup()
    }

    private fun setup() {
        circlePaint.color = Color.MAGENTA
    }

    fun startDrawing() {
        drawingThread = Thread(this)
        drawing = true
        drawingThread?.start()
    }

    fun stopDrawing() {
        drawing = false
        drawingThread?.join()
    }

    fun update() {
//        TODO("Not yet implemented")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        viewWidth = width.toFloat()
        viewHeight = height.toFloat()
        startDrawing()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stopDrawing()
    }

    fun draw() {
        canvas = holder.lockCanvas()
        canvas?.let {
            canvas.drawColor(Color.BLUE)
            canvas.drawCircle((viewWidth / 2), (viewHeight / 2), circleRadius, circlePaint)
            holder.unlockCanvasAndPost(canvas)
        }
    }

    override fun run() {
        while (drawing) {
            var canvas: Canvas? = null
            try {
                synchronized(holder) {
                    draw()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun retrieveCircleRadius(): Float {
        return circleRadius
    }

    fun getCirclePaintColor(): Int {
        return circlePaint.color
    }

    fun modifyCircleRadius(radius: Float) {
        circleRadius = radius
    }

    fun setCirclePaint(color: Int) {
        circlePaint.color = color
    }


}

