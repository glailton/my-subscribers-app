package br.com.grsoft.mysubscribers.data.repository

import androidx.lifecycle.LiveData
import br.com.grsoft.mysubscribers.data.database.dao.SubscriberDAO
import br.com.grsoft.mysubscribers.data.database.entity.SubscriberEntity

class DatabaseDataSource(
    private val subscriberDAO: SubscriberDAO
): SubscriberRepository {
    override suspend fun insertSubscriber(name: String, email: String): Long {
        val subscriber = SubscriberEntity(name = name, email = email)
        return subscriberDAO.insert(subscriber)
    }

    override suspend fun updateSubscriber(id: Long, name: String, email: String) {
        val subscriber = SubscriberEntity(name = name, email = email)
        subscriberDAO.update(subscriber)
    }

    override suspend fun deleteSubscriber(id: Long) {
        subscriberDAO.delete(id)
    }

    override suspend fun deleteAllSubscriber() {
        subscriberDAO.deleteAll()
    }

    override suspend fun getAllSubscriber(): LiveData<List<SubscriberEntity>> {
        return subscriberDAO.getAll()
    }
}