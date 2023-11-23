package com.katja.colorme

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.random.Random

class surfacePointer (context: Context): SurfaceView(context), SurfaceHolder.Callback, Runnable {
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
        paint1.color = ContextCompat.getColor(context, R.color.blue1)
        paint2.color = ContextCompat.getColor(context, R.color.blue2)
        paint3.color = ContextCompat.getColor(context, R.color.blue3)
        paintText.color = Color.BLACK
        paintText.textAlign = Paint.Align.CENTER
        paintText.textSize = textSize
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
        canvas.let {
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
            try {
                synchronized(holder) {
                    draw()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val y = event.y

                // Check which third of the view was clicked based on y-coordinate
                val thirdHeight = viewHeight / 3

                when {
                    y <= thirdHeight -> {
                        // Top third clicked
                        biggerCircle()
                        println("Större clicked")
                    }

                    y <= 2 * thirdHeight -> {
                        // Middle third clicked
                        smallerCircle()
                        println("Mindre clicked")
                    }

                    else -> {
                        // Bottom third clicked
                        colorCircle()
                        println("Färga clicked")
                    }
                }
            }
        }
        return true
    }


    fun smallerCircle() {
        val oldCircleRadius = SurfaceDisplaySingleton.getInstance(context).retrieveCircleRadius()
        val newCircleRadius = oldCircleRadius - Random.nextInt(10,50)
        println(newCircleRadius)
        if(newCircleRadius >= 2){
            SurfaceDisplaySingleton.getInstance(context).modifyCircleRadius(newCircleRadius)
        }
        else{
            SurfaceDisplaySingleton.getInstance(context).modifyCircleRadius(2f)
            val message = resources.getString(R.string.no_smaller)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun biggerCircle() {
        val oldCircleRadius = SurfaceDisplaySingleton.getInstance(context).retrieveCircleRadius()
        val newCircleRadius = oldCircleRadius + Random.nextInt(10,50)
        println(newCircleRadius)
        println(viewWidth / 2)
        println(viewHeight / 2)
        if(newCircleRadius <= (viewWidth / 2) && newCircleRadius <= (viewHeight / 2)){
            SurfaceDisplaySingleton.getInstance(context).modifyCircleRadius(newCircleRadius)
        }
        else{
            var newRadius = viewWidth / 2
            if(viewWidth > viewHeight)
                newRadius = viewHeight / 2
            SurfaceDisplaySingleton.getInstance(context).modifyCircleRadius(newRadius)
            val message = resources.getString(R.string.no_bigger)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getRandomColor(): Int {
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)
        return Color.rgb(r, g, b)
    }

    fun colorCircle() {
        SurfaceDisplaySingleton.getInstance(context).setCirclePaint(getRandomColor())
    }
}

