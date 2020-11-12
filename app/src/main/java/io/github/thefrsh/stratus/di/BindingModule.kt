package io.github.thefrsh.stratus.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.thefrsh.stratus.viewmodel.LoginViewModel
import io.github.thefrsh.stratus.viewmodel.LoginViewModelImpl
import io.github.thefrsh.stratus.viewmodel.RegisterViewModel
import io.github.thefrsh.stratus.viewmodel.RegisterViewModelImpl

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule
{
    @Binds
    abstract fun loginViewModel(viewModel: LoginViewModelImpl): LoginViewModel

    @Binds
    abstract fun registerViewModel(viewModelImpl: RegisterViewModelImpl): RegisterViewModel
}
