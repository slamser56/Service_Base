package com.example.service_base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.service_base.Repair_item.Auth;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    MenuItem new_repair;
    MenuItem logout;
    MenuItem new_parts;
    MenuItem login;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences auth = getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean hasAutorized = auth.getBoolean(Auth.APP_PREFERENCES_AUTORIZED, false);

        if (!hasAutorized){
            new_repair.setVisible(false);
            logout.setVisible(false);
            new_parts.setVisible(false);
            login.setVisible(true);
        }
        else{
            login.setVisible(false);
            new_repair.setVisible(true);
            logout.setVisible(true);
            new_parts.setVisible(true);
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.naw_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();




        new_repair = menu.findItem(R.id.new_repair);
        logout= menu.findItem(R.id.logout);
        new_parts = menu.findItem(R.id.new_parts);
        login = menu.findItem(R.id.login);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();




        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_find()).commit();
            navigationView.setCheckedItem(R.id.find);
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
// Handle navigation view item clicks here.

        Fragment fragment = null;
        Class fragmentClass;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.find:
                Toast.makeText(getApplicationContext(), "Поиск", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_find()).commit();
                break;
            case R.id.news:
                Toast.makeText(getApplicationContext(), "Новости", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_news()).commit();
                break;
            case R.id.scaner:
                Toast.makeText(getApplicationContext(), "Сканер", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_request:
                Toast.makeText(getApplicationContext(), "Написать заявку", Toast.LENGTH_SHORT).show();
                break;
            case R.id.price:
                Toast.makeText(getApplicationContext(), "Цены", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_price()).commit();
                break;
            case R.id.about_us:
                Toast.makeText(getApplicationContext(), "О нас", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about_us()).commit();
                break;
            case R.id.new_repair:
                intent = new Intent(this, new_repair.class);
                this.startActivity(intent);
                break;
            case R.id.new_parts:
                intent = new Intent(this, New_parts.class);
                this.startActivity(intent);
                break;
            case R.id.login:
                intent = new Intent(this, login.class);
                this.startActivity(intent);
                break;
            case R.id.logout:
                SharedPreferences auth = getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = auth.edit();
                editor.clear().apply();
                intent = getIntent();
                finish();
                startActivity(intent);
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
