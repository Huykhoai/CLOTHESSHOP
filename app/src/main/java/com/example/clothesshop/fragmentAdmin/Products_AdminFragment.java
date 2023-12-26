package com.example.clothesshop.fragmentAdmin;



import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.clothesshop.adapteradmin.SpinnerAdapter;
import com.example.clothesshop.adapteradmin.ProductAdminAdapter;
import com.example.clothesshop.interFace.CategoryInterfer;
import com.example.clothesshop.model.Categories;
import com.example.clothesshop.model.Products;
import com.example.clothesshop.server.server;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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


public class Products_AdminFragment extends Fragment {
      RecyclerView recyclerView;
      ArrayList<Products>arrayList;
      ArrayList<Categories> categoriesArrayList;
      ProductAdminAdapter adapter;
      SpinnerAdapter spinnerAdapter;
      FloatingActionButton fab;
      TextInputLayout tilname,tilinventory,tilprice,tilimage,tildesc;
      TextInputEditText edname,edinventory,edprice,edimage,eddesc;
      ImageView image_choose,image_result;
      Spinner spinner;
      int a;
      Uri uri;
      int idcategory;
      TextView btnadd_product,btncancel_product;
      public static final int PICK_IMAGE_REQUEST=3;
      Dialog dialog;
    public Products_AdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products__admin, container, false);
        Anhxa(view);
        getData();
        getCategory();
        return view;
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdanproducts, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        int inventory = jsonObject.getInt("inventory");
                        int price = jsonObject.getInt("price");
                        String image = jsonObject.getString("image");
                        String desc = jsonObject.getString("description");
                        int idcategory = jsonObject.getInt("idcategory");
                        Products products = new Products(id,name,inventory,price,image,desc,idcategory);
                        arrayList.add(products);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void openDialog(){
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_product);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        edname = dialog.findViewById(R.id.edname_product_admin);
        edinventory = dialog.findViewById(R.id.edinventory_product_admin);
        edprice = dialog.findViewById(R.id.edprice_product_admin);
        edimage = dialog.findViewById(R.id.edimage_product_admin);
        eddesc = dialog.findViewById(R.id.eddesc_product_admin);
        btnadd_product= dialog.findViewById(R.id.add_products);
        btncancel_product = dialog.findViewById(R.id.cancel_products);
        image_choose = dialog.findViewById(R.id.choose_image_product);
        image_result = dialog.findViewById(R.id.image_result_product);
        image_choose.setOnClickListener(view1 -> openGrallery());
        spinner = dialog.findViewById(R.id.spinner_product);
        spinnerAdapter = new SpinnerAdapter(getActivity(), categoriesArrayList);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idcategory = categoriesArrayList.get(i).getId();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(a==-1){
            btnadd_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = edname.getText().toString();
                    String inventory = edinventory.getText().toString();
                    String price = edprice.getText().toString();
                    String image = edimage.getText().toString();
                    String desc = eddesc.getText().toString();
                    if(validate(name,inventory,price,image,desc)>0){
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancudproduct, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")){
                                    arrayList.clear();
                                    getData();
                                    Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else {
                                    Log.d("product", "onResponse: "+response);
                                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
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
                                hashMap.put("inventory", inventory);
                                hashMap.put("price", price);
                                hashMap.put("image", image);
                                hashMap.put("description", desc);
                                hashMap.put("idcategory", String.valueOf(idcategory));
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                }
            });
        }else{
            Products products = arrayList.get(a);
            edname.setText(products.getName());
            edinventory.setText(""+products.getInventory());
            edprice.setText(""+products.getPrice());
            String arrUrl = products.getImage();
            String[] urlImage  = arrUrl.split(",");
            edimage.setText(arrUrl);
            eddesc.setText(products.getDescription());
            Picasso.get().load(urlImage[0]).into(image_result);
            for(int i=0;i<spinner.getCount();i++){
                if(categoriesArrayList.get(i).getId()==products.getId_Category()){
                    spinner.setSelection(i);
                }
            }
            btnadd_product.setOnClickListener(view1 -> {
                String name = edname.getText().toString();
                String inventory = edinventory.getText().toString();
                String price = edprice.getText().toString();
                String image = edimage.getText().toString();
                String desc = eddesc.getText().toString();
                if(validate(name,inventory,price,image,desc)>0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdancudproduct, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("1")){
                                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                arrayList.clear();
                                getData();
                                dialog.dismiss();
                            }else {
                                Log.d("updateproduct", "onResponse: "+response);
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
                            hashMap.put("id", String.valueOf(products.getId()));
                            hashMap.put("name", name);
                            hashMap.put("inventory", inventory);
                            hashMap.put("price", price);
                            hashMap.put("image", image);
                            hashMap.put("description", desc);
                            hashMap.put("idcategory", String.valueOf(idcategory));
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            });
        }
        btncancel_product.setOnClickListener(view -> {dialog.dismiss();});
        dialog.show();
    }
    private void Anhxa(View view) {
        recyclerView = view.findViewById(R.id.recycle_product_admin);
        fab = view.findViewById(R.id.fab_product);
        arrayList = new ArrayList<>();
        categoriesArrayList = new ArrayList<>();
        adapter = new ProductAdminAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(view1 -> {
            a=-1;
            openDialog();
        });
        adapter.onClickProduct(new CategoryInterfer() {
            @Override
            public void onItem(int position) {
                a=position;
                openDialog();
            }
        });
    }
    private void getCategory() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdancategories, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        Categories categories = new Categories(id,name,image);
                        categoriesArrayList.add(categories);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private int validate(String name,String inventory,String price,String image,String desc){
        tilname = dialog.findViewById(R.id.til_name_product);
        tilinventory = dialog.findViewById(R.id.til_name_product);
        tilprice = dialog.findViewById(R.id.til_name_product);
        tilimage = dialog.findViewById(R.id.til_name_product);
        tildesc = dialog.findViewById(R.id.til_name_product);
        if(name.isEmpty()){
            tilname.setError("Chưa nhập name");
            return -1;
        }
        if(inventory.isEmpty()){
            tilinventory.setError("Chưa nhập inventory");
            return -1;
        }
        if(price.isEmpty()){
            tilinventory.setError("Chưa nhập price");
            return -1;
        }
        if(image.isEmpty()){
            tilinventory.setError("Imgae trống");
            return -1;
        }
        if(desc.isEmpty()){
            tilinventory.setError("Chưa nhập chi tiết");
            return -1;
        }
        return 1;
    }
    private void openGrallery(){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null){
            image_result = dialog.findViewById(R.id.image_result_product);
            edimage = dialog.findViewById(R.id.edimage_product_admin);
            uri = data.getData();
            String currentText = edimage.getText().toString();
            if (!currentText.isEmpty()) {
                currentText += ",";
            }

            currentText += uri.toString();
            edimage.setText(currentText);
            Picasso.get().load(uri).into(image_result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==PICK_IMAGE_REQUEST && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGrallery();
            }
        }
    }
}