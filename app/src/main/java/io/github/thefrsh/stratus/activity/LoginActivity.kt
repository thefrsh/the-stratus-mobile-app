package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.databinding.ActivityLoginBinding
import io.github.thefrsh.stratus.service.SnackbarService
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

    @Inject
    lateinit var snackbarService: SnackbarService

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        snackBarEvents = viewModel.snackbarEvents.subscribe { event ->
            snackbarService.showSnackbar(this, event.message)
        }
    }
}
