package br.com.grsoft.mysubscribers.ui.subscriberlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import br.com.grsoft.mysubscribers.data.database.entity.SubscriberEntity
import br.com.grsoft.mysubscribers.databinding.SubscriberItemBinding

class SubscriberListAdapter(
        private val subscribers: List<SubscriberEntity>
): RecyclerView.Adapter<SubscriberListAdapter.SubscriberListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberListViewHolder {
        val binding = SubscriberItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubscriberListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberListViewHolder, position: Int) {
        val subscriber = subscribers[position]

        holder.bindView(subscriber)
    }

    override fun getItemCount() = subscribers.count()

    class SubscriberListViewHolder(private val binding: SubscriberItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val name = binding.textViewName
        private val email = binding.textViewEmail

        fun bindView(subscriber: SubscriberEntity) {
            name.text = subscriber.name
            email.text = subscriber.email
        }

    }
}