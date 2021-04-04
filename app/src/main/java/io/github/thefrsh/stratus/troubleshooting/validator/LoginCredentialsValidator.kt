package io.github.thefrsh.stratus.troubleshooting.validator

import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.troubleshooting.exception.LoginCredentialsNotValidException
import io.github.thefrsh.stratus.service.ResourceService
import javax.inject.Inject

class LoginCredentialsValidator @Inject constructor(private val resourceService: ResourceService) {
    fun validate(username: String, password: String) {
        val usernameMinLength = resourceService.getInteger(R.integer.minimum_username_length)
        val passwordMinLength = resourceService.getInteger(R.integer.minimum_password_length)

        if (username.isEmpty() || password.isEmpty()) {
            throw LoginCredentialsNotValidException("Please fill in the required fields")
        }
        else if (username.length < usernameMinLength) {
            throw LoginCredentialsNotValidException("Username must be at least " +
                    "$usernameMinLength characters long")
        }
        else if (password.length < passwordMinLength) {
            throw LoginCredentialsNotValidException("Password must be at least " +
                    "$passwordMinLength characters long")
        }
    }
}
