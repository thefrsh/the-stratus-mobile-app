package io.github.thefrsh.stratus.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.troubleshooting.validator.RegisterCredentialsValidator
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(private val registerCredentialsValidator: RegisterCredentialsValidator)
    : BaseObservable()
{
    val usernameFieldMessage = ObservableField<String>("Username must not be empty")
    val emailFieldMessage = ObservableField<String>("Email must not be empty")
    val passwordFieldMessage = ObservableField<String>("Password must not be empty")
    val confirmPasswordFieldMessage = ObservableField<String>("Please make sure your passwords match")

    val snackbarEvents: PublishSubject<SnackbarMessage> = PublishSubject.create()

    @Bindable
    var username = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.username)
            usernameFieldMessage.set(registerCredentialsValidator.validateUsername(value))
        }

    @Bindable
    var email = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.email)
            emailFieldMessage.set(registerCredentialsValidator.validateEmail(value))
        }

    @Bindable
    var password = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.password)
            passwordFieldMessage.set(registerCredentialsValidator.validatePassword(value))
        }

    @Bindable
    var confirmPassword = ""
        set(value)
        {
            field = value
            notifyPropertyChanged(BR.confirmPassword)
            confirmPasswordFieldMessage.set(
                registerCredentialsValidator.validateConfirmPassword(value, password))
        }

    fun onDoneButtonClick(v: View)
    {
        if (!isFormCorrect())
        {
            snackbarEvents.onNext(SnackbarMessage("The form contains errors"))
        }
    }

    private fun isFormCorrect(): Boolean
    {
        return usernameFieldMessage.get()?.isEmpty()!! && emailFieldMessage.get()?.isEmpty()!! &&
               passwordFieldMessage.get()?.isEmpty()!! && confirmPasswordFieldMessage.get()?.isEmpty()!!
    }
}