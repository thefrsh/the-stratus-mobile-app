package io.github.thefrsh.stratus.service

import android.content.Context
import javax.inject.Inject

class ResourceService @Inject constructor(private val context: Context)
{
    fun getColor(id: Int): Int
    {
        return context.getColor(id)
    }

    fun getInteger(id: Int): Int
    {
        return context.resources.getInteger(id)
    }

    fun getString(id: Int): String
    {
        return context.getString(id)
    }
}
