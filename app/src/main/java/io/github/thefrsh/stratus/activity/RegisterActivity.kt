package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.databinding.ActivityRegisterBinding
import io.github.thefrsh.stratus.viewmodel.RegisterViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.viewModel = viewModel
    }
}
