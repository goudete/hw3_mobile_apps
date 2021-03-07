package com.example.homeworkthree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{

    List<Location> locations;

    public LocationAdapter(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate a layout from xml and return ViewHolder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //inflate custom layout
        View locationView = inflater.inflate(R.layout.item_location, parent, false);

        //return new view holder
        ViewHolder viewHolder = new ViewHolder(locationView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // populate data into the item through view holder

        //grab data model aka location object
        Location location = locations.get(position);

        //set the view based on the data and view names
        holder.textView_name.setText(location.getName());
        holder.textView_type.setText(location.getType());
        holder.textView_dimension.setText(location.getDimension());

        //loading image
//        Picasso.get().load(beer.getImage_url()).into(holder.imageView_beer);

//        holder.imageView_beer.setOnClickListener((view) -> {
//            Intent intent = new Intent(mContext, FourthActivity.class);
//            intent.putExtra("name", beer.getName());
//            intent.putExtra("description", beer.getDescription());
//            intent.putExtra("abv", beer.getAbv());
//            intent.putExtra("image", beer.getImage_url());
//            intent.putExtra("food_pairing", beer.getFood_pairing());
//            intent.putExtra("brewsters_tips", beer.getBrewers_tips());
//            intent.putExtra("first_brewed", beer.getFirst_brewed());
//
//            mContext.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        // returns total number of items in list
        return locations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name;
        TextView textView_type;
        TextView textView_dimension;

        public ViewHolder (View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_type = itemView.findViewById(R.id.textView_type);
            textView_dimension = itemView.findViewById(R.id.textView_dimension);
        }

    }
}