package com.example.clothesshop.fragmentAdmin;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
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
import com.example.clothesshop.adapteradmin.CategoryAdminAdapter;
import com.example.clothesshop.interFace.CategoryInterfer;
import com.example.clothesshop.model.Categories;
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


public class Category_AdminFragment extends Fragment {
    RecyclerView recyclerView;
    CategoryAdminAdapter adapter;
    ArrayList<Categories> arrayList;
    FloatingActionButton fab;
    public static final int PICK_IMAGE_REQUEST=2;
    Uri uri;
    BottomSheetDialog bottomSheetDialog;
    ImageView image_result,image_choose;
    TextInputEditText edimage,edname;
    TextInputLayout tilimage,tilname;
    TextView btnAdd,btnCancel;
    int a;
    public Category_AdminFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_category__admin, container, false);
        Anhxa(view);
        getData();
        return view;
    }

    private void getData() {
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
                      arrayList.add(categories);
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

    private void Anhxa(View view) {
        recyclerView = view.findViewById(R.id.recycle_category_admin);
        fab = view.findViewById(R.id.fab_category);
        arrayList = new ArrayList<>();
        adapter = new CategoryAdminAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        recyclerView.setAdapter(adapter);
        fab.setOnClickListener(view1 -> {
            a=-1;
            openDialog();
        });
        adapter.setOnclickCategory(new CategoryInterfer() {
            @Override
            public void onItem(int position) {
               a=position;
               openDialog();
            }
        });
    }

    private void openDialog() {
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomsetView = getLayoutInflater().inflate(R.layout.dialog_category_admin, null);
        bottomSheetDialog.setContentView(bottomsetView);

        ViewGroup.LayoutParams params =bottomsetView.getLayoutParams();
        params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        bottomsetView.setLayoutParams(params);
         edname = bottomSheetDialog.findViewById(R.id.edname_category_admin);
         edimage = bottomSheetDialog.findViewById(R.id.edimage_category_admin);
         image_choose = bottomSheetDialog.findViewById(R.id.choose_image_category);
         image_result = bottomSheetDialog.findViewById(R.id.image_result_category);
         btnAdd = bottomSheetDialog.findViewById(R.id.add_category);
         btnCancel = bottomSheetDialog.findViewById(R.id.cancel_category);
         image_choose.setOnClickListener(view -> {
            checkImage(edimage.getText().toString());
            });
           //add
          if(a==-1){

              btnAdd.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      String name = edname.getText().toString();
                      String image = edimage.getText().toString();
                      if(validate(name,image)>0){
                          RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                          StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdaninsertcategory, new Response.Listener<String>() {
                              @Override
                              public void onResponse(String response) {
                                  if(response.equals("1")){
                                      arrayList.clear();
                                      getData();
                                      adapter.notifyDataSetChanged();
                                      Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                      bottomSheetDialog.dismiss();
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
                                  hashMap.put("image", image.isEmpty()? uri.toString() : image);
                                  return hashMap;
                              }
                          };
                          requestQueue.add(stringRequest);
                      }
                  }
                  });
          }else {
              Categories categories = arrayList.get(a);
              edname.setText(categories.getName());
              Picasso.get().load(categories.getImage()).into(image_result);
              edimage.setText(categories.getImage());
              btnAdd.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      String name = edname.getText().toString();
                      String image = edimage.getText().toString();
                      if(validate(name,image)>0){
                          RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                          StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdaninsertcategory, new Response.Listener<String>() {
                              @Override
                              public void onResponse(String response) {
                                  Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                                  edname.setText("");
                                  edimage.setText("");
                                  arrayList.clear();
                                  getData();
                                  bottomSheetDialog.dismiss();
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
                                  hashMap.put("id", String.valueOf(categories.getId()));
                                  hashMap.put("nameupdate", name);
                                  hashMap.put("imageupdate",image);
                                  return hashMap;
                              }
                          };
                          requestQueue.add(stringRequest);
                      }
                      }
              });

          }
         btnCancel.setOnClickListener(view -> bottomSheetDialog.dismiss());
        bottomSheetDialog.show();
    }
    private void openGallery(){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode ==RESULT_OK && data!= null){
            uri = data.getData();
             image_result = bottomSheetDialog.findViewById(R.id.image_result_category);
             edimage= bottomSheetDialog.findViewById(R.id.edimage_category_admin);
             edimage.setText(uri.toString());
             Picasso.get().load(uri).into(image_result);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PICK_IMAGE_REQUEST && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //đã cấp quyền
                openGallery();
            }
        }
    }
    private void checkImage(String strImage){
        if(strImage.isEmpty()){
            openGallery();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Cảnh báo!");
            builder.setMessage("Không thể chọn ảnh từ thư viện khi đã điền link ảnh");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
   private int validate(String name,String image){
       tilname = bottomSheetDialog.findViewById(R.id.til_name_category);
       tilimage = bottomSheetDialog.findViewById(R.id.tilimage_category);
       if(name.isEmpty()){
           tilname.setError("Chưa nhập tên");
           return -1;
       }
       if(image.isEmpty()){
           tilimage.setError("Không được để trống");
           return -1;
       }
       return 1;
   }
}