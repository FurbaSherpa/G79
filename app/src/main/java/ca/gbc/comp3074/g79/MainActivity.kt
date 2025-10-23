package ca.gbc.comp3074.g79

import android.annotation.SuppressLint
import android.os.Build
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        // Setup RecyclerView
        val recycler: RecyclerView = findViewById<RecyclerView>(R.id.recyclerRestaurants)
        adapter = RestaurantAdapter()
        recycler.setAdapter(adapter)
        recycler.setLayoutManager(LinearLayoutManager(this))

        // Observe restaurants Flow
        lifecycleScope.launch {
            viewModel?.restaurants?.collectLatest { list ->
                adapter?.submitList(list)
            }
        }


        // FloatingActionButton to add a restaurant
        val fabAdd: FloatingActionButton = findViewById<FloatingActionButton>(R.id.fabAdd)
        fabAdd.setOnClickListener(android.view.View.OnClickListener { v: android.view.View? ->
            val intent = android.content.Intent(this@MainActivity, AddResturantActivity::class.java)
            startActivity(intent)
        })
    }
}