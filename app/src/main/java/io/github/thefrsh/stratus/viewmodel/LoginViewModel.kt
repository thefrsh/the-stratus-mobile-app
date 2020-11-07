package io.github.thefrsh.stratus.viewmodel

interface LoginViewModel
{
    fun getUsername(): String

    fun getPassword(): String

    fun setUsername(username: String)

    fun setPassword(password: String)
}