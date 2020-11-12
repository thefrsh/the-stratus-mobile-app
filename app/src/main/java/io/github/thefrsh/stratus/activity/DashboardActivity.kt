package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.thefrsh.stratus.R

class DashboardActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }
}