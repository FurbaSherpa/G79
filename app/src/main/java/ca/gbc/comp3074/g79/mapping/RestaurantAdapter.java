package ca.gbc.comp3074.g79.mapping;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ca.gbc.comp3074.g79.Edit;
import ca.gbc.comp3074.g79.R;
import ca.gbc.comp3074.g79.data.Restaurant;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder> {

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
        private final TextView name, address, description, phone, tags;
        private final Button btnEdit, btnDirections, btnDelete;
        private final ImageButton btnShare, btnFacebook, btnTwitter;

        RestaurantViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            address = itemView.findViewById(R.id.textAddress);
            description = itemView.findViewById(R.id.textDescription);
            phone = itemView.findViewById(R.id.textPhone);
            tags = itemView.findViewById(R.id.textTags);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDirections = itemView.findViewById(R.id.btnDirections);
            btnShare = itemView.findViewById(R.id.btnShare);
            btnFacebook = itemView.findViewById(R.id.btnFacebook);
            btnTwitter = itemView.findViewById(R.id.btnTwitter);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        void bind(Restaurant restaurant) {
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            description.setText(restaurant.getDescription());
            phone.setText(restaurant.getPhone());
            tags.setText("Tags: " + restaurant.getTags());

            btnDirections.setOnClickListener(v -> {
                String geoUri = "geo:0,0?q=" + Uri.encode(restaurant.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                v.getContext().startActivity(intent);
            });

            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), Edit.class);
                intent.putExtra("restaurantId", restaurant.getId());
                v.getContext().startActivity(intent);
            });

            btnShare.setOnClickListener(v -> {
                String shareText = "Check out: " + restaurant.getName() + " - " + restaurant.getAddress();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);
                v.getContext().startActivity(Intent.createChooser(intent, "Share via"));
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