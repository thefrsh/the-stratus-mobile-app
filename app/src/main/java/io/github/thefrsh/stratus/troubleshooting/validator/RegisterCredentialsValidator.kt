package io.github.thefrsh.stratus.troubleshooting.validator

import android.widget.EditText
import androidx.databinding.BindingAdapter
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.service.ResourceService
import javax.inject.Inject

class RegisterCredentialsValidator @Inject constructor(resourceService: ResourceService)
{
    private val usernameMinLength = resourceService.getInteger(R.integer.minimum_username_length)
    private val passwordMinLength = resourceService.getInteger(R.integer.minimum_password_length)

    fun validateUsername(username: String): String
    {
        var message = ""
        if (username.isEmpty())
        {
            message = "Username must be not empty"
        }
        else if (username.length < usernameMinLength)
        {
            message = "Username must be at least $usernameMinLength characters long"
        }
        return message
    }

    fun validateEmail(email: String): String
    {
        var message = ""
        if (email.isEmpty())
        {
            message = "Email must be not empty"
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            message = "Provided value is not a valid email pattern"
        }
        return message
    }

    fun validatePassword(password: String): String
    {
        var message = ""
        if(password.length < passwordMinLength)
        {
            message = "Password must be at least $passwordMinLength " +
                    "characters long"
        }
        else if (!password.any(Char::isUpperCase))
        {
            message = "Password must contain at least one upper letter"
        }
        else if (!password.any(Char::isLowerCase))
        {
            message = "Password must contain at least one lower letter"
        }
        else if (!password.any(Char::isDigit))
        {
            message = "Password must contain at least one digit"
        }
        return message
    }

    fun validateConfirmPassword(confirmPassword: String, password: String): String
    {
        var message = ""
        if (confirmPassword != password)
        {
            message = "Passwords do not match"
        }
        return message
    }

    companion object
    {
        @JvmStatic
        @BindingAdapter("android:registerValidation")
        fun setRegisterErrorMessage(editText: EditText, message: String)
        {
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && message.isNotEmpty())
                {
                    editText.error = message
                }
            }
        }
    }
}