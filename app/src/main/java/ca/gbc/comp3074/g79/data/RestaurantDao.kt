package ca.gbc.comp3074.g79.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(restaurant: Restaurant)

    @Update
    suspend fun update(restaurant: Restaurant)

    @Query("SELECT * FROM restaurants ORDER BY name ASC")
    fun getAll(): Flow<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE id = :id")
    fun getById(id: Int): Flow<Restaurant?>
}