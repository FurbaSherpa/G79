package ca.gbc.comp3074.g79;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.btn_add);
        button.setOnClickListener(v->{
            Log.d("BTN", "It works");
            Toast.makeText(MainActivity.this, "Add button pressed", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(MainActivity.this, AddResturant.class);
            startActivity(intent);

        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btn_edit = findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(v->{
            Log.d("BTN", "It works");
            Toast.makeText(MainActivity.this, "Edit button pressed", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(MainActivity.this, AddResturant.class);
            startActivity(intent);

        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btn_directions = findViewById(R.id.btn_directions);
        btn_directions.setOnClickListener(v->{
           Intent intent = new Intent(Intent.ACTION_VIEW,
                   Uri.parse("geo: 43.6410475,-79.7372308"));
           startActivity(intent);
        });

    }
}