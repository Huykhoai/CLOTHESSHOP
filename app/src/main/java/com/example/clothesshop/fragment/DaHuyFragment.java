package com.example.clothesshop.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.clothesshop.adapter.OrderAdapter;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Order;
import com.example.clothesshop.server.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DaHuyFragment extends Fragment {
    String idOrder,nameorder,addressorder,messagesorder,createAt;
    String cartname, cartimage, cartsize, cartcolor;
    int cartid, cartquality, cartprice;
    int phoneorder,status, total;
    ArrayList<Cart> cartArrayList;
    ArrayList<Order> arrayList;
    OrderAdapter adapter;
    ListView listView;

    int phoneuser;
    SwipeRefreshLayout swipeRefreshLayout;
    public DaHuyFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_da_huy, container, false);
        Anhxa(view);
        getDataUser();
        getData();
        return view;
    }
    private void getDataUser() {
        for(int i=0;i<LoginFragment.arrayList.size();i++){
            phoneuser = LoginFragment.arrayList.get(i).getPhone();
        }
    }

    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanorders, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("-1")){
                    Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
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
                            arrayList.add(new Order(idOrder, nameorder, phoneorder,addressorder, cartArrayList, "", status,total, createAt));
                            adapter.notifyDataSetChanged();
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
                params.put("phone", String.valueOf(phoneuser));
                params.put("status", String.valueOf(2));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Anhxa(View view) {
        listView = view.findViewById(R.id.listviewCancelOrder);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        arrayList = new ArrayList<>();
        adapter = new OrderAdapter(getActivity(), arrayList);
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
    }

}