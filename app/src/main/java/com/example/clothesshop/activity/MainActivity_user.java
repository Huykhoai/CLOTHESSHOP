package com.example.clothesshop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clothesshop.R;
import com.example.clothesshop.adapter.CategoryAdapter;
import com.example.clothesshop.adapter.PhotoAdapter;
import com.example.clothesshop.adapter.ProductAdapter;
import com.example.clothesshop.interFace.RecycleInterface;
import com.example.clothesshop.model.Categories;
import com.example.clothesshop.model.Cart;
import com.example.clothesshop.model.Photo;
import com.example.clothesshop.model.Products;
import com.example.clothesshop.server.server;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity_user extends AppCompatActivity {
    public static ArrayList<Cart> userCart;
     private ViewPager2 viewPager2;
    private List<Photo> photoList;
    private ArrayList<Categories> categoriesList;
    private ArrayList<Products> productsList;
    private ArrayList<Products> OldproductsList;
    private ProductAdapter productAdapter;
    private PhotoAdapter photoAdapter;
    private CategoryAdapter categoryAdapter;
    private CircleIndicator3 circleIndicator3;
    private RecyclerView recycler_category,recycler_product;
    private TextView tilte_main_user;
    private BottomNavigationView bottomNavigationView;
    private int idCategory;
    private  Handler handler = new Handler(Looper.myLooper());
    private Runnable runnable = new Runnable() {
           @Override
           public void run() {
               int currentposition = viewPager2.getCurrentItem();
                if(currentposition == photoList.size()-1){
                    viewPager2.setCurrentItem(0);
                }else {
                    viewPager2.setCurrentItem(currentposition+1);
                }
           }
       };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        AnhXa();
        startTiltle();
        ViewPaper();
        getCategories();
        getProduct();
        onClickCategory();
        onClickProduct();
    }

    private void onClickProduct() {
        productAdapter.setOnlickProduct(new RecycleInterface() {
            @Override
            public void onClickItemRecycleView(int position) {
                Products products = productsList.get(position);
                Intent intent = new Intent(MainActivity_user.this,DetailProductActivity.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailproduct", products);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void onClickCategory() {
        categoryAdapter.OnClickItem(new RecycleInterface() {
            @Override
            public void onClickItemRecycleView(int position) {
                idCategory = categoriesList.get(position).getId();
                productsList.clear();
               for (Products products : OldproductsList){
                   if(products.getId_Category()==idCategory){
                      productsList.add(products);
                   }
               }
               productAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getCategories() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_user.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(server.duongdancategories, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String image = jsonObject.getString("image");
                        Categories categories = new Categories(id,name,image);
                        categoriesList.add(categories);
                        categoryAdapter.notifyDataSetChanged();
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
    private void getProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity_user.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, server.duongdanproducts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("json", "onResponse: "+response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            int inventory = jsonObject.getInt("inventory");
                            int price = jsonObject.getInt("price");
                            String image = jsonObject.getString("image");
                            String desc = jsonObject.getString("description");
                            int idcategory= jsonObject.getInt("idcategory");
                            Products products = new Products(id,name,inventory,price,image,desc,idcategory);
                            productsList.add(products);
                            OldproductsList.add(products);
                            productAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

         requestQueue.add(stringRequest);
    }

    private void ViewPaper() {
        photoList = mangquangcao();
        photoAdapter = new PhotoAdapter(MainActivity_user.this, photoList);
        viewPager2.setAdapter(photoAdapter);
        circleIndicator3.setViewPager(viewPager2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable,3000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable,3000);
    }

    private void AnhXa() {
        viewPager2 = findViewById(R.id.viewpaper);
        circleIndicator3 = findViewById(R.id.circleIndicator);
        recycler_category = findViewById(R.id.recycle_category);
        recycler_product = findViewById(R.id.recycle_product);
        tilte_main_user = findViewById(R.id.title_main_user);
        bottomNavigationView = findViewById(R.id.bottm_nav);
        if(userCart == null){
            userCart = new ArrayList<>();
        }
        categoriesList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(MainActivity_user.this, categoriesList);
        recycler_category.setLayoutManager(new LinearLayoutManager(MainActivity_user.this, LinearLayoutManager.HORIZONTAL, false));

        recycler_category.setAdapter(categoryAdapter);

        productsList = new ArrayList<>();
        OldproductsList = new ArrayList<>();
        productAdapter = new ProductAdapter(MainActivity_user.this, productsList);
        recycler_product.setLayoutManager(new GridLayoutManager(MainActivity_user.this,2));
        recycler_product.setAdapter(productAdapter);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_home){startActivity(new Intent(MainActivity_user.this, MainActivity_user.class));}
                if(item.getItemId()==R.id.nav_order){startActivity(new Intent(MainActivity_user.this, OrderActivity.class));}
                if(item.getItemId()==R.id.nav_cart){startActivity(new Intent(MainActivity_user.this, CartActivity.class));}
                if(item.getItemId()==R.id.nav_account){startActivity(new Intent(MainActivity_user.this,AcountActivity.class));}
            }
        });
    }
    private List<Photo> mangquangcao(){
         List<Photo> list = new ArrayList<>();
         list.add(new Photo(R.drawable.mangquangcao1));
         list.add(new Photo(R.drawable.mangquangcao2));
         list.add(new Photo(R.drawable.mangquangcao3));
         list.add(new Photo(R.drawable.mangquangcao4));
         list.add(new Photo(R.drawable.mangquangcao5));
         return list;
    }
    private void startTiltle(){
        int speed =10;
        int postDelay = 100;
         Handler handler1 = new Handler();
         handler1.postDelayed(new Runnable() {
             @Override
             public void run() {
                 tilte_main_user.setTranslationX(tilte_main_user.getTranslationX()-speed);
                 if(tilte_main_user.getX() + tilte_main_user.getWidth()<0) {
                     tilte_main_user.setTranslationX(tilte_main_user.getWidth());
                 }
                 handler1.postDelayed(this, postDelay);
             }
         },postDelay);
    }
}