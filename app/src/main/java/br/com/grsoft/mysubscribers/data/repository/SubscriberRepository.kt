package br.com.grsoft.mysubscribers.data.repository

import br.com.grsoft.mysubscribers.data.database.entity.SubscriberEntity

interface SubscriberRepository {

    suspend fun insertSubscriber(name: String, email: String): Long
    suspend fun updateSubscriber(id: Long, name: String, email: String)
    suspend fun deleteSubscriber(id: Long)
    suspend fun deleteAllSubscriber()
    suspend fun getAllSubscribers(): List<SubscriberEntity>
}