    package com.example.clothesshop.activity;

    import android.Manifest;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.View;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;
    import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.StringRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.clothesshop.R;
    import com.example.clothesshop.fragment.LoginFragment;
    import com.example.clothesshop.server.server;
    import com.google.android.material.textfield.TextInputEditText;
    import com.google.android.material.textfield.TextInputLayout;
    import com.squareup.picasso.Picasso;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.HashMap;
    import java.util.Map;

    public class AcountActivity extends AppCompatActivity {
        Toolbar toolbar;
         TextView txtname_account,txtphone_account,txttotal_account,btnupdate,btnlogout;
         ImageView imageView,imageupdateImgae;
         TextInputLayout tilname, tilpassword,tiladdress;
         TextInputEditText edname,edpassword,edaddress;
         int id;
        String pathImage;
        String img_user,name,phone;
         private static final int PICK_IMAGE_REQUEST =1;
        private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 101;

        SwipeRefreshLayout swipe;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_acount);
            Anhxa();
            getImageUpdate();
            getUser();
            UpdateUser();
            ActionBar();

        }

        private void UpdateUser() {

            btnupdate.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String nameupdate = edname.getText().toString();
                  String passwordupdate = edpassword.getText().toString();
                  String addressupdate = edaddress.getText().toString();
                  RequestQueue requestQueue = Volley.newRequestQueue(AcountActivity.this);
                  StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanupdateusers, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          Log.d("update", "onResponse: "+response);
                          if(response.equals("1")){
                              Toast.makeText(AcountActivity.this, "Update thất bại", Toast.LENGTH_SHORT).show();
                          }else if(response.equals("0")){
                              Toast.makeText(AcountActivity.this, "không có dữ liệu cập nhập", Toast.LENGTH_SHORT).show();
                          }else {
                              try {
                                  JSONObject jsonObject = new JSONObject(response);
                                  String name = jsonObject.getString("name");
                                  String avatar = jsonObject.getString("avatar");
                                  txtname_account.setText(name);
                                  Picasso.get().load(avatar).into(imageView);
                                  Toast.makeText(AcountActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
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
                          params.put("id", String.valueOf(id));
                          params.put("name", nameupdate);
                          params.put("pass", passwordupdate);
                          params.put("address", addressupdate);
                          params.put("image", pathImage);
                          return params;
                      }
                  };
              requestQueue.add(stringRequest);
              }
          });
            btnlogout.setOnClickListener(view -> {startActivity(new Intent(AcountActivity.this, LoginActivity.class));});
        }

        private void getUser() {
            id = LoginFragment.arrayList.get(0).getId();
             name = LoginFragment.arrayList.get(0).getName();
             phone = String.valueOf(LoginFragment.arrayList.get(0).getPhone());
             img_user = LoginFragment.arrayList.get(0).getAvatar();

            txtname_account.setText(""+ name);
            txtphone_account.setText("+84"+phone);
            Picasso.get().load(img_user).into(imageView);
        }

        private void Anhxa() {
            toolbar = findViewById(R.id.toolbar_account);
            imageupdateImgae = findViewById(R.id.image_update_account);
            txtname_account = findViewById(R.id.txtname_account);
            txtphone_account = findViewById(R.id.txtphone_account);
            txttotal_account = findViewById(R.id.txttotal_account);
            btnupdate = findViewById(R.id.btnupdate_account);
            btnlogout = findViewById(R.id.btnlogout_account);
            imageView = findViewById(R.id.image_account);
            tilname = findViewById(R.id.tilname_account);
            tilpassword = findViewById(R.id.tilpass_account);
            tiladdress = findViewById(R.id.tiladdress_account);
            edname = findViewById(R.id.edname_account);
            edpassword = findViewById(R.id.edpass_account);
            edaddress = findViewById(R.id.edaddress_account);
            swipe = findViewById(R.id.swipe_account);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getUser();
                    swipe.setRefreshing(false);
                }
            });
        }
        private void ActionBar(){
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
        private void getImageUpdate(){
            imageupdateImgae.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     openGrallery();
                }
            });
        }
    private void openGrallery(){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,PICK_IMAGE_REQUEST);
        }

    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data != null ){
               Uri UriImage = data.getData();
               pathImage = UriImage.toString();
                Log.d("pathImage", "onActivityResult: "+UriImage);

            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
              if(requestCode==PICK_IMAGE_REQUEST && grantResults.length>0){
                       if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                           //đã được cấp quyền
                           openGrallery();
                       }
              }
        }
    }