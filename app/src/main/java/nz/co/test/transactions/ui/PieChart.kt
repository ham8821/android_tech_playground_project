package nz.co.test.transactions.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.core.graphics.blue
import nz.co.test.transactions.R
import nz.co.test.transactions.infrastructure.model.PieData
import nz.co.test.transactions.infrastructure.model.PieSlice
import nz.co.test.transactions.ui.states.PieState

class PieChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    // Data
    private var data: PieData? = null

    // State
    private var pieState = PieState.MINIMIZED
    private var initialHeight: Int? = null

    // Graphics
    private val borderPaint = Paint()
    private val linePaint = Paint()
    private val indicatorCirclePaint = Paint()
    private var indicatorCircleRadius = 0f // radius of markers
    private val mainTextPaint = Paint()
    private val oval = RectF() // define the bounds of the complete pie chart circle.

    // Animations
    private val expandAnimator = ValueAnimator.ofInt()
    private val collapseAnimator = ValueAnimator.ofInt()
    private val textAlpha = ValueAnimator.ofInt()
    private val animateExpansion = AnimatorSet()
    private val animateCollapse = AnimatorSet()

    init {
        // line that divides the pie slices
        borderPaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = context.getColor(R.color.white)
        }
        // marker on each pie slice
        indicatorCirclePaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = context.getColor(R.color.warm_grey)
            alpha = 0
        }
        // straight lines connecting the markers on each pie slice to their associated name and value labels.
        linePaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = context.getColor(R.color.warm_grey)
            alpha = 0
        }
        // slice name labels.
        mainTextPaint.apply {
            isAntiAlias = true
            color = context.getColor(R.color.black2)
            alpha = 0
        }
        setupAnimations()
    }

    /**
     * Use the angle between the start and sweep angles to help get position of the indicator circle
     * formula for x pos: (length of line) * cos(middleAngle) + (distance from left edge of screen)
     * formula for y pos: (length of line) * sin(middleAngle) + (distance from top edge of screen)
     *
     * @param key key of pie slice being altered
     */

    fun setIndicatorLocation(key: String) {
        data?.pieSlices?.get(key)?.let {
            val middleAngle = it.sweepAngle / 2 + it.startAngle

            it.indicatorCircleLocation.x =
                (layoutParams.height.toFloat() / 2 - layoutParams.height / 8) *
                        Math.cos(Math.toRadians(middleAngle.toDouble())).toFloat() + width / 2
            it.indicatorCircleLocation.y =
                (layoutParams.height.toFloat() / 2 - layoutParams.height / 8) *
                        Math.sin(Math.toRadians(middleAngle.toDouble()))
                            .toFloat() + layoutParams.height / 2
        }

    }

    // Calculate and sets the dimensions of the pie slices in the pie chart
    fun setPieSliceDimensions() {
        var lastAngle = 0f

        data?.pieSlices?.forEach {
            it.value.startAngle = lastAngle
            it.value.sweepAngle = ((it.value.value / data?.totalValue!!) * 360f).toFloat()
            lastAngle += it.value.sweepAngle

            setIndicatorLocation(it.key)
        }
    }

    fun setData(data: PieData) {
        this.data = data
        setPieSliceDimensions()
        invalidate()
    }

    private fun setCircleBounds(
        top: Float = 0f, bottom: Float = layoutParams.height.toFloat(),
        left: Float = (width / 2) - (layoutParams.height / 2).toFloat(),
        right: Float = (width / 2) + (layoutParams.height / 2).toFloat(),
    ) {
        oval.top = top
        oval.bottom = bottom
        oval.left = left
        oval.right = right
    }

    private fun setGraphicSizes() {
        mainTextPaint.textSize = height / 15f
        borderPaint.strokeWidth = height / 80f
        linePaint.strokeWidth = height / 120f
        indicatorCircleRadius = height / 70f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setCircleBounds()
        setGraphicSizes()
        data?.pieSlices?.forEach {
            setIndicatorLocation(it.key)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        borderPaint.color = context.getColor(R.color.white)
        data?.pieSlices?.let { slices ->
            slices.forEach {
                canvas?.drawArc(oval,
                    it.value.startAngle,
                    it.value.sweepAngle,
                    true,
                    it.value.paint)
                canvas?.drawArc(oval, it.value.startAngle, it.value.sweepAngle, true, borderPaint)
                drawIndicators(canvas, it.value)
            }
        }
    }

    private fun drawIndicators(canvas: Canvas?, pieItem: PieSlice) {
        // draw line & text for indicator circle if on left side of the pie chart
        if (pieItem.indicatorCircleLocation.x < width / 2) {
            drawIndicatorLine(canvas, pieItem, IndicatorAlignment.LEFT)
            drawIndicatorText(canvas, pieItem, IndicatorAlignment.LEFT)
            // draw line & text for indicator circle if on right side of the pie chart
        } else {
            drawIndicatorLine(canvas, pieItem, IndicatorAlignment.RIGHT)
            drawIndicatorText(canvas, pieItem, IndicatorAlignment.RIGHT)
        }
        // draw indicator circles for pie slice
        indicatorCirclePaint.color = context.getColor(R.color.warm_grey)
        canvas?.drawCircle(pieItem.indicatorCircleLocation.x, pieItem.indicatorCircleLocation.y,
            indicatorCircleRadius, indicatorCirclePaint)
    }

    private fun drawIndicatorLine(
        canvas: Canvas?,
        pieItem: PieSlice,
        alignment: IndicatorAlignment,
    ) {
        val xOffset = if (alignment == IndicatorAlignment.LEFT) width / 4 * -1 else width / 4
        linePaint.color = context.getColor(R.color.warm_grey)
        canvas?.drawLine(
            pieItem.indicatorCircleLocation.x,
            pieItem.indicatorCircleLocation.y,
            pieItem.indicatorCircleLocation.x + xOffset,
            pieItem.indicatorCircleLocation.y,
            linePaint
        )
    }

    private fun drawIndicatorText(
        canvas: Canvas?,
        pieItem: PieSlice,
        alignment: IndicatorAlignment,
    ) {
        val xOffset = if (alignment == IndicatorAlignment.LEFT) width / 4 * -1 else width / 4
        if (alignment == IndicatorAlignment.LEFT) mainTextPaint.textAlign = Paint.Align.LEFT
        else mainTextPaint.textAlign = Paint.Align.RIGHT
        mainTextPaint.color = context.getColor(R.color.black2)
        canvas?.drawText(pieItem.name, pieItem.indicatorCircleLocation.x + xOffset,
            pieItem.indicatorCircleLocation.y - 10, mainTextPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) return true
        if (event?.action == MotionEvent.ACTION_UP) {
            when (pieState) {
                // if pie chart is minimized, expand the chart
                PieState.MINIMIZED -> expandPieChart()
                // if pie chart is expanded, minimize the chart
                PieState.EXPANDED -> collapsePieChart()
                else -> {
                } //do nothing
            }
        }
        return super.onTouchEvent(event)
    }

    private fun expandPieChart() {
        expandAnimator.setIntValues(layoutParams.height, (width / 2.5).toInt())
        textAlpha.setIntValues(0, 255)
        animateExpansion.start()
    }

    /**
     * Collapses the view to the original size of the view
     */
    private fun collapsePieChart() {
        Log.d("PIE_CHART", initialHeight.toString())
        initialHeight?.let {
            collapseAnimator.setIntValues(layoutParams.height, it)
            textAlpha.setIntValues(255, 0)
            animateCollapse.start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (initialHeight == null) initialHeight = layoutParams.height
    }

    private fun setupAnimations() {
        // Expands pie chart from minimized state
        expandAnimator.duration = 200
        expandAnimator.interpolator = OvershootInterpolator()
        expandAnimator.addUpdateListener {
            layoutParams.height = it.animatedValue as Int
            requestLayout()
            setCircleBounds()
            setPieSliceDimensions()
            invalidate()
        }
        expandAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                pieState = PieState.EXPANDED
            }
        })

        // Collapses pie chart from expanded state
        collapseAnimator.duration = 200
        collapseAnimator.interpolator = DecelerateInterpolator()
        collapseAnimator.addUpdateListener {
            layoutParams.height = it.animatedValue as Int
            requestLayout()
            setCircleBounds()
            setPieSliceDimensions()
            invalidate()
        }
        collapseAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                pieState = PieState.MINIMIZED
            }
        })

        // Animates showing and hiding project titles
        textAlpha.duration = 300
        textAlpha.interpolator = DecelerateInterpolator()
        textAlpha.addUpdateListener {
            mainTextPaint.alpha = it.animatedValue as Int
            linePaint.alpha = it.animatedValue as Int
            indicatorCirclePaint.alpha = it.animatedValue as Int
            invalidate()
        }

        animateExpansion.play(expandAnimator).with(textAlpha)
        animateCollapse.play(collapseAnimator).with(textAlpha)
    }
}


enum class IndicatorAlignment {
    LEFT, RIGHT
}