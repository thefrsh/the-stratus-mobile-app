package io.github.thefrsh.stratus.fragment.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.transfer.ConversationIdTransfer
import io.github.thefrsh.stratus.viewmodel.ContactsViewModel

class RemoveContactDialog(private val contactsViewModel: ContactsViewModel,
                          private val friendId: Long,
                          private val conversationId: Long): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = AlertDialog.Builder(activity, R.style.AlertDialogStyle)
            .setTitle("Remove Contact")
            .setMessage("Are you sure you want to remove this contact?")
            .setPositiveButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Yes") { _, _ ->
                contactsViewModel.removeContact(friendId, ConversationIdTransfer(conversationId))
            }
            .create()

        dialog.setOnShowListener {

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.crimson,
                activity?.theme))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.crimson,
                activity?.theme))
        }

        return dialog
    }
}