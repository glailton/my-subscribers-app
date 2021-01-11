package br.com.grsoft.mysubscribers.ui.subscriber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.grsoft.mysubscribers.data.database.AppDatabase
import br.com.grsoft.mysubscribers.data.database.dao.SubscriberDAO
import br.com.grsoft.mysubscribers.data.repository.DatabaseDataSource
import br.com.grsoft.mysubscribers.data.repository.SubscriberRepository
import br.com.grsoft.mysubscribers.databinding.SubscriberFragmentBinding
import br.com.grsoft.mysubscribers.extensions.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class SubscriberFragment : Fragment() {

    private var _binding: SubscriberFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SubscriberViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val subscriberDAO: SubscriberDAO by lazy { AppDatabase.getInstance(requireContext()).subscriberDAO }
                val repository: SubscriberRepository by lazy { DatabaseDataSource(subscriberDAO) }
                return SubscriberViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SubscriberFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner, Observer { subscriberState ->
            when(subscriberState) {
                is SubscriberViewModel.SubscriberState.Inserted -> {
                    clearFields()
                    hideKeyboard()
                }
            }
        })

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        binding.inputName.text?.clear()
        binding.inputEmail.text?.clear()
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity) {
            parentActivity.hideKeyboard()
        }
    }

    private fun setListeners() {
        binding.buttonSubscriber.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()

            viewModel.addSubscriber(name, email)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
