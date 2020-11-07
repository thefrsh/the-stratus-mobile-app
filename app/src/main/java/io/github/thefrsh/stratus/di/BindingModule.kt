package io.github.thefrsh.stratus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.thefrsh.stratus.viewmodel.LoginViewModel
import io.github.thefrsh.stratus.viewmodel.LoginViewModelImpl

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule
{
    @Binds
    abstract fun loginViewModel(viewModel: LoginViewModelImpl): LoginViewModel
}