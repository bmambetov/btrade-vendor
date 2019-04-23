package com.btradesm.storemanager.Dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.franmontiel.localechanger.LocaleChanger;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.btradesm.storemanager.Adapter.SelectStockListViewAdapter;
import com.btradesm.storemanager.Config.BaseURL;
import com.btradesm.storemanager.Config.SharedPref;
import com.btradesm.storemanager.MainActivity;
import com.btradesm.storemanager.NetworkConnectivity.NetworkConnection;
import com.btradesm.storemanager.R;
import com.btradesm.storemanager.util.Session_management;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockUpdate extends AppCompatActivity {
    EditText editText_Qty, editText_Unit;
    TextView editText_Product;
    final Context context = this;
    int Selected_Stock_product = 0;
    String SelectProduct = "";
    ArrayList<String> Product_List = new ArrayList<>();
    String Product_Id;
    ArrayList<String> Product_LIST_ID = new ArrayList<>();
    private JsonObject Json;
    private Session_management sessionManagement;
    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = LocaleChanger.configureBaseContext(newBase);
        super.attachBaseContext(newBase);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_update);
        sessionManagement = new Session_management(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.pur_pr));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockUpdate.this, MainActivity.class);
                startActivity(intent);
            }
        });
        editText_Product = (TextView) findViewById(R.id.et_product);
        editText_Qty = (EditText) findViewById(R.id.et_quantity);
        editText_Unit = (EditText) findViewById(R.id.et_unit);
        editText_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Get_Products();


            }
        });

        RelativeLayout Next = (RelativeLayout) findViewById(R.id.btn_add_product);
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = editText_Product.getText().toString();
                if (value.equals(SelectProduct)) {
                    update();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    finish();
                } else {
                    Toast.makeText(context, "Please Select Product", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void SelectProductsDialog() {
        final Dialog dialog;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_stock_product_dialog);
        final ListView listView = (ListView) dialog.findViewById(R.id.list_product);
        SelectStockListViewAdapter sec = new SelectStockListViewAdapter(context, Product_List);
        listView.setAdapter(sec);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SelectProduct = (String) adapterView.getItemAtPosition(position);
                editText_Product.setText(StringUtils.capitalize(Product_List.get(position).toLowerCase().trim()));
                SelectProduct = editText_Product.getText().toString();
                Selected_Stock_product = position + 1;
                Product_Id = ("" + Product_LIST_ID.get(position));
                SharedPref.putString(context, BaseURL.KEY_STOCK_PRODUCTS_ID, Product_Id);
                dialog.dismiss();
            }
        });
        dialog.getWindow().getDecorView().setTop(100);
        dialog.getWindow().getDecorView().setLeft(100);
        dialog.show();

    }

    private void Get_Products() {
        if (NetworkConnection.connectionChecking(context)) {
            Json = new JsonObject();
            Ion.with(context).load(BaseURL.GET_STOCK)
                    .setTimeout(15000).setJsonObjectBody(Json).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    if (e == null) {
                        Log.e("result", result);
                        try {
                            JSONObject js = new JSONObject(result);
                            {
                                JSONArray obj = js.getJSONArray("products");
                                Product_List.clear();
                                Product_LIST_ID.clear();
                                for (int i = 0; i < obj.length(); i++) {
                                    Product_List.add("" + obj.getJSONObject(i).optString("product_name"));
                                    Product_LIST_ID.add("" + obj.getJSONObject(i).optString("product_id"));
                                }
                                Log.e("Size", "" + Product_List.size());
                                Log.e("result", js.toString() + "\n" + js.getJSONArray("products") + "\n"
                                        + obj.getJSONObject(0).optString("product_name"));
                            }
                            SelectProductsDialog();
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

    private void update() {
        final String getid = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);
        final String pro_id = SharedPref.getString(this, BaseURL.KEY_STOCK_PRODUCTS_ID);
        final String Qty = editText_Qty.getText().toString();
        final String unit = editText_Unit.getText().toString();
        if (NetworkConnection.connectionChecking(this)) {
            RequestQueue rq = Volley.newRequestQueue(this);
            StringRequest postReq = new StringRequest(Request.Method.POST, BaseURL.STOCK_UPDATE,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("eclipse", "Response=" + response);
                            try {
                                JSONObject object = new JSONObject(response);
                                JSONArray Jarray = object.getJSONArray("product");
                                for (int i = 0; i < Jarray.length(); i++) {
                                    JSONObject json_data = Jarray.getJSONObject(i);
                                    String msg = json_data.getString("msg");
                                    Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
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
                    params.put("product_id", pro_id);
                    params.put("qty", Qty);
                    params.put("unit", unit);
                    params.put("store_id_login", getid);

                    return params;
                }
            };
            rq.add(postReq);
        } else {
            Intent intent = new Intent(this, NetworkError.class);
            startActivity(intent);
        }
    }
}
