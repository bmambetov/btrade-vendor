package com.btradesm.storemanager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


import com.btradesm.storemanager.Model.StockModel;
import com.btradesm.storemanager.R;

import java.util.ArrayList;
import java.util.List;


public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<StockModel> list;
    private List<StockModel> stockListFiltered;
    private StockAdapterListener listener;

    public StockAdapter(Context context, List<StockModel> list, StockAdapterListener listener) {
        this.context = context;
        this.stockListFiltered = list;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_stock, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final StockModel movie = stockListFiltered.get(position);
        holder.stock_Id.setText(movie.getId());
        holder.stock_name.setText(movie.getName());
        holder.stock_unit.setText(movie.getUnit());
        holder.stock_price.setText(movie.getPrice());

    }

    @Override
    public int getItemCount() {
        return stockListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    stockListFiltered = list;
                } else {
                    List<StockModel> filteredList = new ArrayList<>();
                    for (StockModel row : list) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getId().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    stockListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = stockListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                stockListFiltered = (ArrayList<StockModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stock_Id, stock_name, stock_unit, stock_price;

        public ViewHolder(View itemView) {
            super(itemView);

            stock_Id = itemView.findViewById(R.id.stock_id);
            stock_name = itemView.findViewById(R.id.stock_title);
            stock_price = itemView.findViewById(R.id.stock_price);
            stock_unit = itemView.findViewById(R.id.stock_unit);

        }
    }

    public interface StockAdapterListener {
        void onContactSelected(StockModel contact);
    }

}