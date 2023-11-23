package com.katja.colorme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.Gravity.CENTER
import android.view.SurfaceHolder
import android.view.SurfaceView

class surfacePointer (context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {
    var thread: Thread? = null
    var running = false
    lateinit var canvas: Canvas
    var mholder: SurfaceHolder? = holder
    var viewWidth = 0f
    var viewHeight = 0f
    var paint1 = Paint()
    var paint2 = Paint()
    var paint3 = Paint()
    var paintText = Paint()
    var textSize = 100f
    private var drawingThread: Thread? = null
    private var drawing = false


    init {
        if (mholder != null) {
            mholder?.addCallback(this)
        }
        setup()
    }

    private fun setup() {
        paint1.color = Color.RED
        paint2.color = Color.YELLOW
        paint3.color = Color.GREEN
        paintText.color = Color.BLACK
        paintText.textAlign = Paint.Align.CENTER
        paintText.textSize = textSize
    }

    fun start() {
        running = true
        thread = Thread(this)
        thread?.start()
    }

    fun stop() {
        running = false
        thread?.join()
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
        start()
        startDrawing()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        stop()
    }

    fun draw() {
        canvas = holder.lockCanvas()
        canvas?.let {
            canvas.drawRect(0f, 0f, viewWidth, (viewHeight / 3), paint1)
            canvas.drawRect(0f, (viewHeight / 3), viewWidth, ((viewHeight / 3) * 2), paint2)
            canvas.drawRect(0f, ((viewHeight / 3) * 2), viewWidth, viewHeight, paint3)
            canvas.drawText("Större", (viewWidth / 2), ((viewHeight / 6) + (textSize / 2)), paintText)
            canvas.drawText("Mindre", (viewWidth / 2), ((viewHeight / 2) + (textSize / 2)), paintText)
            canvas.drawText("Färga", (viewWidth / 2), (viewHeight - (viewHeight / 6) + (textSize / 2)), paintText)
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
}

