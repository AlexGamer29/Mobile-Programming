package com.midterm.nguyenhuuminhduc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.midterm.nguyenhuuminhduc.Interface.ItemClickListener;
import com.midterm.nguyenhuuminhduc.Model.Data;
import com.midterm.nguyenhuuminhduc.R;
import com.midterm.nguyenhuuminhduc.View.DetailsActivity;
import com.midterm.nguyenhuuminhduc.ViewModel.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>  implements Filterable {

    private List<Data> listData;
    private List<Data> listDataSearch;

    private Context context;

    private DataViewModel model;

    public DataAdapter(Context context, List<Data> listData) {
        this.context = context;
        this.listData = listData;
        this.listDataSearch = listData;

        model = new ViewModelProvider((FragmentActivity) context).get(DataViewModel.class);
    }

    public void setDatas(List<Data> listData) {
        this.listData = listData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data data = listData.get(position);

        holder.tvTitle.setText(data.getTitle());
        holder.tvDesc.setText(data.getDesc());
        holder.tvTimestamp.setText(data.getTimeStamp());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    model.delete(listData.get(data.getId()));
                    notifyDataSetChanged();
                } else {
                    //Details Activity
                    Intent detailsIntent = new Intent(context, DetailsActivity.class);
                    String title = listData.get(position).getTitle();
                    String desc = listData.get(position).getDesc();
                    String timeStamp = listData.get(position).getTimeStamp();
                    String lat = listData.get(position).getLat();
                    String lng = listData.get(position).getLng();
                    String addr = listData.get(position).getAddr();
                    String e = listData.get(position).getE();
                    String zip = listData.get(position).getZip();
                    detailsIntent.putExtra("title", "Title: " + title);
                    detailsIntent.putExtra("desc", "Desc: " + desc);
                    detailsIntent.putExtra("timeStamp", "Timestamp: " + timeStamp);
                    detailsIntent.putExtra("lat", "lat: " + lat);
                    detailsIntent.putExtra("lng", "lng: " + lng);
                    detailsIntent.putExtra("addr", "addr: " + addr);
                    detailsIntent.putExtra("e", "e: " + e);
                    detailsIntent.putExtra("zip", "zip: " + zip);
                    context.startActivity(detailsIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString().trim();
                if (strSearch.isEmpty()) {
                    listData = listDataSearch;
                } else {
                    List<Data> listTemp = new ArrayList<>();
                    for (Data data : listDataSearch) {
                        if (data.getTitle().toLowerCase().contains(strSearch.toLowerCase())) {
                            listTemp.add(data);
                        }
                        if (data.getDesc().toLowerCase().contains(strSearch.toLowerCase())) {
                            listTemp.add(data);
                        }
                        if (data.getTimeStamp().toLowerCase().contains(strSearch.toLowerCase())) {
                            listTemp.add(data);
                        }
                    }
                    listData = listTemp;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listData = (List<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView tvTitle;
        private TextView tvDesc;
        private TextView tvTimestamp;

        private ItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            tvTimestamp = itemView.findViewById(R.id.tv_timestamp);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return true;
        }
    }
}
