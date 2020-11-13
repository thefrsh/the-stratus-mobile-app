package io.github.thefrsh.stratus.service.web

import io.github.thefrsh.stratus.transfer.RegisterTransfer
import io.github.thefrsh.stratus.transfer.UserTransfer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterWebService
{
    @POST("/register")
    fun register(@Body registerTransfer: RegisterTransfer): Call<UserTransfer>
}