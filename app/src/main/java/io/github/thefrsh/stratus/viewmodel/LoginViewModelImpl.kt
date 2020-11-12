package io.github.thefrsh.stratus.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.model.LoginCredentials
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.troubleshooting.validator.LoginCredentialsValidator
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(private val loginCredentialsValidator: LoginCredentialsValidator)
    : BaseObservable(), LoginViewModel
{
    private val loginCredentials = LoginCredentials()
    private val snackbarEvent: PublishSubject<SnackbarMessage> = PublishSubject.create()

    @Bindable
    override fun getUsername(): String
    {
        return loginCredentials.username
    }

    @Bindable
    override fun getPassword(): String
    {
        return loginCredentials.password
    }

    override fun setPassword(password: String)
    {
        this.loginCredentials.password = password
        notifyPropertyChanged(BR.viewModel)
    }

    override fun setUsername(username: String)
    {
        this.loginCredentials.username = username
        notifyPropertyChanged(BR.viewModel)
    }

    override fun onLoginButtonClick(v: View)
    {
        try
        {
            loginCredentialsValidator.validate(loginCredentials)
        }
        catch (e: LoginCredentialsNotValidException)
        {
            snackbarEvent.onNext(SnackbarMessage(e.message!!))
        }
    }

    override fun snackbarEvents(): PublishSubject<SnackbarMessage>
    {
        return snackbarEvent
    }
}
