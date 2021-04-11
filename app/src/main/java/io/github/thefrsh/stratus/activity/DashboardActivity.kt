package io.github.thefrsh.stratus.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import io.github.thefrsh.stratus.R
import io.github.thefrsh.stratus.service.SnackbarService
import io.github.thefrsh.stratus.util.Const
import io.github.thefrsh.stratus.viewmodel.ContactsViewModel
import io.github.thefrsh.stratus.viewmodel.DashboardViewModel
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var contactClickedSubscription: Disposable
    private lateinit var snackbarMessageSubscription: Disposable

    @Inject
    lateinit var viewModel: DashboardViewModel

    @Inject
    lateinit var contactsViewModel: ContactsViewModel

    @Inject
    lateinit var snackbarService: SnackbarService

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        viewModel.setContentView(this, R.layout.activity_dashboard)
        window.navigationBarColor = getColor(R.color.black)

        subscribeEvents()
    }

    override fun onDestroy() {

        super.onDestroy()

        contactClickedSubscription.dispose()
        snackbarMessageSubscription.dispose()
    }

    private fun subscribeEvents() {

        contactClickedSubscription = contactsViewModel.contactClicked().subscribe { event ->

            val intent = Intent(this, ChatActivity::class.java)

            intent.putExtra(Const.CONVERSATION_ID, event.conversationId)
            intent.putExtra(Const.CONVERSATION_NAME, event.conversationName)

            startActivity(intent)
        }

        snackbarMessageSubscription = contactsViewModel.snackbarMessage().subscribe { event ->
            snackbarService.showSnackbar(this, event.message)
        }
    }
}
