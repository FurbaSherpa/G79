package ca.gbc.comp3074.g79

import android.annotation.SuppressLint
import android.os.Build
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.gbc.comp3074.g79.data.Restaurant
import ca.gbc.comp3074.g79.data.RestaurantRepository
import ca.gbc.comp3074.g79.mapping.RestaurantAdapter
import ca.gbc.comp3074.g79.ui.RestaurantViewModel
import ca.gbc.comp3074.g79.ui.RestaurantViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var viewModel: RestaurantViewModel? = null
    private var adapter: RestaurantAdapter? = null
    private var fullList: List<Restaurant> = emptyList()

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        // Initialize ViewModel
        val app = getApplication() as G79App
        val repo: RestaurantRepository = RestaurantRepository(app.db.restaurantDao())
        viewModel = ViewModelProvider(this, RestaurantViewModelFactory(repo))
            .get<RestaurantViewModel>(RestaurantViewModel::class.java) as RestaurantViewModel?

        // Initialize adapter with delete callback
        adapter = RestaurantAdapter { restaurant ->
            viewModel?.deleteRestaurant(restaurant)
        }

        // Setup RecyclerView
        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerRestaurants)
        adapter = RestaurantAdapter{restaurant ->
            viewModel?.deleteRestaurant(restaurant)}
        recycler.setAdapter(adapter)
        recycler.setLayoutManager(LinearLayoutManager(this))

        // Observe restaurants Flow
        lifecycleScope.launch {
            viewModel?.restaurants?.collectLatest { list ->
                fullList = list
                adapter?.submitList(list)
            }
        }

        // Setup SearchView to filter by tags
        val searchView: SearchView = findViewById(R.id.view_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                filterByTags(query)
                filterByName(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterByTags(newText)
                filterByName(newText)
                return true
            }
        })


        // FloatingActionButton to add a restaurant
        val fabAdd: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener(android.view.View.OnClickListener { v: android.view.View? ->
            val intent = android.content.Intent(this@MainActivity, AddResturantActivity::class.java)
            startActivity(intent)
        })
    }

    private fun filterByTags(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            fullList
        } else {
            fullList.filter { restaurant ->
                restaurant.tags.contains(query, ignoreCase = true)
            }
        }
        adapter?.submitList(filteredList)
    }
    private fun filterByName(query: String?) {
        val filteredList = if (query.isNullOrEmpty()) {
            fullList
        } else {
            fullList.filter { restaurant ->
                restaurant.name.contains(query, ignoreCase = true)
            }
        }
        adapter?.submitList(filteredList)
    }
}
