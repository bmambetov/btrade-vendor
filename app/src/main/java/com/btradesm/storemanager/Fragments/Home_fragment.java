package com.btradesm.storemanager.Fragments;

import android.content.Context;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.btradesm.storemanager.Adapter.My_Nextday_Order_Adapter;
import com.btradesm.storemanager.Adapter.My_Today_Order_Adapter;
import com.btradesm.storemanager.Config.BaseURL;
import com.btradesm.storemanager.Config.SharedPref;
import com.btradesm.storemanager.MainActivity;
import com.btradesm.storemanager.Model.NextdayOrderModel;
import com.btradesm.storemanager.Model.TodayOrderModel;
import com.btradesm.storemanager.R;

import com.btradesm.storemanager.util.Session_management;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home_fragment extends Fragment {

    private static String TAG = Home_fragment.class.getSimpleName();
    private RecyclerView rv_today_orders,rv_next_day_orders;
    private My_Today_Order_Adapter my_today_order_adapter;
    private My_Nextday_Order_Adapter my_nextday_order_adapter;
    private List<TodayOrderModel> movieList = new ArrayList<>();
    private List<NextdayOrderModel> nextdayOrderModels = new ArrayList<>();
    ProgressDialog pd;
    private LinearLayout linearLayout;
    private Session_management sessionManagement;

    public Home_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.app_name));
        rv_today_orders = (RecyclerView) view.findViewById(R.id.rv_today_order);
        rv_next_day_orders=(RecyclerView)view.findViewById(R.id.rv_next_order);
        rv_today_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_next_day_orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTodayOrders();
        getNextDayOrders();

        return view;
    }

    private void getTodayOrders() {
        try {
            sessionManagement = new Session_management(getActivity());
            RequestQueue rq = Volley.newRequestQueue(getActivity());
            StringRequest postReq = new StringRequest(Request.Method.GET, BaseURL.DAshborad_URL + "?user_id=" + sessionManagement.getUserDetails().get("user_id"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            final List<TodayOrderModel> data = new ArrayList<>();
                            Log.e("eclipse", "Response=" + response);
                            // We set the response data in the TextView
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray Jarray = object.getJSONArray("today_orders");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject json_data = Jarray.getJSONObject(i);
                                    TodayOrderModel brandModel = new TodayOrderModel();
                                    brandModel.sale_id = json_data.getString("sale_id");
                                    final String sal=brandModel.sale_id;
                                    SharedPref.putString(getActivity(), BaseURL.KEY_ORDER_ID, sal);
                                    brandModel.user_id = json_data.getString("user_id");
                                    brandModel.on_date = json_data.getString("on_date");
                                    brandModel.delivery_time_from = json_data.getString("delivery_time_from");
                                    brandModel.delivery_time_to = json_data.getString("delivery_time_to");
                                    brandModel.status = json_data.getString("status");
                                    brandModel.note = json_data.getString("note");
                                    brandModel.is_paid = json_data.getString("is_paid");
                                    brandModel.total_amount = json_data.getString("total_amount");
                                    brandModel.total_rewards = json_data.getString("total_rewards");
                                    brandModel.total_kg = json_data.getString("total_kg");
                                    brandModel.total_items = json_data.getString("total_items");
                                    brandModel.socity_id = json_data.getString("socity_id");
                                    brandModel.delivery_address = json_data.getString("delivery_address");
                                    brandModel.location_id = json_data.getString("location_id");
                                    brandModel.delivery_charge = json_data.getString("delivery_charge");
                                    brandModel.new_store_id = json_data.getString("new_store_id");
                                    brandModel.assign_to = json_data.getString("assign_to");
                                    brandModel.payment_method = json_data.getString("payment_method");
                                    brandModel.user_fullname = json_data.getString("user_fullname");
                                    brandModel.user_phone = json_data.getString("user_phone");
                                    brandModel.pincode = json_data.getString("pincode");
                                    brandModel.house_no = json_data.getString("house_no");
                                    brandModel.socity_name = json_data.getString("socity_name");
                                    brandModel.receiver_name = json_data.getString("receiver_name");
                                    brandModel.receiver_mobile = json_data.getString("receiver_mobile");
                                    brandModel.user_status = json_data.getInt("user_status");
                                    data.add(brandModel);
                                }
                                my_today_order_adapter = new My_Today_Order_Adapter(getActivity(), data);
                                rv_today_orders.setAdapter(my_today_order_adapter);
                                rv_today_orders.refreshDrawableState();
                                rv_today_orders.smoothScrollToPosition(0);
                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error [" + error + "]");
                }
            });
            rq.add(postReq);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "No Response on the Server", Toast.LENGTH_LONG).show();
        }
    }

    private void getNextDayOrders() {
        try {
            sessionManagement = new Session_management(getActivity());
            RequestQueue rq = Volley.newRequestQueue(getActivity());
            StringRequest postReq = new StringRequest(Request.Method.GET, BaseURL.DAshborad_URL + "?user_id=" + sessionManagement.getUserDetails().get("user_id"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            final List<NextdayOrderModel> data = new ArrayList<>();
                            Log.i("eclipse", "Response=" + response);
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray Jarray = object.getJSONArray("nextday_orders");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject json_data = Jarray.getJSONObject(i);
                                    NextdayOrderModel brandModel = new NextdayOrderModel();
                                    brandModel.sale_id = json_data.getString("sale_id");
                                    brandModel.user_id = json_data.getString("user_id");
                                    brandModel.on_date = json_data.getString("on_date");
                                    brandModel.delivery_time_from = json_data.getString("delivery_time_from");
                                    brandModel.delivery_time_to = json_data.getString("delivery_time_to");
                                    brandModel.status = json_data.getString("status");
                                    brandModel.note = json_data.getString("note");
                                    brandModel.is_paid = json_data.getString("is_paid");
                                    brandModel.total_amount = json_data.getString("total_amount");
                                    brandModel.total_rewards = json_data.getString("total_rewards");
                                    brandModel.total_kg = json_data.getString("total_kg");
                                    brandModel.total_items = json_data.getString("total_items");
                                    brandModel.socity_id = json_data.getString("socity_id");
                                    brandModel.delivery_address = json_data.getString("delivery_address");
                                    brandModel.location_id = json_data.getString("location_id");
                                    brandModel.delivery_charge = json_data.getString("delivery_charge");
                                    brandModel.new_store_id = json_data.getString("new_store_id");
                                    brandModel.assign_to = json_data.getString("assign_to");
                                    brandModel.payment_method = json_data.getString("payment_method");
                                    brandModel.user_fullname = json_data.getString("user_fullname");
                                    brandModel.user_phone = json_data.getString("user_phone");
                                    brandModel.pincode = json_data.getString("pincode");
                                    brandModel.house_no = json_data.getString("house_no");
                                    brandModel.socity_name = json_data.getString("socity_name");
                                    brandModel.receiver_name = json_data.getString("receiver_name");
                                    brandModel.receiver_mobile = json_data.getString("receiver_mobile");
                                    brandModel.user_status = json_data.getInt("user_status");
                                    data.add(brandModel);
                                }
                                my_nextday_order_adapter = new My_Nextday_Order_Adapter(getActivity(), data);
                                rv_next_day_orders.setAdapter(my_nextday_order_adapter);
                                rv_next_day_orders.refreshDrawableState();
                                rv_next_day_orders.smoothScrollToPosition(0);
                            } catch (JSONException e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error [" + error + "]");
                }
            });
            rq.add(postReq);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "No Response on the Server", Toast.LENGTH_LONG).show();
        }
    }
}


