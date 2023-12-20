package com.example.clothesshop.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.clothesshop.server.server;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {
    EditText edRegisterName,edRegisterPass,edRegisterSdt;
    TextInputLayout tilRegisterName,tilRegisterPass,tilRegisterSdt;
    Button btnRegister;
    ImageView fabFacebook,fabGoogle,fabTwiter;

    int temp=0;
    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        Anhxa(view);
        onClickButton();
        animation();
        return view;
    }
    private void onClickButton() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sodienthoai = edRegisterSdt.getText().toString();
                String name = edRegisterName.getText().toString();
                String pass = edRegisterPass.getText().toString();
                ProgressDialog progressDialog =new ProgressDialog(getActivity());
                validate(name, pass,sodienthoai);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                if(temp==0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,server.duongdanusers, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                                if(response.equals("1")){
                                    Toast.makeText(getActivity(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    progressDialog.dismiss();
                                }else if(response.equals("0")){
                                    Toast.makeText(getActivity(), "Tên tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "Đăng kí thất bại!", Toast.LENGTH_SHORT).show();
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
                            HashMap<String,String> param = new HashMap<>();
                            param.put("number", sodienthoai);
                            param.put("username", name);
                            param.put("pass", pass);
                            return param;
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
        tilRegisterSdt= view.findViewById(R.id.register_tilPhone);
        tilRegisterName= view.findViewById(R.id.register_tilusername);
        tilRegisterPass= view.findViewById(R.id.register_tilpassword);

        edRegisterSdt = view.findViewById(R.id.register_edPhone);
        edRegisterName = view.findViewById(R.id.register_edusername);
        edRegisterPass = view.findViewById(R.id.register_edpassword);

        fabFacebook = view.findViewById(R.id.fabFacebook_register);
        fabGoogle = view.findViewById(R.id.fabGoogle_register);
        fabTwiter = view.findViewById(R.id.fabTwiter_register);

        btnRegister = view.findViewById(R.id.register_btnregister);

    }
    private void validate(String name,String pass,String sodienthoai){
        String regex = "0[0-9]{9}";
        if(name.isEmpty()){
            tilRegisterName.setError("Chưa nhập tên");
            temp++;
        }else {
            tilRegisterName.setError("");
            temp=0;
        }
        if(pass.isEmpty()){
            tilRegisterPass.setError("Chưa nhập tên");
            temp++;
        }else {
            tilRegisterPass.setError("");
            temp=0;
        }
        if(sodienthoai.isEmpty()){
            tilRegisterSdt.setError("Chưa nhập Số điện thoại");
            temp++;
        }else {
            tilRegisterSdt.setError("");
            temp=0;
            if (!sodienthoai.matches(regex)) {
                tilRegisterSdt.setError("Số điện thoại không hợp lệ");
                temp++;
            } else {
                tilRegisterSdt.setError("");
                temp=0;
            }
        }
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