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

<<<<<<< HEAD
import ca.gbc.comp3074.g79.Edit;
=======
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
import ca.gbc.comp3074.g79.R;
import ca.gbc.comp3074.g79.data.Restaurant;

public class RestaurantAdapter extends ListAdapter<Restaurant, RestaurantAdapter.RestaurantViewHolder> {

<<<<<<< HEAD
=======
    public interface OnEditClickListener {
        void onEdit(Restaurant restaurant);
    }

    // Interface for Delete button callback
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
    public interface OnDeleteClickListener {
        void onDelete(Restaurant restaurant);
    }

<<<<<<< HEAD
    private final OnDeleteClickListener deleteListener;

    public RestaurantAdapter(OnDeleteClickListener listener) {
        super(DIFF_CALLBACK);
        this.deleteListener = listener;
=======
    private final OnEditClickListener editListener;
    private final OnDeleteClickListener deleteListener;
    public RestaurantAdapter(OnDeleteClickListener listener, OnEditClickListener editListener) {
        super(DIFF_CALLBACK);
        this.deleteListener = listener;
        this.editListener = editListener;
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
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
<<<<<<< HEAD
        private final TextView name, address, description, phone, tags;
        private final Button btnEdit, btnDirections, btnDelete;
        private final ImageButton btnShare, btnFacebook, btnTwitter;
=======
        private final TextView name;
        private final TextView address;
        private final TextView description;
        private final TextView phone;
        private final TextView rating;
        private final TextView tags;
        private final Button btnEdit;
        private final Button btnDirections;
        private final ImageButton btnShare;
        private final ImageButton btnFacebook;
        private final ImageButton btnTwitter;
        private final Button btnDelete;

>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b

        RestaurantViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            address = itemView.findViewById(R.id.textAddress);
            description = itemView.findViewById(R.id.textDescription);
            phone = itemView.findViewById(R.id.textPhone);
<<<<<<< HEAD
            tags = itemView.findViewById(R.id.textTags);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDirections = itemView.findViewById(R.id.btnDirections);
            btnShare = itemView.findViewById(R.id.btnShare);
            btnFacebook = itemView.findViewById(R.id.btnFacebook);
            btnTwitter = itemView.findViewById(R.id.btnTwitter);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

=======
            rating = itemView.findViewById(R.id.textRating);
            tags = itemView.findViewById(R.id.textTags);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDirections = itemView.findViewById(R.id.btnDirections);
            btnShare = (ImageButton) itemView.findViewById(R.id.btnShare);
            btnFacebook = (ImageButton) itemView.findViewById(R.id.btnFacebook);
            btnTwitter = (ImageButton) itemView.findViewById(R.id.btnTwitter);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }




>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
        void bind(Restaurant restaurant) {
            name.setText(restaurant.getName());
            address.setText(restaurant.getAddress());
            description.setText(restaurant.getDescription());
            phone.setText(restaurant.getPhone());
<<<<<<< HEAD
            tags.setText("Tags: " + restaurant.getTags());

=======
            //rating.setText("Rating: " + restaurant.getRating());
            tags.setText("Tags: " + restaurant.getTags());

            // Example: Directions button opens Google Maps
>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
            btnDirections.setOnClickListener(v -> {
                String geoUri = "geo:0,0?q=" + Uri.encode(restaurant.getAddress());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                v.getContext().startActivity(intent);
            });

<<<<<<< HEAD
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

=======
            // Example: Edit button launches EditActivity with restaurant ID
            btnEdit.setOnClickListener(v -> {
                if (editListener != null) {
                    editListener.onEdit(restaurant);
                }
            });
            // Share button logic
            btnShare.setOnClickListener(v -> {
                String shareText = "Check out this restaurant:\n" +
                        "Name: " + restaurant.getName() + "\n" +
                        "Address: " + restaurant.getAddress() + "\n" +
                        "Phone: " + restaurant.getPhone() + "\n" +
                        "Tags: " + restaurant.getTags();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, shareText);


                Intent chooser = Intent.createChooser(intent, "Share via");
                v.getContext().startActivity(chooser);
            });

            btnFacebook.setOnClickListener(v -> {
                String shareText = "Check out this restaurant:\n" +
                        restaurant.getName() + " - " + restaurant.getAddress();

                String fallbackUrl = "https://example.com"; // Replace with your app's site if available
                String url = "https://www.facebook.com/sharer/sharer.php?u=" + Uri.encode(fallbackUrl);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                v.getContext().startActivity(intent);
            });

            btnTwitter.setOnClickListener(v -> {
                String tweetText = "Check out this restaurant:\n" +
                        restaurant.getName() + " - " + restaurant.getAddress();

                String tweetUrl = "https://twitter.com/intent/tweet?text=" + Uri.encode(tweetText);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tweetUrl));
                v.getContext().startActivity(intent);
            });



>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
            btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onDelete(restaurant);
                    Toast.makeText(v.getContext(), "Restaurant Deleted", Toast.LENGTH_SHORT).show();
                }
            });
<<<<<<< HEAD
=======

>>>>>>> 69501158b79dd13502fc33c5a480fe9b6b75a37b
        }
    }
}