package io.github.thefrsh.stratus.viewmodel

import android.view.View
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.reactivex.subjects.PublishSubject

interface LoginViewModel
{
    fun getUsername(): String

    fun getPassword(): String

    fun setUsername(username: String)

    fun setPassword(password: String)

    fun onLoginButtonClick(v: View)

    fun snackbarEvents(): PublishSubject<SnackbarMessage>
}
