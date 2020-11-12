package io.github.thefrsh.stratus.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.model.RegisterCredentials
import javax.inject.Inject

class RegisterViewModelImpl @Inject constructor() : BaseObservable(), RegisterViewModel
{
    private val registerCredentials = RegisterCredentials()

    @Bindable
    override fun getUsername(): String
    {
        return registerCredentials.username
    }

    @Bindable
    override fun getEmail(): String
    {
        return registerCredentials.email
    }

    @Bindable
    override fun getPassword(): String
    {
        return registerCredentials.password
    }

    @Bindable
    override fun getConfirmPassword(): String
    {
        return registerCredentials.confirmPassword
    }

    override fun setUsername(username: String)
    {
        registerCredentials.username = username
        notifyPropertyChanged(BR.viewModel)
    }

    override fun setEmail(email: String)
    {
        registerCredentials.email = email
        notifyPropertyChanged(BR.viewModel)
    }

    override fun setPassword(password: String)
    {
        registerCredentials.password = password
        notifyPropertyChanged(BR.viewModel)
    }

    override fun setConfirmPassword(password: String)
    {
        registerCredentials.confirmPassword = password
        notifyPropertyChanged(BR.viewModel)
    }
}