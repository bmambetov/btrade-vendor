package com.btradesm.storemanager.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.btradesm.storemanager.Config.BaseURL;
import com.btradesm.storemanager.Config.SharedPref;
import com.btradesm.storemanager.Dashboard.AssignSuccess;
import com.btradesm.storemanager.Dashboard.MyOrderDeatil;
import com.btradesm.storemanager.Model.TodayOrderModel;
import com.btradesm.storemanager.NetworkConnectivity.NetworkConnection;
import com.btradesm.storemanager.R;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class My_Today_Order_Adapter extends RecyclerView.Adapter<My_Today_Order_Adapter.MyViewHolder> {

    private List<TodayOrderModel> modelList;
    private LayoutInflater inflater;
    String select="false";
    SharedPreferences preferences;
    private Fragment currentFragment;
    private Context context;


    public My_Today_Order_Adapter(Context context, List<TodayOrderModel> modemodelList, final Fragment currentFragment) {

        this.context = context;
        this.modelList = modelList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.currentFragment = currentFragment;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_ammount, tv_assign_to, tv_status, tv_order_id, tv_customer_name, tv_customer_socity, tv_customer_phone,
                tv_order_date, tv_order_time, Assign_Boy_name, payment_mode;
        public RelativeLayout Assign_Order_to_layout, Assign_Order_button;
        public CardView card_view;
        int Selected_Boy = 0;
        String SelectBoy = "";
        ArrayList<String> Boy_List = new ArrayList<>();
        String Boy_Id;
        ArrayList<String> BOY_LIST_ID = new ArrayList<>();
        private JsonObject Json;
        private LinearLayout assign_layout;


        public MyViewHolder(View view) {
            super(view);
            tv_ammount = (TextView) view.findViewById(R.id.ammount);
            payment_mode = (TextView) view.findViewById(R.id.payment_mode);
            tv_assign_to = (TextView) view.findViewById(R.id.assign_to);
            tv_status = (TextView) view.findViewById(R.id.status);
            tv_order_id = (TextView) view.findViewById(R.id.order_id);
            tv_customer_name = (TextView) view.findViewById(R.id.customer_name);
            tv_customer_socity = (TextView) view.findViewById(R.id.order_socity);
            tv_customer_phone = (TextView) view.findViewById(R.id.customer_phone);
            tv_order_date = (TextView) view.findViewById(R.id.order_date);
            tv_order_time = (TextView) view.findViewById(R.id.order_time);
            Assign_Order_to_layout = (RelativeLayout) view.findViewById(R.id.assign_order_to);
            Assign_Boy_name = (TextView) view.findViewById(R.id.order_assign_list);
            Assign_Order_button = (RelativeLayout) view.findViewById(R.id.assign_order);
            assign_layout = (LinearLayout) view.findViewById(R.id.assign_layout);
            Assign_Order_to_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Get_Boys();

                }
            });
            Assign_Order_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select.contains("true")) {
                        int pos = getAdapterPosition();
                        final String id = modelList.get(pos).getSale_id();
                        final String getname = SharedPref.getString(context, BaseURL.KEY_DELIVERY_BOY_NAME);
                        if (NetworkConnection.connectionChecking(context)) {
                            RequestQueue rq = Volley.newRequestQueue(context);
                            StringRequest postReq = new StringRequest(Request.Method.POST, BaseURL.ASSIGN_ORDER,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.i("eclipse", "Response=" + response);
                                            try {
                                                JSONObject object = new JSONObject(response);
                                                JSONArray Jarray = object.getJSONArray("assign");
                                                for (int i = 0; i < Jarray.length(); i++) {
                                                    JSONObject json_data = Jarray.getJSONObject(i);
                                                    String msg = json_data.getString("msg");
                                                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(context, AssignSuccess.class);
                                                    context.startActivity(intent);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("Error [" + error + "]");
                                }
                            }) {

                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("order_id", id);
                                    params.put("boy_name", getname);

                                    return params;
                                }
                            };
                            rq.add(postReq);
                        } else {
                            Intent intent = new Intent(context, NetworkError.class);
                            context.startActivity(intent);
                        }
                    }else {
                        Toast.makeText(context, "Select Delivery Boy First", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
                    }
                }
            });


        }

        private void SelectBoyDialog() {
            final Dialog dialog;
            dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.activity_assign_order_dialog);
            final ListView listView = (ListView) dialog.findViewById(R.id.list_order);
            SelectDeliveryBoyListViewAdapter sec = new SelectDeliveryBoyListViewAdapter(context, Boy_List);
            listView.setAdapter(sec);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    select="true";
                    SelectBoy = (String) adapterView.getItemAtPosition(position);
                    Assign_Boy_name.setText(StringUtils.capitalize(Boy_List.get(position).toLowerCase().trim()));
                    SelectBoy = Assign_Boy_name.getText().toString();
                    SharedPref.putString(context, BaseURL.KEY_DELIVERY_BOY_NAME, SelectBoy);
                    Selected_Boy = position + 1;
                    Boy_Id = ("" + BOY_LIST_ID.get(position));
                    SharedPref.putString(context, BaseURL.KEY_DELIVERY_BOY_ID, Boy_Id);
                    dialog.dismiss();
                }
            });
            dialog.getWindow().getDecorView().setTop(100);
            dialog.getWindow().getDecorView().setLeft(100);
            dialog.show();

        }

        private void Get_Boys() {
            if (NetworkConnection.connectionChecking(context)) {
                Json = new JsonObject();
                Ion.with(context).load(BaseURL.DELIVERY_BOY)
                        .setTimeout(15000).setJsonObjectBody(Json).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            Log.e("result", result);
                            try {
                                JSONObject js = new JSONObject(result);
                                {
                                    JSONArray obj = js.getJSONArray("delivery_boy");
                                    Boy_List.clear();
                                    BOY_LIST_ID.clear();
                                    for (int i = 0; i < obj.length(); i++) {
                                        Boy_List.add("" + obj.getJSONObject(i).optString("user_name"));
                                        BOY_LIST_ID.add("" + obj.getJSONObject(i).optString("id"));
                                    }
                                    Log.e("Size", "" + Boy_List.size());
                                    Log.e("result", js.toString() + "\n" + js.getJSONArray("delivery_boy") + "\n"
                                            + obj.getJSONObject(0).optString("user_name"));
                                }
                                SelectBoyDialog();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });

            } else {
                Toast.makeText(context, "No Internet Connnection", Toast.LENGTH_SHORT).show();
            }
        }


    }

    public My_Today_Order_Adapter(Activity activity, List<TodayOrderModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public My_Today_Order_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_today_order_rv, parent, false);
        context = parent.getContext();
        return new My_Today_Order_Adapter.MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(My_Today_Order_Adapter.MyViewHolder holder, int position) {
        TodayOrderModel mList = modelList.get(position);
        holder.tv_ammount.setText(mList.getTotal_amount() +context.getResources().getString(R.string.currency));
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
            holder.assign_layout.setVisibility(View.VISIBLE);
        } else if (mList.getAssign_to() != "0") {
            holder.tv_assign_to.setText(context.getResources().getString(R.string.assign_to) + mList.getAssign_to());
            holder.assign_layout.setVisibility(View.GONE);
        }
        holder.payment_mode.setText(mList.getPayment_method());
        holder.tv_order_id.setText(mList.getSale_id());
        holder.tv_customer_name.setText(mList.getUser_fullname());
        holder.tv_customer_socity.setText(mList.getSocity_name());
        holder.tv_customer_phone.setText(mList.getUser_phone());
        holder.tv_order_date.setText(mList.getOn_date());
        preferences = context.getSharedPreferences("lan", MODE_PRIVATE);
        String language=preferences.getString("language","");
        if (language.contains("spanish")) {
            String timefrom=mList.getDelivery_time_from();
            String timeto=mList.getDelivery_time_to();

            timefrom=timefrom.replace("pm","م");
            timefrom=timefrom.replace("am","ص");

            timeto=timeto.replace("pm","م");
            timeto=timeto.replace("am","ص");



            holder.tv_order_time.setText(timefrom + "-" + timeto);
        }else {

            String timefrom=mList.getDelivery_time_from();
            String timeto=mList.getDelivery_time_to();


            holder.tv_order_time.setText(timefrom + "-" + timeto);

        }



    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

}
