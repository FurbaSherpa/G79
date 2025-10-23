package ca.gbc.comp3074.g79

import android.app.Application
import androidx.room.Room
import ca.gbc.comp3074.g79.data.RestaurantDatabase

class G79App : Application() {
    lateinit var db: RestaurantDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            RestaurantDatabase::class.java,
            "restaurants.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}