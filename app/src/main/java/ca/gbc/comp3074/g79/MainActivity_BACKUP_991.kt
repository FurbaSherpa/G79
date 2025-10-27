package ca.gbc.comp3074.g79

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
<<<<<<< HEAD
<<<<<<< HEAD
import android.util.Log.v
=======
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
=======
>>>>>>> origin/main
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
<<<<<<< HEAD
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
=======
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
=======
>>>>>>> origin/main
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

        val btn_info: ImageButton = findViewById(R.id.btn_info)
        btn_info.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
        }


        // Initialize ViewModel
        val app = getApplication() as G79App
        val repo: RestaurantRepository = RestaurantRepository(app.db.restaurantDao())
        viewModel = ViewModelProvider(this, RestaurantViewModelFactory(repo))
            .get<RestaurantViewModel>(RestaurantViewModel::class.java) as RestaurantViewModel?

<<<<<<< HEAD
<<<<<<< HEAD
        // Initialize adapter with delete callback
        adapter = RestaurantAdapter { restaurant ->
            viewModel?.deleteRestaurant(restaurant)
        }

        // Setup RecyclerView
        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerRestaurants)
        adapter = RestaurantAdapter{restaurant ->
            viewModel?.deleteRestaurant(restaurant)}
=======
=======
>>>>>>> origin/main
        // Initialize adapter with delete and edit callback
        adapter = RestaurantAdapter(
            { restaurant ->
            viewModel?.deleteRestaurant(restaurant)
            },
            { restaurant ->
                val intent = Intent(this@MainActivity, Edit::class.java)
                intent.putExtra("restaurantId", restaurant.id)
                startActivity(intent)
            }
        )


        // Setup RecyclerView
        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerRestaurants)
<<<<<<< HEAD
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
=======
>>>>>>> origin/main
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
