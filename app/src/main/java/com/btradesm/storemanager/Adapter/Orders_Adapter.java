package com.btradesm.storemanager.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.btradesm.storemanager.Dashboard.MyOrderDeatil;
import com.btradesm.storemanager.Model.OrdersModel;
import com.btradesm.storemanager.R;

import java.util.ArrayList;
import java.util.List;


public class Orders_Adapter extends RecyclerView.Adapter<Orders_Adapter.MyViewHolder> implements Filterable {

    private List<OrdersModel> modelList;
    private List<OrdersModel> appuserlistfilter;
    private OrderAdpaterListener listener;
    private Context context;

    public Orders_Adapter(Context context, List<OrdersModel> list) {
        this.context = context;
        this.appuserlistfilter = list;
        this.modelList = list;
    }

    @Override
    public Orders_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_rv, parent, false);
        context = parent.getContext();
        return new Orders_Adapter.MyViewHolder(itemView);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ammount, tv_assign_to, tv_status, tv_order_id, tv_customer_name,
                tv_customer_socity, tv_customer_phone, tv_order_date, tv_order_time, payment_mode;

        public MyViewHolder(View view) {
            super(view);
            tv_ammount = (TextView) view.findViewById(R.id.ammount);
            tv_assign_to = (TextView) view.findViewById(R.id.assign_to);
            payment_mode = (TextView) view.findViewById(R.id.payment_mode);
            tv_status = (TextView) view.findViewById(R.id.status);
            tv_order_id = (TextView) view.findViewById(R.id.order_id);
            tv_customer_name = (TextView) view.findViewById(R.id.customer_name);
            tv_customer_socity = (TextView) view.findViewById(R.id.order_socity);
            tv_customer_phone = (TextView) view.findViewById(R.id.customer_phone);
            tv_order_date = (TextView) view.findViewById(R.id.order_date);
            tv_order_time = (TextView) view.findViewById(R.id.order_time);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String saleid = modelList.get(position).getSale_id();
                        String userfullname = modelList.get(position).getUser_fullname();
                        String socity = modelList.get(position).getSocity_name();
                        String customerphone = modelList.get(position).getUser_phone();
                        String date = modelList.get(position).getOn_date();
                        String time = modelList.get(position).getDelivery_time_from() + "-" + modelList.get(position).getDelivery_time_to();
                        String ammount = modelList.get(position).getTotal_amount();
                        String status = modelList.get(position).getStatus();
                        Intent intent = new Intent(context, MyOrderDeatil.class);
                        intent.putExtra("sale_id", saleid);
                        intent.putExtra("user_fullname", userfullname);
                        intent.putExtra("socity", socity);
                        intent.putExtra("customer_phone", customerphone);
                        intent.putExtra("date", date);
                        intent.putExtra("time", time);
                        intent.putExtra("ammount", ammount);
                        intent.putExtra("status", status);
                        context.startActivity(intent);
//
                    }
                }
            });

        }

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
                    appuserlistfilter = modelList;
                } else {
                    List<OrdersModel> filteredList = new ArrayList<>();
                    for (OrdersModel row : modelList) {
                        if (row.getUser_fullname().toLowerCase().contains(charString.toLowerCase()) || row.getUser_phone().contains(charSequence)) {
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
                appuserlistfilter = (ArrayList<OrdersModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(Orders_Adapter.MyViewHolder holder, int position) {
        final OrdersModel mList = appuserlistfilter.get(position);
        holder.tv_ammount.setText(context.getResources().getString(R.string.currency) + mList.getTotal_amount());
        holder.payment_mode.setText(mList.getPayment_method());

        if (mList.getStatus().equals("0")) {
            holder.tv_status.setText(context.getResources().getString(R.string.pending));
        } else if (mList.getStatus().equals("1")) {
            holder.tv_status.setText(context.getResources().getString(R.string.confirm));
        } else if (mList.getStatus().equals("2")) {
            holder.tv_status.setText(context.getResources().getString(R.string.outfordeliverd));
        } else if (mList.getStatus().equals("4")) {
            holder.tv_status.setText(context.getResources().getString(R.string.delivered));
        }

        if (mList.getAssign_to().equals("0")) {
            holder.tv_assign_to.setText("");
        } else if (mList.getAssign_to() != "0") {
            holder.tv_assign_to.setText("Assign To " + mList.getAssign_to());

        }
        holder.tv_order_id.setText(mList.getSale_id());
        holder.tv_customer_name.setText(mList.getUser_fullname());
        holder.tv_customer_socity.setText(mList.getSocity_name());
        holder.tv_customer_phone.setText(mList.getUser_phone());
        holder.tv_order_date.setText(mList.getOn_date());
        holder.tv_order_time.setText(mList.getDelivery_time_from() + "-" + mList.getDelivery_time_to());
    }


    public interface OrderAdpaterListener {
        void onContactSelected(OrdersModel contact);
    }

}
