package ca.gbc.comp3074.g79.mapping;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ca.gbc.comp3074.g79.Edit;
import ca.gbc.comp3074.g79.R;
import ca.gbc.comp3074.g79.data.Restaurant;
import ca.gbc.comp3074.g79.data.RestaurantDao;
import ca.gbc.comp3074.g79.data.RestaurantDatabase;
import ca.gbc.comp3074.g79.data.RestaurantDatabase_Impl;
import ca.gbc.comp3074.g79.data.RestaurantRepository;
import ca.gbc.comp3074.g79.ui.RestaurantViewModel;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder> {

    // Interface for Delete button callback
    public interface OnDeleteClickListener {
        void onDelete(Restaurant restaurant);
    }

    private final OnDeleteClickListener deleteListener;
    public RestaurantAdapter(OnDeleteClickListener listener) {
        super(DIFF_CALLBACK);
        this.deleteListener = listener;
    }

    private static final DiffUtil.ItemCallback<Restaurant> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Restaurant>() {
                @Override
                public boolean areItemsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Restaurant oldItem, @NonNull Restaurant newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView address;
        private final TextView description;
        private final TextView phone;
        private final TextView rating;
        private final TextView tags;
        private final Button btnEdit;
        private final Button btnDirections;
        private final Button btnDelete;


        RestaurantViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            address = itemView.findViewById(R.id.textAddress);
            description = itemView.findViewById(R.id.textDescription);
            phone = itemView.findViewById(R.id.textPhone);
            rating = itemView.findViewById(R.id.textRating);
            tags = itemView.findViewById(R.id.textTags);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDirections = itemView.findViewById(R.id.btnDirections);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(Restaurant restaurant) {
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            description.setText(restaurant.getDescription());
            phone.setText(restaurant.getPhone());
            //rating.setText("Rating: " + restaurant.getRating());
            tags.setText("Tags: " + restaurant.getTags());

            // Example: Directions button opens Google Maps
            btnDirections.setOnClickListener(v -> {
                String geoUri = "geo:0,0?q=" + Uri.encode(restaurant.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                v.getContext().startActivity(intent);
            });

            // Example: Edit button launches EditActivity with restaurant ID
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), Edit.class);
                intent.putExtra("restaurantId", restaurant.getId());
                v.getContext().startActivity(intent);
            });

            btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDelete(restaurant);
                    Toast.makeText(v.getContext(), "Restaurant Deleted", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}