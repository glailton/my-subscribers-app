package br.com.grsoft.mysubscribers.ui.subscriber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.grsoft.mysubscribers.R
import br.com.grsoft.mysubscribers.data.repository.SubscriberRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class SubscriberViewModel(
        private val repository: SubscriberRepository
) : ViewModel() {

    sealed class SubscriberState {
        object Inserted : SubscriberState()
    }

    private val _subscribersStateEventData = MutableLiveData<SubscriberState>()
    val subscriberStateEventData: LiveData<SubscriberState>
        get() = _subscribersStateEventData

    private val _messageEventData = MutableLiveData<Int>()
    val messageEventData: LiveData<Int>
        get() = _messageEventData

    fun addSubscriber(name: String, email: String) = viewModelScope.launch {
        try {
            val id = repository.insertSubscriber(name, email)
            if (id > 0) {
                _subscribersStateEventData.value = SubscriberState.Inserted
                _messageEventData.value = R.string.subscriber_inserted_successfully
            }
        } catch (e: Exception) {
            _messageEventData.value = R.string.subscriber_error_t_insert
            Timber.e(e)
        }
    }
}