package com.btradesm.storemanager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.btradesm.storemanager.Model.AlllProductModel;
import com.btradesm.storemanager.R;


import java.util.ArrayList;
import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder>implements Filterable {

    private Context context;
    private List<AlllProductModel> list;
    private List<AlllProductModel> productListFiltered;
    private AllProductsAdapterListener listener;

    public AllProductsAdapter(Context context, List<AlllProductModel> list, AllProductsAdapterListener listener) {
        this.context = context;
        this.productListFiltered = list;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_all_products, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AlllProductModel movie = productListFiltered.get(position);
        if (movie.getStock().equals("1")){
            holder.stock.setText(context.getResources().getString(R.string.in_st));
        }else if (movie.getStock().equals("2")){
            holder.stock.setText(context.getResources().getString(R.string.out_st));
            holder.stock.setTextColor(context.getResources().getColor(R.color.color_3));
        }
        holder.product_Id.setText(movie.getId());
        holder.product_name.setText(movie.getTitle());
        holder.catogary_id.setText(movie.getCatogary_id());
        holder.increment.setText(movie.getIncrement());
        holder.price.setText(movie.getPrice());
        holder.unit_value.setText(movie.getUnit_value());
        holder.unit.setText(movie.getUnit());
        holder.reward.setText(movie.getReward());
        Glide.with(context)
                .load(movie.getImage())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(holder.productimage);
    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = list;
                } else {
                    List<AlllProductModel> filteredList = new ArrayList<>();
                    for (AlllProductModel row : list) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getCatogary_id().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<AlllProductModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView product_Id, product_name, catogary_id,increment,price,unit_value,unit,reward,stock;
        public ImageView productimage;

        public ViewHolder(View itemView) {
            super(itemView);

            product_Id = itemView.findViewById(R.id.product_id);
            product_name = itemView.findViewById(R.id.product_title);
            catogary_id = itemView.findViewById(R.id.catogary_id);
            increment = itemView.findViewById(R.id.increment);
            price = itemView.findViewById(R.id.price);
            unit_value = itemView.findViewById(R.id.unit_value);
            unit = itemView.findViewById(R.id.unit);
            reward = itemView.findViewById(R.id.reward);
            stock=itemView.findViewById(R.id.stock);
            productimage=itemView.findViewById(R.id.product_image);


        }
    }
    public interface AllProductsAdapterListener {
        void onContactSelected(AlllProductModel contact);
    }

}