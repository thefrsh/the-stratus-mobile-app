package io.github.thefrsh.stratus.service.web

import io.github.thefrsh.stratus.transfer.ConversationIdTransfer
import io.github.thefrsh.stratus.transfer.ConversationTransfer
import retrofit2.Call
import retrofit2.http.*

interface UserWebService {

    @GET("/users/{id}/conversations")
    fun getConversations(@Path("id") id: Long): Call<List<ConversationTransfer>>

    @HTTP(path = "/users/{id}/friends/{friendId}", method = "DELETE", hasBody = true)
    fun removeFriend(@Path("id") userId: Long,
                     @Path("friendId") friendId: Long,
                     @Body conversationIdTransfer: ConversationIdTransfer): Call<Unit>
}