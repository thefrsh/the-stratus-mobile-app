package io.github.thefrsh.stratus.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.troubleshooting.validator.LoginCredentialsValidator
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val loginCredentialsValidator: LoginCredentialsValidator)
    : BaseObservable()
{
    val snackbarEvents: PublishSubject<SnackbarMessage> = PublishSubject.create()

    @Bindable
    var username = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.username)
        }

    @Bindable
    var password = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.username)
        }

    fun onLoginButtonClick(v: View)
    {
        try
        {
            loginCredentialsValidator.validate(username, password)
        }
        catch (e: LoginCredentialsNotValidException)
        {
            snackbarEvents.onNext(SnackbarMessage(e.message!!))
        }
    }
}
