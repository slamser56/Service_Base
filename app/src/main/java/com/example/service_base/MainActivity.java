package com.example.service_base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.naw_view);
        navigationView.setNavigationItemSelectedListener(this);


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_find()).commit();
            navigationView.setCheckedItem(R.id.find);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
// Handle navigation view item clicks here.

        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId())
        {
            case R.id.find:
                Toast.makeText(getApplicationContext(),"Поиск",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_find()).commit();
                break;
            case R.id.news:
                Toast.makeText(getApplicationContext(),"Новости",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_news()).commit();
                break;
            case  R.id.scaner:
                Toast.makeText(getApplicationContext(),"Сканер",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.send_request:
                Toast.makeText(getApplicationContext(),"Написать заявку",Toast.LENGTH_SHORT).show();
                break;
            case  R.id.price:
                Toast.makeText(getApplicationContext(),"Цены",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_price()).commit();
                break;
            case R.id.about_us:
                Toast.makeText(getApplicationContext(),"О нас",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about_us()).commit();
                break;

            default:
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
