package io.github.thefrsh.stratus.service.web

import io.github.thefrsh.stratus.transfer.LoginTransfer
import io.github.thefrsh.stratus.transfer.TokenTransfer
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface LoginWebService
{
    @GET("/login")
    fun login(@Body loginTransfer: LoginTransfer): Observable<Response<TokenTransfer>>
}
