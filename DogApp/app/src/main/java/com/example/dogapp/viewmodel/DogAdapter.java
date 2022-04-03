package com.example.dogapp.viewmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogapp.R;
import com.example.dogapp.model.DogBreed;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DogAdapter extends RecyclerView.Adapter<DogAdapter.ViewHolder> implements Filterable {

    private List<DogBreed> listDogs;
    private List<DogBreed> listDogsSearch;

    private Context context;

    public DogAdapter(Context context, List<DogBreed> listDogs) {
        this.context = context;
        this.listDogs = listDogs;
        this.listDogsSearch = listDogs;
    }

    @NonNull
    @Override
    public DogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogAdapter.ViewHolder holder, int position) {
        DogBreed dog = listDogs.get(position);

        holder.tvDogName.setText(dog.getName());
        holder.tvOrigin.setText(dog.getOrigin());

        Picasso.get()
                .load(dog.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_baseline_error_24)
                .fit()
                .into(holder.ivUrl);
    }

    @Override
    public int getItemCount() {
        return listDogs.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString().trim();
                if (strSearch.isEmpty()) {
                    listDogs = listDogsSearch;
                } else {
                    List<DogBreed> listDogsTemp = new ArrayList<>();
                    for (DogBreed dog : listDogsSearch) {
                        if (dog.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            listDogsTemp.add(dog);
                        }
//                        if (dog.getOrigin().toLowerCase().contains(strSearch.toLowerCase())) {
//                            listDogsTemp.add(dog);
//                        }
                    }
                    listDogs = listDogsTemp;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listDogs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listDogs = (List<DogBreed>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUrl;
        public TextView tvDogName;
        public TextView tvOrigin;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            ivUrl = view.findViewById(R.id.iv_url);
            tvDogName = view.findViewById(R.id.tv_dog_name);
            tvOrigin = view.findViewById(R.id.tv_origin);
        }
    }
}
