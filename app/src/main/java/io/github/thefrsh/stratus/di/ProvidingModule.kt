package io.github.thefrsh.stratus.di

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.service.ResourceService
import io.github.thefrsh.stratus.service.web.LoginWebService
import io.github.thefrsh.stratus.service.web.RegisterWebService
import io.github.thefrsh.stratus.service.web.RetrofitAuthorizationInterceptor
import io.github.thefrsh.stratus.service.web.UserWebService
import io.github.thefrsh.stratus.util.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
class ProvidingModule {

    @Provides
    fun resourceProvider(@ApplicationContext context: Context): ResourceService {
        return ResourceService(context)
    }

    @Provides
    fun sharedPreferences(@ApplicationContext context: Context): SharedPreferences {

        val preferencesKey = context.getString(R.string.app_preferences)
        return context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE)
    }

    @Provides
    fun retrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(Const.RETROFIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @WithAuthorization
    fun authRetrofit(sharedPreferences: SharedPreferences): Retrofit {

        val client = OkHttpClient.Builder()
                .addInterceptor(RetrofitAuthorizationInterceptor(sharedPreferences))
                .build()

        return Retrofit.Builder()
                .baseUrl(Const.RETROFIT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
    }

    @Provides
    fun loginWebService(retrofit: Retrofit): LoginWebService {
        return retrofit.create(LoginWebService::class.java)
    }

    @Provides
    fun registerWebService(retrofit: Retrofit): RegisterWebService {
        return retrofit.create(RegisterWebService::class.java)
    }

    @Provides
    fun userWebService(@WithAuthorization retrofit: Retrofit): UserWebService {
        return retrofit.create(UserWebService::class.java)
    }
}
