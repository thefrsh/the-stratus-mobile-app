package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.databinding.ActivityLoginBinding
import io.github.thefrsh.stratus.viewmodel.LoginViewModel
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity()
{
    @Inject
    lateinit var viewModel: LoginViewModel

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel
    }
}
