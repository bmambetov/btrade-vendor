package com.btradesm.storemanager.Dashboard;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.franmontiel.localechanger.LocaleChanger;
import com.btradesm.storemanager.Adapter.Orders_Adapter;
import com.btradesm.storemanager.Config.BaseURL;
import com.btradesm.storemanager.MainActivity;
import com.btradesm.storemanager.Model.OrdersModel;
import com.btradesm.storemanager.R;
import com.btradesm.storemanager.util.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrdersActivity extends AppCompatActivity implements Orders_Adapter.OrderAdpaterListener {
    private static String TAG = OrdersActivity.class.getSimpleName();
    private RecyclerView rv_items;
    private List<OrdersModel> movieList;
    private Orders_Adapter my_today_order_adapter;
    private SearchView searchView;
    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.order));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrdersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        rv_items = (RecyclerView) findViewById(R.id.rv_orderes);
        rv_items.setLayoutManager(new LinearLayoutManager(this));
        if (ConnectivityReceiver.isConnected()) {
            get_Today_Orders();
        } else {
            Toast.makeText(getApplicationContext(), "Network Issue", Toast.LENGTH_SHORT).show();
        }


    }

    private void get_Today_Orders() {
        try {
            RequestQueue rq = Volley.newRequestQueue(this);
            StringRequest postReq = new StringRequest(Request.Method.GET, BaseURL.DAshborad_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            final List<OrdersModel> data = new ArrayList<>();
                            Log.i("eclipse", "Response=" + response);
                            // We set the response data in the TextView
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                JSONArray Jarray = object.getJSONArray("today_orders");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject json_data = Jarray.getJSONObject(i);
                                    OrdersModel brandModel = new OrdersModel();
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
                                    data.add(brandModel);
                                }
                                movieList = new ArrayList<>();
                                my_today_order_adapter = new Orders_Adapter(OrdersActivity.this, data);
                                rv_items.setAdapter(my_today_order_adapter);
                                rv_items.refreshDrawableState();
                                rv_items.smoothScrollToPosition(0);
                            } catch (JSONException e) {
                                Toast.makeText(OrdersActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(OrdersActivity.this, "No Response on the Server", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                my_today_order_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                my_today_order_adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        if (id==R.id.action_language){
            openLanguageDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }


    @Override
    public void onContactSelected(OrdersModel contact) {

    }
    private void openLanguageDialog() {
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_language, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        TextView lEnglish = v.findViewById(R.id.l_english);
        TextView lSpanish = v.findViewById(R.id.l_arabic);
        final AlertDialog dialog = builder.create();

        lEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleChanger.setLocale(Locale.ENGLISH);
                recreate();
                dialog.dismiss();
            }
        });
        lSpanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleChanger.setLocale(new Locale("ar", "ARABIC"));

                recreate();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
