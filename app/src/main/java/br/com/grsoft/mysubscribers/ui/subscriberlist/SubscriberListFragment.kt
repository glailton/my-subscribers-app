package br.com.grsoft.mysubscribers.ui.subscriberlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.grsoft.mysubscribers.data.database.AppDatabase
import br.com.grsoft.mysubscribers.data.database.dao.SubscriberDAO
import br.com.grsoft.mysubscribers.data.database.entity.SubscriberEntity
import br.com.grsoft.mysubscribers.data.repository.DatabaseDataSource
import br.com.grsoft.mysubscribers.data.repository.SubscriberRepository
import br.com.grsoft.mysubscribers.databinding.SubscriberListFragmentBinding
import br.com.grsoft.mysubscribers.ui.subscriber.SubscriberViewModel

class SubscriberListFragment : Fragment() {

    private var _binding: SubscriberListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriberListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO by lazy { AppDatabase.getInstance(requireContext()).subscriberDAO }
                val repository: SubscriberRepository by lazy { DatabaseDataSource(subscriberDAO) }
                return SubscriberListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = SubscriberListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerEvents()
    }

    private fun observerEvents() {
        viewModel.allSubscribersEvent.observe(viewLifecycleOwner) { allSubscribers ->
            val subscriberAdapter = SubscriberListAdapter(allSubscribers)

            with(binding.recyclerSubscribers) {
                setHasFixedSize(true)
                adapter = subscriberAdapter
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}