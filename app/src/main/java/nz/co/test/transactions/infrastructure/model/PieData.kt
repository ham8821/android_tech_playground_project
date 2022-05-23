package nz.co.test.transactions.infrastructure.model

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import java.util.*
import kotlin.collections.HashMap

class PieData {
    val pieSlices = HashMap<String, PieSlice>()
    var totalValue = 0.0

    fun add(name: String, value: Double, color: String? = null) {
        if (pieSlices.containsKey(name)) {
            pieSlices[name]?.let { it.value += value }
        } else {
            color?.let {
                pieSlices[name] = PieSlice(name, value, 0f, 0f, PointF(), createPaint(it))
            } ?: run {
                pieSlices[name] = PieSlice(name, value, 0f, 0f, PointF(), createPaint(null))
            }
        }
        totalValue += value
    }

    private fun createPaint(color: String?): Paint {
        val newPaint = Paint()
        color?.let {
            newPaint.color = Color.parseColor(color)
        } ?: run {
            val randomValue = Random()
            newPaint.color = Color.argb(255,
                randomValue.nextInt(255),
                randomValue.nextInt(255),
                randomValue.nextInt(255))
        }
        newPaint.isAntiAlias = true
        return newPaint
    }
}