package dev.alexknittel.syntact.presentation.common

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator


fun animateOut(vararg views: View) {
    views.forEach {
        it.animate().setDuration(200).alpha(0f).translationXBy(100f).setInterpolator(AccelerateDecelerateInterpolator()).withEndAction {
            it.translationX = 0f
        }.start()
    }
}

fun animateIn(vararg views: View) {
    views.forEach {
        it.translationX = -100f
        it.animate().setDuration(200).alpha(1f).translationXBy(100f).setInterpolator(AccelerateDecelerateInterpolator()).withEndAction {
            it.translationX = 0f
        }.start()
    }
}
