package io.github.thefrsh.stratus.troubleshooting.validation

import android.widget.EditText
import androidx.databinding.BindingAdapter
import javax.inject.Inject

class LoginFormValidator @Inject constructor()
{
    private var isUsernameValid: Boolean = false
    private var isPasswordValid: Boolean = false

    companion object
    {
        @JvmStatic
        @BindingAdapter("android:usernameValidator")
        fun validateUsername(editText: EditText, username: String)
        {

        }

        @JvmStatic
        @BindingAdapter("android:passwordValidator")
        fun validatePassword(editText: EditText, password: String)
        {

        }
    }

    fun isLoginFormValid(): Boolean
    {
        return isUsernameValid && isPasswordValid
    }
}
