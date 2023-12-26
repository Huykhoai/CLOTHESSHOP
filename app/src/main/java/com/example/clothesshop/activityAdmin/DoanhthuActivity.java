package com.example.clothesshop.activityAdmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.server.server;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DoanhthuActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnDoanhthu;
    ImageView btnTuNgay,btnDenNgay;
    EditText edTuNgay,edDenNgay;
    SimpleDateFormat spd =new SimpleDateFormat("yyyy-MM-dd");
    int mYear,mMonth,mDay;
    TextView tv;
    int temp =0;
    DatePickerDialog.OnDateSetListener dateDenNgay,dateTuNgay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthu);
        Anhxa();
        ButtonDoanhthu();
        Actionbar();
        dateTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear =i;
                mMonth =i1;
                mDay = i2;
                GregorianCalendar c =new GregorianCalendar(mYear,mMonth,mDay);
                edTuNgay.setText(spd.format(c.getTime()));
            }
        };
        dateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mYear =i;
                mMonth =i1;
                mDay = i2;
                GregorianCalendar c =new GregorianCalendar(mYear,mMonth,mDay);
                edDenNgay.setText(spd.format(c.getTime()));
            }
        };
    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void ButtonDoanhthu() {
       btnTuNgay.setOnClickListener(view -> {
           Calendar c =Calendar.getInstance();
           mYear = c.get(Calendar.YEAR);
           mMonth = c.get(Calendar.MONTH);
           mDay = c.get(Calendar.DAY_OF_MONTH);
           DatePickerDialog dpd = new DatePickerDialog(DoanhthuActivity.this, dateTuNgay, mYear, mMonth, mDay);
           dpd.show();
       });
        btnDenNgay.setOnClickListener(view -> {
            Calendar c =Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(DoanhthuActivity.this, dateDenNgay, mYear, mMonth, mDay);
            dpd.show();
        });
        btnDoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String tungay = edTuNgay.getText().toString();
              String denngay = edDenNgay.getText().toString();
              if(tungay.isEmpty() ||denngay.isEmpty()){
                  Toast.makeText(DoanhthuActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                  temp++;
              }else {
                 String[]temptungay = tungay.split("-");
                 String[] tempdenngay = denngay.split("-");
                 String newTungay = "";
                 String newDenngay = "";
                int intTungay = Integer.parseInt(newTungay.concat(temptungay[0]).concat(temptungay[1]).concat(temptungay[2]));
                int intDenngay = Integer.parseInt(newDenngay.concat(tempdenngay[0]).concat(tempdenngay[1]).concat(tempdenngay[2]));
                if(intDenngay< intTungay){
                    Toast.makeText(DoanhthuActivity.this, "Lỗi, từ ngày phải bé hơn đến ngày", Toast.LENGTH_SHORT).show();
                    temp++;
                }
              }
               if(temp==0){
                   RequestQueue requestQueue = Volley.newRequestQueue(DoanhthuActivity.this);
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdandoanhthu, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                       String trimmedResponse = response.trim().replace("\"", "");
                               tv.setText("Doanh thu: $"+trimmedResponse);
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
                           hashMap.put("tungay", tungay);
                           hashMap.put("denngay", denngay);
                           return hashMap;
                       }
                   };
                   requestQueue.add(stringRequest);
               }
            }
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_doanhthu);
        btnDoanhthu = findViewById(R.id.doanhthu_btn_tinh);
        btnTuNgay =findViewById(R.id.doanhthu_img_tungay);
        btnDenNgay = findViewById(R.id.doanhthu_img_denngay);
        edTuNgay = findViewById(R.id.doanhthu_edt_tungay);
        edDenNgay = findViewById(R.id.doanhthu_edt_denngay);
        tv = findViewById(R.id.doanhthu_tv);
    }
}