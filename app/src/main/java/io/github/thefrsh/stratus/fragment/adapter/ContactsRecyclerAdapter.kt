package io.github.thefrsh.stratus.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.fragment.dialog.RemoveContactDialog
import io.github.thefrsh.stratus.rx.ContactClickedMessage
import io.github.thefrsh.stratus.transfer.ConversationTransfer
import io.github.thefrsh.stratus.viewmodel.ContactsViewModel

class ContactsRecyclerAdapter(private val layoutInflater: LayoutInflater,
                              private val conversations: List<ConversationTransfer>,
                              private val contactsViewModel: ContactsViewModel)
    : RecyclerView.Adapter<ContactsRecyclerAdapter.ViewHolder>() {

    companion object {
        private const val COMMA_SEPARATOR = ", "
        private const val DEFAULT_ID = 0L
    }

    class ViewHolder(itemView: View, private val contactsViewModel: ContactsViewModel)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var id: Long = DEFAULT_ID
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val removeButton: ImageButton = itemView.findViewById(R.id.contact_remove_button)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            contactsViewModel.setContactClicked(ContactClickedMessage(id, contactName.text as String))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val viewItem = layoutInflater.inflate(R.layout.contacts_item, parent, false)
        return ViewHolder(viewItem, contactsViewModel)
    }

    override fun getItemCount() = conversations.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val conversationTransfer = conversations[position]

        holder.id = conversationTransfer.id

        val stringBuilder = StringBuilder()

        conversationTransfer.participants
            .filter { participant -> participant.username != contactsViewModel.getUsername() }
            .forEach { participant ->
                stringBuilder.append(participant.username).append(COMMA_SEPARATOR)
                holder.removeButton.setOnClickListener {
                    contactsViewModel.setRemoveContactDialog(
                        RemoveContactDialog(contactsViewModel, participant.id, conversationTransfer.id)
                    )
                }
            }

        holder.contactName.text = stringBuilder.toString().dropLast(COMMA_SEPARATOR.length)
    }
}
