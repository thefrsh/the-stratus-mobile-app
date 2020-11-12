package io.github.thefrsh.stratus.viewmodel

import android.content.SharedPreferences
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.gson.Gson
import io.github.thefrsh.stratus.BR
import io.github.thefrsh.stratus.activity.DashboardActivity
import io.github.thefrsh.stratus.rx.IntentMessage
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.service.web.LoginWebService
import io.github.thefrsh.stratus.transfer.ApiError
import io.github.thefrsh.stratus.transfer.LoginTransfer
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.troubleshooting.validator.LoginCredentialsValidator
import io.reactivex.subjects.PublishSubject
import java.net.HttpURLConnection
import javax.inject.Inject

class LoginViewModel
@Inject constructor(private val loginCredentialsValidator: LoginCredentialsValidator,
                    private val sharedPreferences: SharedPreferences,
                    private val loginWebService: LoginWebService) : BaseObservable()
{
    val snackbarEvents: PublishSubject<SnackbarMessage> = PublishSubject.create()
    val intentEvents: PublishSubject<IntentMessage> = PublishSubject.create()

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

            val loginTransfer = LoginTransfer(username, password)

            val disposable = loginWebService.login(loginTransfer).subscribe { response ->
                if (response.isSuccessful)
                {
                    val tokenTransfer = response.body()

                    if (tokenTransfer != null)
                    {
                        sharedPreferences.edit()
                            .putString("token", tokenTransfer.token)
                            .apply()

                        intentEvents.onNext(IntentMessage(DashboardActivity::class.java))
                    }
                }
                else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                {
                    val apiError = Gson().fromJson(response.body().toString(), ApiError::class.java)
                    snackbarEvents.onNext(SnackbarMessage(apiError.message))
                }
            }
        }
        catch (e: LoginCredentialsNotValidException)
        {
            snackbarEvents.onNext(SnackbarMessage(e.message!!))
        }
    }
}
