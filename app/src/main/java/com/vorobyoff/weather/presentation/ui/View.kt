package com.vorobyoff.weather.presentation.ui

import android.graphics.Rect
import android.view.View
import android.view.View.OnAttachStateChangeListener
import android.view.ViewGroup
import androidx.core.view.*

fun View.addSystemWindowInsetToPadding(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
) {
    val initialPadding: Rect = requestInitialPadding(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updatePadding(
                top = initialPadding.top + if (top) insets.systemWindowInsetTop else 0,
                left = initialPadding.left + if (left) insets.systemWindowInsetLeft else 0,
                right = initialPadding.right + if (right) insets.systemWindowInsetRight else 0,
                bottom = initialPadding.bottom + if (bottom) insets.systemWindowInsetBottom else 0
        )
        insets
    }

    requestInsetsWhenAttached()
}

fun View.addSystemWindowInsetToMargin(
        left: Boolean = false,
        top: Boolean = false,
        right: Boolean = false,
        bottom: Boolean = false
) {
    val initialMargins = requestInitialMargins(this)

    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        view.updateLayoutParams {
            (this as? ViewGroup.MarginLayoutParams)?.let {
                updateMargins(
                        top = initialMargins.top + if (top) insets.systemWindowInsetTop else 0,
                        left = initialMargins.left + if (left) insets.systemWindowInsetLeft else 0,
                        right = initialMargins.right + if (right) insets.systemWindowInsetRight else 0,
                        bottom = initialMargins.bottom + if (bottom) insets.systemWindowInsetBottom else 0
                )
            }
        }
        insets
    }

    requestInsetsWhenAttached()
}

private fun requestInitialPadding(view: View): Rect =
        Rect(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)

private fun requestInitialMargins(view: View): Rect =
        Rect(view.marginLeft, view.marginTop, view.marginRight, view.marginBottom)

fun View.requestInsetsWhenAttached(): Unit = if (!isAttachedToWindow)
    addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View) {
            v.removeOnAttachStateChangeListener(this)
            requestApplyInsets()
        }

        override fun onViewDetachedFromWindow(v: View?): Unit = Unit
    }) else requestApplyInsets()