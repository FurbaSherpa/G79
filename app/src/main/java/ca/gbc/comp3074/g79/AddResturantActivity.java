package ca.gbc.comp3074.g79;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AddResturantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_resturant);

        ImageButton back =findViewById(R.id.btnBack);
        back.setOnClickListener(v -> finish());

        EditText name = findViewById(R.id.etRestaurantName);
        EditText address = findViewById(R.id.etRestaurantAddress);
        EditText description = findViewById(R.id.etRestaurantDescription);
        EditText phone = findViewById(R.id.etRestaurantPhone);
        EditText rating = findViewById(R.id.etRestaurantRating);
        EditText tags = findViewById(R.id.etRestaurantTags);


        Button addButton = findViewById(R.id.btnAddRestaurant);
        addButton.setOnClickListener(v -> {
            String nameText = name.getText().toString();
            String addressText = address.getText().toString();
            String descriptionText = description.getText().toString();
            String phoneText = phone.getText().toString();
            String ratingText = rating.getText().toString();
            String tagsText = tags.getText().toString();

            //validation
            if (nameText.isEmpty() || addressText.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Restaurant added: " + nameText, Toast.LENGTH_SHORT).show();

                //Clear all fields after successful add
                name.setText("");
                address.setText("");
                description.setText("");
                phone.setText("");
                rating.setText("");
                tags.setText("");
            }
        });
    }
}