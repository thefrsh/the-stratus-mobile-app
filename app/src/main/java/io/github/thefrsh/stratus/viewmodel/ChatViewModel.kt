package io.github.thefrsh.stratus.viewmodel

import androidx.databinding.DataBindingUtil
import io.github.thefrsh.stratus.activity.ChatActivity
import io.github.thefrsh.stratus.databinding.ActivityChatBinding
import javax.inject.Inject

class ChatViewModel @Inject constructor() {

    private lateinit var binding: ActivityChatBinding

    fun setContentView(activity: ChatActivity, layoutId: Int) {
        this.binding = DataBindingUtil.setContentView(activity, layoutId)
    }
}