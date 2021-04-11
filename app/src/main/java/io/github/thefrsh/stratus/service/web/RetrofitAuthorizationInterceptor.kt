package io.github.thefrsh.stratus.service.web

import android.content.SharedPreferences
import io.github.thefrsh.stratus.util.Const
import okhttp3.Interceptor
import okhttp3.Response

class RetrofitAuthorizationInterceptor(private val sharedPreferences: SharedPreferences)
    : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader(Const.AUTHORIZATION_HEADER, Const.BEARER_PREFIX.plus(
                    sharedPreferences.getString(Const.SP_TOKEN_KEY, null)))
                .build()
        )
    }
}