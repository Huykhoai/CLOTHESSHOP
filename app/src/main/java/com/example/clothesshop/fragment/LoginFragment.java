package com.example.clothesshop.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.activity.LoginActivity;
import com.example.clothesshop.activity.MainActivity_user;
import com.example.clothesshop.activityAdmin.MainActivity;
import com.example.clothesshop.model.Users;
import com.example.clothesshop.server.server;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {
    EditText edSodienthoai, edPass;
    TextInputLayout tilEmail, tilPass;
    Button btnLogin;
    CheckBox checkBox;
    int temp=0;
    ImageView fabFacebook,fabGoogle,fabTwiter;
    public static ArrayList<Users> arrayList;
    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        Anhxa(view);
        onclickButton();
        animation();
        return view;
    }
    private void onclickButton() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sodienthoai = edSodienthoai.getText().toString().trim();
                String pass = edPass.getText().toString().trim();

                ProgressDialog progressDialog =new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                if (validateLogin(sodienthoai, pass) > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanloginusers, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                int loginStatus = jsonObject.getInt("login_status");
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String pass = jsonObject.getString("hashed_pass");
                                int phone = jsonObject.getInt("phone");
                                String address = jsonObject.getString("address");
                                int role = jsonObject.getInt("role");
                                String avatar = jsonObject.getString("avatar");
                                arrayList.add(new Users(id,name,pass,phone,address,role,avatar));
                                if (loginStatus==1) {
                                    Toast.makeText(getActivity(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    if(role==0){
                                        startActivity(new Intent(getActivity(), MainActivity_user.class));
                                        rememberUser(sodienthoai, pass, checkBox.isChecked());
                                        progressDialog.dismiss();
                                    }else {
                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                    }

                                }else if(response.equals("0")){
                                    Toast.makeText(getActivity(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }else {
                                    Toast.makeText(getActivity(), " Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> params = new HashMap<>();
                            params.put("sodienthoai", edSodienthoai.getText().toString());
                            params.put("password", edPass.getText().toString());
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void Anhxa(View view) {
        edSodienthoai = view.findViewById(R.id.login_edSodienthoai);
        edPass = view.findViewById(R.id.login_edpassword);
        btnLogin = view.findViewById(R.id.login_btnlogin);
        checkBox = view.findViewById(R.id.login_checkBox);
        tilEmail = view.findViewById(R.id.login_tilSodienthoai);
        tilPass = view.findViewById(R.id.login_tilpassword);
        arrayList = new ArrayList<>();

        fabFacebook = view.findViewById(R.id.fabFacebook);
        fabGoogle = view.findViewById(R.id.fabGoogle);
        fabTwiter = view.findViewById(R.id.fabTwiter);

        SharedPreferences spf = getActivity().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        edSodienthoai.setText(spf.getString("PHONE", ""));
        edPass.setText(spf.getString("PASSWORD", ""));
        checkBox.setChecked(spf.getBoolean("REMEMBER", false));
    }
    private int validateLogin(String sodienthoai,String pass){
        int a=1;
        String regex = "0[0-9]{9}";
        if (sodienthoai.length()==0) {
            tilEmail.setError("Chưa nhập Số điện thoại");
            a=-1;
        } else {
            tilEmail.setError("");
            a++;
            if (!Patterns.PHONE.matcher(sodienthoai).matches()&& sodienthoai.length()==10) {
                tilEmail.setError("Số điện thoại không hợp lệ");
                a=-1;
            } else {
                tilPass.setError("");
                a++;
            }
        }
        if(pass.length()==0){
            tilPass.setError("Chưa nhập pass");
            a=-1;
        }else {
            tilPass.setError("");
            a++;
        }
        return  a;
    }
    private void rememberUser(String phone,String pass, boolean status){
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(!status){
            editor.clear();
        }else {
            editor.putString("PHONE", phone);
            editor.putString("PASSWORD" , pass);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }
    private void animation() {
        fabFacebook.setTranslationY(500);
        fabGoogle.setTranslationY(500);
        fabTwiter.setTranslationY(500);
        float alpha = 0;
        fabFacebook.setAlpha(alpha);
        fabGoogle.setAlpha(alpha);
        fabTwiter.setAlpha(alpha);

        fabFacebook.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(1000).start();
        fabGoogle.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(1200).start();
        fabTwiter.animate().translationY(0).alpha(1).setDuration(1500).setStartDelay(1400).start();
    }
}