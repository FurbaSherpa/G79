package ca.gbc.comp3074.g79.data

import kotlinx.coroutines.flow.Flow

class RestaurantRepository(private val dao: RestaurantDao) {
    fun getAll(): Flow<List<Restaurant>> = dao.getAll()
    fun getById(id: Int): Flow<Restaurant?> = dao.getById(id)

    suspend fun add(r: Restaurant) = dao.insert(r)
    suspend fun edit(r: Restaurant) = dao.update(r)
}