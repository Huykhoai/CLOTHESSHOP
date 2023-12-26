package com.example.clothesshop.fragmentAdmin;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.adapteradmin.CustomerAdminAdapter;
import com.example.clothesshop.interFace.CategoryInterfer;
import com.example.clothesshop.model.Users;
import com.example.clothesshop.server.server;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Customer_AdminFragment extends Fragment {
    RecyclerView recyclerView;
    CustomerAdminAdapter adapter;
    ArrayList<Users> arrayList;
    FloatingActionButton fab;
    int a,introle;
    ImageView image_choose,image_result;
    TextInputEditText edname, edpass,edphone,edaddress, edrole,edavatar;
    TextInputLayout tilname,tilpass,tilphone,tiladdress,tilrole,tilavatar;
    RadioButton rdoadmin ,rdocustomer;
    TextView btnadd,btnCancel;
    Dialog dialog;
    public Customer_AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer__admin, container, false);
        Anhxa(view);
        getData();
        return view;
    }

    private void getData() {
       RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
       StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancruduser, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray jsonArray = new JSONArray(response);
                   for(int j=0;j<jsonArray.length();j++){
                       JSONObject jsonObject = jsonArray.getJSONObject(j);
                       int id = jsonObject.getInt("id");
                       String name = jsonObject.getString("name");
                       int phone = jsonObject.getInt("phone");
                       String pass = jsonObject.getString("pass");
                       String address= jsonObject.getString("address");
                       int role = jsonObject.getInt("role");
                       String image = jsonObject.getString("avatar");
                       arrayList.add(new Users(id,name,pass,phone,address,role,image));
                       adapter.notifyDataSetChanged();
                   }
               } catch (JSONException e) {
                   throw new RuntimeException(e);
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
               HashMap<String,String> hashMap = new HashMap<>();
               hashMap.put("status", String.valueOf(0));
               return hashMap;
           }
       };
       requestQueue.add(stringRequest);
    }

    private void Anhxa(View view) {
        recyclerView = view.findViewById(R.id.recycle_user_admin);
        fab = view.findViewById(R.id.fab_user);
        arrayList = new ArrayList<>();
        adapter = new CustomerAdminAdapter(getActivity(), arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(view1 -> {
             a = -1;
            openDialog();
        });
        adapter.setOnclickUser(new CategoryInterfer() {
            @Override
            public void onItem(int position) {
                a=position;
                openDialog();
            }
        });
    }

    private void openDialog() {
         dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_user_admin);
        Window window = dialog.getWindow();
        if(window== null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        edname = dialog.findViewById(R.id.edname_user_admin);
        edpass = dialog.findViewById(R.id.edpass_user_admin);
        edphone = dialog.findViewById(R.id.edphone_user_admin);
        edaddress = dialog.findViewById(R.id.edaddress_user_admin);
        edavatar = dialog.findViewById(R.id.edimage_user_admin);

        btnadd = dialog.findViewById(R.id.add_user);
        btnCancel = dialog.findViewById(R.id.cancel_user);
        image_choose = dialog.findViewById(R.id.choose_image_user);
        image_result = dialog.findViewById(R.id.image_result_user);
        rdoadmin = dialog.findViewById(R.id.rdoadmin);
        rdocustomer = dialog.findViewById(R.id.rdocustomer);



        if(a==-1){
            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   String name = edname.getText().toString();
                   String pass = edpass.getText().toString();
                   String phone = edphone.getText().toString();
                   String address = edaddress.getText().toString();
                   String image = edavatar.getText().toString();
                   introle = rdoadmin.isChecked() ? 1 : rdocustomer.isChecked() ? 0 : -1;
                   if(validate(name,pass,phone,address,image, introle)>0){
                         RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                         StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancruduser, new Response.Listener<String>() {
                             @Override
                             public void onResponse(String response) {
                              if(response.equals("1")){
                                  Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                  arrayList.clear();
                                  getData();
                                  dialog.dismiss();
                              }else {
                                  Log.d("user", "onResponse: "+response);
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
                                 HashMap<String,String> hashMap = new HashMap<>();
                                 hashMap.put("status", String.valueOf(1));
                                 hashMap.put("name", name);
                                 hashMap.put("pass", pass);
                                 hashMap.put("phone", phone);
                                 hashMap.put("address", address);
                                 hashMap.put("role", String.valueOf(introle));
                                 hashMap.put("avatar", image);
                                 return hashMap;
                             }
                         };
                         requestQueue.add(stringRequest);
                   }

                }
            });
        }else {
            btnadd.setText("UPDATE");
            Users users = arrayList.get(a);
            edname.setText(users.getName());
            edpass.setText(users.getPass());
            edphone.setText("0"+users.getPhone());
            edaddress.setText(users.getAddress());
            Picasso.get().load(users.getAvatar()).into(image_result);
            edavatar.setText(users.getAvatar());
            if(users.getRole()==1){
                rdoadmin.setChecked(true);
            }else {
                rdocustomer.setChecked(true);
            }

            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = edname.getText().toString();
                    String pass = edpass.getText().toString();
                    String phone = edphone.getText().toString();
                    String address = edaddress.getText().toString();
                    String image = edavatar.getText().toString();
                    introle = rdoadmin.isChecked() ? 1 : rdocustomer.isChecked() ? 0 : -1;
                    if(validate(name,pass,phone,address,image, introle)>0){
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancruduser, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                    arrayList.clear();
                                    getData();
                                    dialog.dismiss();
                                }else {
                                    Log.d("user", "onResponse: "+response);
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
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("status", String.valueOf(3));
                                hashMap.put("name", name);
                                hashMap.put("pass", pass);
                                hashMap.put("phone", phone);
                                hashMap.put("address", address);
                                hashMap.put("role", String.valueOf(introle));
                                hashMap.put("avatar", image);
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            });

        }
        btnCancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
    private int validate(String name,String pass,String phone, String address,String image, int role){
        String regex = "0[0-9]{9}";
        tilname = dialog.findViewById(R.id.til_name_user);
        tilpass = dialog.findViewById(R.id.til_pass_user);
        tilphone = dialog.findViewById(R.id.til_phone_user);
        tiladdress= dialog.findViewById(R.id.til_address_user);
        tilavatar = dialog.findViewById(R.id.tilimage_user);
        if(name.isEmpty()){
            tilname.setError("Chưa nhập tên");
            return -1;
        }else {
            tilname.setError("");
        }
        if(phone.isEmpty()){
            tilphone.setError("Chưa nhập số điện thoai");
            return -1;
        }else {
            tilphone.setError("");
            if(!phone.matches(regex)){
                tilphone.setError("Số điện thoại không hợp lệ");
                return -1;
            }else {
                tilphone.setError("");
            }
        }
        if(pass.isEmpty()){
            tilpass.setError("Chưa nhập mật khẩu");
            return -1;
        }else {
            tilpass.setError("");
        }
        if(address.isEmpty()){
            tiladdress.setError("chưa nhập địa chỉ");
            return -1;
        }else {
            tiladdress.setError("");
        }
        if(image.isEmpty()){
            tilavatar.setError("chưa nhập ảnh");
            return -1;
        }else {
            tilavatar.setError("");
        }
        if(role==-1){
            Toast.makeText(getActivity(), "Chưa chọn quyền", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return 1;
    }
}