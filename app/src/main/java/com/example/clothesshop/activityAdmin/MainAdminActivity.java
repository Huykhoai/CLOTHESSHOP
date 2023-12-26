package com.example.clothesshop.activityAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clothesshop.R;
import com.example.clothesshop.activity.LoginActivity;
import com.example.clothesshop.fragmentAdmin.Category_AdminFragment;
import com.example.clothesshop.fragmentAdmin.Customer_AdminFragment;
import com.example.clothesshop.fragmentAdmin.HomeAdminFragment;
import com.example.clothesshop.fragmentAdmin.Order_AdminFragment;
import com.example.clothesshop.fragmentAdmin.Products_AdminFragment;
import com.google.android.material.navigation.NavigationView;

public class MainAdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
     Toolbar toolbar;
     DrawerLayout drawerLayout;
     NavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Anhxa();
        ActionBAr();
    }

    private void ActionBAr() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });
    }

    private void Anhxa() {
        toolbar = findViewById(R.id.toolbar_main_admin);
        drawerLayout = findViewById(R.id.drawer);
        nav = findViewById(R.id.nav);
        replaceFragment(new HomeAdminFragment());
        nav.getMenu().findItem(R.id.nav_home).setChecked(true);
        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_home){
           replaceFragment(new HomeAdminFragment());
        }
        if(item.getItemId()==R.id.nav_category){
            replaceFragment(new Category_AdminFragment());
        }
        if(item.getItemId()==R.id.nav_product){
            replaceFragment(new Products_AdminFragment());
        }
        if(item.getItemId()==R.id.nav_customer){
            replaceFragment(new Customer_AdminFragment());
        }
        if(item.getItemId()==R.id.nav_order){
            replaceFragment(new Order_AdminFragment());
        }
        if(item.getItemId()==R.id.nav_statistic){
            startActivity(new Intent(MainAdminActivity.this, DoanhthuActivity.class));
        }
        if(item.getItemId()==R.id.nav_logout){
            startActivity(new Intent(MainAdminActivity.this, LoginActivity.class));
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.framelayout_main_admin, fragment).commit();
   }
}