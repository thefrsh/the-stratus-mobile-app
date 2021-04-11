package io.github.thefrsh.stratus.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.viewmodel.ChatViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        viewModel.setContentView(this, R.layout.activity_chat)

        window.navigationBarColor = getColor(R.color.black)
    }
}
