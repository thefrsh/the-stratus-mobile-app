package io.github.thefrsh.stratus.troubleshooting.validator

import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.model.LoginCredentials
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.util.ResourceProvider
import javax.inject.Inject

class LoginCredentialsValidator @Inject constructor(private val resourceProvider: ResourceProvider)
{
    fun validate(loginCredentials: LoginCredentials)
    {
        val minimumUsernameLength = resourceProvider.getInteger(R.integer.minimum_username_length)
        val minimumPasswordLength = resourceProvider.getInteger(R.integer.minimum_password_length)

        if (loginCredentials.username.isEmpty() || loginCredentials.password.isEmpty())
        {
            throw LoginCredentialsNotValidException("Please fill in the required fields")
        }
        else if (loginCredentials.username.length < minimumUsernameLength)
        {
            throw LoginCredentialsNotValidException("Username must be at least " +
                    "$minimumUsernameLength characters long")
        }
        else if (loginCredentials.password.length < minimumPasswordLength)
        {
            throw LoginCredentialsNotValidException("Password must be at least " +
                    "$minimumPasswordLength characters long")
        }
    }
}
