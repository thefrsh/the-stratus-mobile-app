package io.github.thefrsh.stratus.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import io.github.thefrsh.stratus.fragment.dialog.RemoveContactDialog
import io.github.thefrsh.stratus.rx.ContactClickedMessage
import io.github.thefrsh.stratus.rx.SnackbarMessage
import io.github.thefrsh.stratus.service.web.UserWebService
import io.github.thefrsh.stratus.transfer.ConversationIdTransfer
import io.github.thefrsh.stratus.transfer.ConversationTransfer
import io.github.thefrsh.stratus.util.Const
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ContactsViewModel @Inject constructor(private val sharedPreferences: SharedPreferences,
                                            private val userWebService: UserWebService) {

    private val contactClickedSubject: PublishSubject<ContactClickedMessage> = PublishSubject.create()
    private val snackbarMessageSubject: PublishSubject<SnackbarMessage> = PublishSubject.create()
    private val dialogSubject: PublishSubject<RemoveContactDialog> = PublishSubject.create()

    private val conversations: MutableLiveData<List<ConversationTransfer>> = MutableLiveData()
    private val conversationRemoved: MutableLiveData<Long> = MutableLiveData()

    fun getConversations(): MutableLiveData<List<ConversationTransfer>> {

        val userId = sharedPreferences.getLong(Const.SP_ID_KEY, 0)

        userWebService.getConversations(userId).enqueue(object : Callback<List<ConversationTransfer>> {

            override fun onFailure(call: Call<List<ConversationTransfer>>, t: Throwable) {

                snackbarMessageSubject.onNext(SnackbarMessage("Unable to load contact list. Please check your " +
                        "internet connection"))
            }

            override fun onResponse(call: Call<List<ConversationTransfer>>,
                                    response: Response<List<ConversationTransfer>>) {

                if(response.isSuccessful) {
                    conversations.value = response.body()!!
                }
            }
        })

        return conversations
    }

    fun removeContact(friendId: Long, conversationIdTransfer: ConversationIdTransfer) {

        val userId = sharedPreferences.getLong(Const.SP_ID_KEY, 0)

        userWebService.removeFriend(userId, friendId, conversationIdTransfer).enqueue(object : Callback<Unit> {

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                snackbarMessageSubject.onNext(SnackbarMessage("Unable to load contact list. Please check your " +
                        "internet connection"))
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                if (response.isSuccessful) {
                    conversationRemoved.value = conversationIdTransfer.id
                }
            }
        })
    }

    fun setRemoveContactDialog(dialog: RemoveContactDialog) {
        dialogSubject.onNext(dialog)
    }

    fun contactClicked(): Observable<ContactClickedMessage> = contactClickedSubject

    fun snackbarMessage(): Observable<SnackbarMessage> = snackbarMessageSubject

    fun conversationRemoved(): MutableLiveData<Long> = conversationRemoved

    fun dialogCreated(): Observable<RemoveContactDialog> = dialogSubject

    fun setContactClicked(contactClickedMessage: ContactClickedMessage) {
        contactClickedSubject.onNext(contactClickedMessage)
    }

    fun getUsername() = sharedPreferences.getString(Const.USERNAME, null)
}