package io.github.thefrsh.stratus.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.thefrsh.stratus.util.ResourceProvider

@Module
@InstallIn(ApplicationComponent::class)
class ProvidingModule
{
    @Provides
    fun resourceProvider(@ApplicationContext context: Context): ResourceProvider
    {
        return ResourceProvider(context)
    }
}
