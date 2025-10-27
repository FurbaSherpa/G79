package ca.gbc.comp3074.g79

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ca.gbc.comp3074.g79.data.Restaurant
import ca.gbc.comp3074.g79.data.RestaurantRepository
import ca.gbc.comp3074.g79.ui.RestaurantViewModel
import ca.gbc.comp3074.g79.ui.RestaurantViewModelFactory
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Edit : AppCompatActivity() {

    private lateinit var viewModel: RestaurantViewModel
    private var restaurantId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val app = application as G79App
        val repo = RestaurantRepository(app.db.restaurantDao())
        viewModel = ViewModelProvider(this, RestaurantViewModelFactory(repo))[RestaurantViewModel::class.java]

        val name: EditText = findViewById(R.id.name_text)
        val address: EditText = findViewById(R.id.address_text)
        val description: EditText = findViewById(R.id.description_text)
        val phone: EditText = findViewById(R.id.number_text)
        val rating: RatingBar = findViewById(R.id.ratingBar)

        //gets id for restaurant to edit
        restaurantId = intent.getIntExtra("restaurantId", -1)
        if (restaurantId == -1) {
            Toast.makeText(this, "No Restaurant ID provided", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        //populate the text fields with info from database
        lifecycleScope.launch {
            val restaurant = viewModel.getRestaurantById(restaurantId).filterNotNull().first()

            name.setText(restaurant.name)
            address.setText(restaurant.address)
            description.setText(restaurant.description)
            phone.setText(restaurant.phone)
            rating.rating = restaurant.rating.toFloatOrNull() ?: 0.0f
        }

        //save button logic
        val save: Button = findViewById(R.id.btn_save)
        save.setOnClickListener {
            val nameText = name.text.toString().trim()
            val addressText = address.text.toString().trim()
            val descriptionText = description.text.toString().trim()
            val phoneText = phone.text.toString().trim()
            val ratingText = rating.rating.toString()

            if (nameText.isEmpty() || addressText.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show()
            } else {
                val updatedRestaurant = Restaurant(
                    id = restaurantId, // Use the existing ID for an update
                    name = nameText,
                    address = addressText,
                    phone = phoneText,
                    description = descriptionText,
                    rating = ratingText,
                    tags = ""
                )
                viewModel.editRestaurant(updatedRestaurant)
                Toast.makeText(this, "Restaurant updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        val back: Button = findViewById(R.id.btn_back)
        back.setOnClickListener {
            finish()
        }
    }
}