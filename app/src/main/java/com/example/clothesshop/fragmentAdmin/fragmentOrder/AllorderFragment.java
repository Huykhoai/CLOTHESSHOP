package com.example.clothesshop.fragmentAdmin.fragmentOrder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.activityAdmin.InfomationAdminActivity;
import com.example.clothesshop.adapter.OrderAdapter;
import com.example.clothesshop.adapteradmin.OrderAdminAdapter;
import com.example.clothesshop.fragment.LoginFragment;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;
import com.example.clothesshop.model.Users;
import com.example.clothesshop.server.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AllorderFragment extends Fragment {
    String idOrder,nameorder,addressorder,messagesorder,createAt,createCancel,dateConfirm,dateDelivery,dateSuccess;
    String cartname, cartimage, cartsize, cartcolor;
    int cartid, cartquality, cartprice;
    int phoneorder,status, total;
    public static ArrayList<Cart> cartArrayList;
    public static ArrayList<Order> arrayList;
    ArrayList<Users> listUser ;
    OrderAdminAdapter adapter;
    ListView listView;

    int phoneuser,role;
    SwipeRefreshLayout swipeRefreshLayout;
    public AllorderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allorder, container, false);
        Anhxa(view);
        getData();
        getDataUser();
        return view;
    }

    private void getDataUser() {
       phoneuser = LoginFragment.arrayList.get(0).getPhone();
       role = LoginFragment.arrayList.get(0).getRole();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanorders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("-1")){
                    Toast.makeText(getActivity(), "Trống", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        Log.d("order", "onResponse: "+response.toString());
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i=0;i< jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            idOrder = jsonObject.getString("idorder");
                            nameorder = jsonObject.getString("name");
                            phoneorder = jsonObject.getInt("phone");
                            addressorder = jsonObject.getString("address");
                            cartArrayList = new ArrayList<>();
                            JSONArray cartArray = new JSONArray(jsonObject.getString("cart"));
                            for(int j=0;j<cartArray.length();j++){
                                JSONObject cartObject = cartArray.getJSONObject(j);
                                cartid = cartObject.getInt("cartid");
                                cartname = cartObject.getString("cartname");
                                cartimage = cartObject.getString("cartimage");
                                cartsize = cartObject.getString("cartsize");
                                cartcolor = cartObject.getString("cartcolor");
                                cartquality = cartObject.getInt("cartquality");
                                cartprice = cartObject.getInt("cartprice");
                                cartArrayList.add(new Cart(cartid, cartimage,cartname, cartsize, cartcolor, cartquality, cartprice));
                            }
                            status = jsonObject.getInt("status");
                            total = jsonObject.getInt("total");
                            createAt = jsonObject.getString("createAt");
                            createCancel = jsonObject.getString("createCancel");
                            dateDelivery = jsonObject.getString("dateDelivery");
                            dateConfirm = jsonObject.getString("dateConfirm");
                            dateSuccess = jsonObject.getString("dateSuccess");
                            arrayList.add(new Order(idOrder, nameorder, phoneorder,addressorder, cartArrayList
                                    , "", status,total, createAt,createCancel,dateConfirm,dateDelivery,dateSuccess));                            adapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("json", "");
                params.put("status", String.valueOf(0));
                params.put("role", String.valueOf(role));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa(View view) {
        listView = view.findViewById(R.id.listview_orderadmin);
        swipeRefreshLayout = view.findViewById(R.id.swipe_allorderadmin);
        arrayList = new ArrayList<>();
        listUser = new ArrayList<>();
        adapter = new OrderAdminAdapter(getActivity(), arrayList);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InfomationAdminActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order_detail",arrayList.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}