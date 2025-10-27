package ca.gbc.comp3074.g79.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ca.gbc.comp3074.g79.data.Restaurant
import ca.gbc.comp3074.g79.data.RestaurantRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repo: RestaurantRepository) : ViewModel() {
    val restaurants: StateFlow<List<Restaurant>> =
        repo.getAll().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addRestaurant(r: Restaurant) {
        viewModelScope.launch { repo.add(r) }
    }

    fun getRestaurantById(id: Int): Flow<Restaurant?> {
        return repo.getById(id)
    }

    fun editRestaurant(r: Restaurant) {
        viewModelScope.launch { repo.edit(r) }
    }

    fun deleteRestaurant(r: Restaurant) {
        viewModelScope.launch { repo.delete(r) }
    }
}

class RestaurantViewModelFactory(private val repo: RestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RestaurantViewModel(repo) as T
    }
}