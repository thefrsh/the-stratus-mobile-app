package io.github.thefrsh.stratus.service

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.snackbar.Snackbar
import io.github.thefrsh.stratus.R
import javax.inject.Inject

class SnackbarService @Inject constructor(private val resourceService: ResourceService) {
    fun showSnackbar(activity: Activity, message: String) {
        val snackbar = Snackbar.make(activity.findViewById<View>(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG)

        snackbar.setAction(resourceService
                .getString(R.string.snackbar_dismiss_action)) { snackbar.dismiss() }
        snackbar.setBackgroundTint(resourceService.getColor(R.color.grey_darken_4))
        snackbar.setTextColor(resourceService.getColor(R.color.white))
        snackbar.setActionTextColor(resourceService.getColor(R.color.crimson))

        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP

        snackbar.show()
    }
}