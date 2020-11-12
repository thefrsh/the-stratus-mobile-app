package io.github.thefrsh.stratus.viewmodel

interface RegisterViewModel
{
    fun getUsername(): String

    fun getEmail(): String

    fun getPassword(): String

    fun getConfirmPassword(): String

    fun setUsername(username: String)

    fun setEmail(email: String)

    fun setPassword(password: String)

    fun setConfirmPassword(password: String)
}