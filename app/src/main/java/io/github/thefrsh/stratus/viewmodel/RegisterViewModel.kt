package io.github.thefrsh.stratus.viewmodel

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.google.gson.Gson
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.activity.LoginActivity
import io.github.thefrsh.stratus.rx.IntentMessage
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.service.web.RegisterWebService
import io.github.thefrsh.stratus.transfer.ApiError
import io.github.thefrsh.stratus.transfer.RegisterTransfer
import io.github.thefrsh.stratus.transfer.UserTransfer
import io.github.thefrsh.stratus.troubleshooting.validator.RegisterCredentialsValidator
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RegisterViewModel
@Inject constructor(private val registerCredentialsValidator: RegisterCredentialsValidator,
                    private val registerWebService: RegisterWebService) : BaseObservable()
{
    val usernameFieldMessage = ObservableField<String>("Username must not be empty")
    val emailFieldMessage = ObservableField<String>("Email must not be empty")
    val passwordFieldMessage = ObservableField<String>("Password must not be empty")
    val confirmPasswordFieldMessage = ObservableField<String>("Please make sure your passwords " +
            "match")

    val snackbarEvents: PublishSubject<SnackbarMessage> = PublishSubject.create()
    val intentEvents: PublishSubject<IntentMessage> = PublishSubject.create()

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
        else
        {
            val registerTransfer = RegisterTransfer(username, password, email)

            registerWebService.register(registerTransfer).enqueue(object : Callback<UserTransfer>
            {
                override fun onFailure(call: Call<UserTransfer>, t: Throwable)
                {
                    snackbarEvents.onNext(
                        SnackbarMessage("Unable to connect. Please check your internet connection"))
                }

                override fun onResponse(call: Call<UserTransfer>, response: Response<UserTransfer>)
                {
                    if (response.isSuccessful)
                    {
                        snackbarEvents.onNext(SnackbarMessage(
                            "Account has been successfully created! You can go back and login"))
                    }
                    else
                    {
                        val apiError = Gson().fromJson(
                            response.errorBody()?.string(), ApiError::class.java)

                        snackbarEvents.onNext(SnackbarMessage(apiError.message))
                    }
                }
            })
        }
    }

    fun onGoBackButtonClick(v: View)
    {
        intentEvents.onNext(IntentMessage(LoginActivity::class.java))
    }

    private fun isFormCorrect(): Boolean
    {
        return usernameFieldMessage.get()?.isEmpty()!! && emailFieldMessage.get()?.isEmpty()!! &&
                passwordFieldMessage.get()?.isEmpty()!! && confirmPasswordFieldMessage.get()
            ?.isEmpty()!!
    }
}