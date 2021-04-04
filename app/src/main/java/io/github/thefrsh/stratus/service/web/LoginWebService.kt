package io.github.thefrsh.stratus.service.web

import io.github.thefrsh.stratus.transfer.LoginTransfer
import io.github.thefrsh.stratus.transfer.TokenTransfer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginWebService {
    @POST("/login")
    fun login(@Body loginTransfer: LoginTransfer): Call<TokenTransfer>
}
