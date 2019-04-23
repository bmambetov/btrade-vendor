package com.btradesm.storemanager.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.btradesm.storemanager.Model.AppUserModel;
import com.btradesm.storemanager.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 27/10/17.
 */

public class AppUserAdapter extends RecyclerView.Adapter<AppUserAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<AppUserModel> list;
    private List<AppUserModel> appuserlistfilter;
    private AppUserAdapterListener listener;

    public AppUserAdapter(Context context, List<AppUserModel> list,AppUserAdapterListener appUserAdapterListener) {
        this.context = context;
        this.appuserlistfilter = list;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.row_app_users, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AppUserModel movie = appuserlistfilter.get(position);
        if (movie.getStatus().equals("1"))
            holder.status.setText(context.getResources().getString(R.string.active));
        holder.status.setTextColor(context.getResources().getColor(R.color.green));
        holder.user_Id.setText(movie.getId());
        holder.user_name.setText(movie.getName());
        holder.user_phone.setText(movie.getPhone());
        holder.user_email.setText(movie.getEmail());
        holder.user_wallet.setText(movie.getWallet()+context.getResources().getString(R.string.currency));
        holder.user_reward.setText(movie.getRewards());
    }

    @Override
    public int getItemCount() {
        return appuserlistfilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    appuserlistfilter = list;
                } else {
                    List<AppUserModel> filteredList = new ArrayList<>();
                    for (AppUserModel row : list) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getId().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    appuserlistfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = appuserlistfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                appuserlistfilter = (ArrayList<AppUserModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView user_Id, user_name, user_phone, user_email, user_wallet, user_reward, status;

        public ViewHolder(View itemView) {
            super(itemView);

            user_Id = itemView.findViewById(R.id.user_id);
            user_name = itemView.findViewById(R.id.user_name);
            user_email = itemView.findViewById(R.id.user_email);
            user_phone = itemView.findViewById(R.id.user_phone);
            user_wallet = itemView.findViewById(R.id.wallet_ammount);
            user_reward = itemView.findViewById(R.id.reward);
            status = itemView.findViewById(R.id.status);

        }
    }
    public interface AppUserAdapterListener {
        void onContactSelected(AppUserModel contact);
    }

}