package io.github.thefrsh.stratus.viewmodel

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.model.UserCredentials
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor() : BaseObservable(), LoginViewModel
{
    private val userCredentials = UserCredentials()

    @Bindable
    override fun getUsername(): String
    {
        return userCredentials.username
    }

    @Bindable
    override fun getPassword(): String
    {
        return userCredentials.password
    }

    override fun setPassword(password: String)
    {
        this.userCredentials.password = password
        notifyPropertyChanged(BR.viewModel)
    }

    override fun setUsername(username: String)
    {
        this.userCredentials.username = username
        notifyPropertyChanged(BR.viewModel)
    }
}