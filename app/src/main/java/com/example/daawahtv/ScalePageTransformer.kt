package com.example.daawahtv

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * محول صفحة مخصص لـ ViewPager2 يقوم بتغيير حجم الصفحات بناءً على موضعها.
 * الصفحات في المنتصف تكون بحجمها الكامل، بينما الصفحات البعيدة يتم تقليص حجمها.
 */
class ScalePageTransformer : ViewPager2.PageTransformer {

    // عامل التكبير الأقصى للصفحة النشطة
    private val MIN_SCALE = 0.85f
    // عامل التعتيم الأدنى للصفحة غير النشطة
    private val MIN_ALPHA = 0.5f

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height

            when {
                position < -1 -> { // هذا يعني أن الصفحة خارج الشاشة إلى اليسار
                    alpha = 0f
                }
                position <= 1 -> { // الصفحات المرئية (بما في ذلك الحالية والصفحات المجاورة)
                    // حساب عامل التحجيم (scale factor) بناءً على الموضع
                    val scaleFactor = Math.max(MIN_SCALE, 1 - abs(position))
                    val vertMargin = pageHeight * (1 - scaleFactor) / 2
                    val horzMargin = pageWidth * (1 - scaleFactor) / 2
                    translationX = if (position < 0) {
                        horzMargin - vertMargin / 2
                    } else {
                        -horzMargin + vertMargin / 2
                    }

                    // تطبيق التحجيم
                    scaleX = scaleFactor
                    scaleY = scaleFactor

                    // تطبيق التعتيم (alpha)
                    alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
                }
                else -> { // هذا يعني أن الصفحة خارج الشاشة إلى اليمين
                    alpha = 0f
                }
            }
        }
    }
}
