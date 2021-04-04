package io.github.thefrsh.stratus.viewmodel

import android.content.SharedPreferences
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.activity.DashboardActivity
import io.github.thefrsh.stratus.activity.RegisterActivity
import io.github.thefrsh.stratus.rx.IntentMessage
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.service.web.LoginWebService
import io.github.thefrsh.stratus.transfer.ApiError
import io.github.thefrsh.stratus.transfer.LoginTransfer
import io.github.thefrsh.stratus.transfer.TokenTransfer
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.troubleshooting.validator.LoginCredentialsValidator
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val loginCredentialsValidator: LoginCredentialsValidator,
                    private val sharedPreferences: SharedPreferences,
                    private val loginWebService: LoginWebService) : BaseObservable() {

    val snackbarEvents: PublishSubject<SnackbarMessage> = PublishSubject.create()
    val intentEvents: PublishSubject<IntentMessage> = PublishSubject.create()

    @Bindable
    var username = ""
        set(value) {

            field = value
            notifyPropertyChanged(BR.username)
        }

    @Bindable
    var password = ""
        set(value) {

            field = value
            notifyPropertyChanged(BR.username)
        }

    fun onLoginButtonClick(v: View) {

        try {

            loginCredentialsValidator.validate(username, password)

            val loginTransfer = LoginTransfer(username, password)

            val disposable = loginWebService.login(loginTransfer).enqueue(object : Callback<TokenTransfer> {

                override fun onFailure(call: Call<TokenTransfer>, t: Throwable) {

                    snackbarEvents.onNext(SnackbarMessage("Unable to connect. Please check your" +
                            " internet connection"))
                }

                override fun onResponse(call: Call<TokenTransfer>, response: Response<TokenTransfer>) {

                    if (response.isSuccessful) {

                        val tokenTransfer = response.body()

                        if (tokenTransfer != null) {

                            sharedPreferences.edit()
                                    .putLong("id", tokenTransfer.id)
                                    .putString("token", tokenTransfer.token)
                                    .apply()

                            intentEvents.onNext(IntentMessage(DashboardActivity::class.java))
                        }
                    }
                    else {

                        val apiError = Gson().fromJson(response.errorBody()?.string(),
                                ApiError::class.java)

                        snackbarEvents.onNext(SnackbarMessage(apiError.message))
                    }
                }
            })
        }
        catch (e: LoginCredentialsNotValidException) {

            snackbarEvents.onNext(SnackbarMessage(e.message!!))
        }
    }

    fun onSignUpTextClick(v: View) {

        intentEvents.onNext(IntentMessage(RegisterActivity::class.java))
    }
}
