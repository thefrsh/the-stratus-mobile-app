package io.github.thefrsh.stratus.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.fragment.adapter.ContactsRecyclerAdapter
import io.github.thefrsh.stratus.transfer.ConversationTransfer
import io.github.thefrsh.stratus.viewmodel.ContactsViewModel
import io.reactivex.disposables.Disposable

class ContactsFragment(private val viewModel: ContactsViewModel) : Fragment() {

    private var conversations: MutableList<ConversationTransfer> = ArrayList()

    private lateinit var contactClickedSubscription: Disposable
    private lateinit var dialogCreatedSubscription: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_contacts, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.contacts_recycler_view)
        val recyclerAdapter = ContactsRecyclerAdapter(layoutInflater, conversations, viewModel)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recyclerAdapter

        viewModel.getConversations().observe(viewLifecycleOwner, Observer { list ->

            conversations.addAll(list)
            recyclerAdapter.notifyDataSetChanged()
        })

        viewModel.conversationRemoved().observe(viewLifecycleOwner, Observer { id ->

            val filtered = conversations.filter { conversation -> conversation.id != id }

            conversations.clear()
            conversations.addAll(filtered)

            recyclerAdapter.notifyDataSetChanged()
        })

        subscribeEvents()

        return view
    }

    override fun onDestroyView() {

        super.onDestroyView()
        contactClickedSubscription.dispose()
        dialogCreatedSubscription.dispose()
    }

    private fun subscribeEvents() {

        dialogCreatedSubscription = viewModel.dialogCreated().subscribe { dialog ->
            dialog.show(childFragmentManager, "RemoveContactFragment")
        }
    }
}
