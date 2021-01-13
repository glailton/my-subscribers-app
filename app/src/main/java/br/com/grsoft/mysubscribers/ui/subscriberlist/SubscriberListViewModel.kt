package br.com.grsoft.mysubscribers.ui.subscriberlist

import androidx.lifecycle.ViewModel
import br.com.grsoft.mysubscribers.data.repository.SubscriberRepository

class SubscriberListViewModel(
        private val repository: SubscriberRepository
) : ViewModel() {

    val allSubscribersEvent = repository.getAllSubscribers()
}