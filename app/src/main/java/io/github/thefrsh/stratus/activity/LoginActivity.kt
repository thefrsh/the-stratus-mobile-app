package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.databinding.ActivityLoginBinding
import io.github.thefrsh.stratus.viewmodel.LoginViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityLoginBinding
    private lateinit var snackBarEvents: Disposable

    @Inject
    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        snackBarEvents = viewModel.snackbarEvents().subscribe { event ->
            showSnackbar(event.getMessage())
        }
    }

    private fun showSnackbar(message: String)
    {
        val snackbar = Snackbar.make(findViewById<View>(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG)

        snackbar.setAction(getString(R.string.snackbar_dismiss_action)) { snackbar.dismiss() }
        snackbar.setBackgroundTint(getColor(R.color.grey_darken_4))
        snackbar.setTextColor(getColor(R.color.white))
        snackbar.setActionTextColor(getColor(R.color.crimson))

        val layoutParams = snackbar.view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP

        snackbar.show()
    }
}
