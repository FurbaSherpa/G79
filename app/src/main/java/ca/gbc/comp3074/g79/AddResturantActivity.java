package ca.gbc.comp3074.g79;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ca.gbc.comp3074.g79.data.Restaurant;
import ca.gbc.comp3074.g79.data.RestaurantRepository;
import ca.gbc.comp3074.g79.ui.RestaurantViewModel;
import ca.gbc.comp3074.g79.ui.RestaurantViewModelFactory;

public class AddResturantActivity extends AppCompatActivity {

    private RestaurantViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_resturant);

        // Initialize ViewModel with G79App + Repository
        G79App app = (G79App) getApplication();
        RestaurantRepository repo = new RestaurantRepository(app.getDb().restaurantDao());
        viewModel = new ViewModelProvider(this, new RestaurantViewModelFactory(repo))
                .get(RestaurantViewModel.class);

        ImageButton back = findViewById(R.id.btnBack);
        back.setOnClickListener(v -> finish());

        EditText name = findViewById(R.id.etRestaurantName);
        EditText address = findViewById(R.id.etRestaurantAddress);
        EditText description = findViewById(R.id.etRestaurantDescription);
        EditText phone = findViewById(R.id.etRestaurantPhone);
        EditText rating = findViewById(R.id.etRestaurantRating);
        EditText tags = findViewById(R.id.etRestaurantTags);

        Button addButton = findViewById(R.id.btnAddRestaurant);
        addButton.setOnClickListener(v -> {
            String nameText = name.getText().toString().trim();
            String addressText = address.getText().toString().trim();
            String descriptionText = description.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();
            String ratingText = rating.getText().toString().trim();
            String tagsText = tags.getText().toString().trim();

            // validation
            if (nameText.isEmpty() || addressText.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            } else {
                // Create Restaurant object (ignoring rating for now, since DB schema doesn’t include it yet)
                Restaurant restaurant = new Restaurant(
                        0, // id auto-generated
                        nameText,
                        addressText,
                        phoneText,
                        descriptionText,
                        tagsText
                );

                // Save via ViewModel
                viewModel.addRestaurant(restaurant);

                Toast.makeText(this, "Restaurant added: " + nameText, Toast.LENGTH_SHORT).show();

                // Finish and return to list
                finish();
            }
        });
    }
}