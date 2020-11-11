package io.github.thefrsh.stratus.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.model.UserCredentials
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.util.ResourceProvider
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModelImpl @Inject constructor(private val resourceProvider: ResourceProvider)
    : BaseObservable(), LoginViewModel
{
    private val userCredentials = UserCredentials()
    private val snackbarEvent: PublishSubject<SnackbarMessage> = PublishSubject.create()

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

    override fun onLoginButtonClick(v: View)
    {
        if (userCredentials.username.isEmpty() || userCredentials.password.isEmpty())
        {
            snackbarEvent.onNext(SnackbarMessage("There are empty fields"))
        }
        else if (userCredentials.username.length < resourceProvider.getInteger(R.integer.minimum_login_length))
        {
            snackbarEvent.onNext(SnackbarMessage("Login must be at least 3 characters long"))
        }
        else if (userCredentials.password.length < resourceProvider.getInteger(R.integer.minimum_password_length))
        {
            snackbarEvent.onNext(SnackbarMessage("Password must be at least 6 characters long"))
        }
    }

    override fun snackbarEvents(): PublishSubject<SnackbarMessage>
    {
        return snackbarEvent
    }
}