package com.example.service_base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.service_base.Repair_item.Auth;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    MenuItem new_repair;
    MenuItem logout;
    MenuItem new_parts;
    MenuItem login;
    ImageView imageView;

    @Override
    protected void onResume() {
        super.onResume();

        setTitle("Поиск");

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

        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);

        imageView = headerLayout.findViewById(R.id.logo);

        new_repair = menu.findItem(R.id.new_repair);
        logout= menu.findItem(R.id.logout);
        new_parts = menu.findItem(R.id.new_parts);
        login = menu.findItem(R.id.login);

        setTitle("Поиск");



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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_find()).commit();
                imageView.setImageResource(R.drawable.find_logo);
                setTitle("Поиск");
                break;
            case R.id.news:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_news()).commit();
                imageView.setImageResource(R.drawable.news_logo);
                setTitle("Новости");
                break;
            case R.id.scaner:
                new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
                break;
            case R.id.send_request:
                intent = new Intent(this, send_request.class);
                this.startActivity(intent);
                setTitle("Отправить запрос");
                break;
            case R.id.price:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_price()).commit();
                imageView.setImageResource(R.drawable.cost_logo);
                break;
            case R.id.about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new fragment_about_us()).commit();
                imageView.setImageResource(R.drawable.about_us_logo);
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

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Сканер закрыт", Toast.LENGTH_LONG).show();
            } else {
                String str = result.getContents();
                int intIndex = str.indexOf("СЦ");

                if(intIndex == - 1) {
                    Toast.makeText(this, "Просканируйте правильный QR код", Toast.LENGTH_LONG).show();
                } else {

                    String[] regex = result.getContents().split("(?<=\\D)(?=\\d)");
                    Intent mediaStreamIntent = new Intent(this, order_activity.class);
                    mediaStreamIntent.putExtra("id", regex[1]);
                    this.startActivity(mediaStreamIntent);

                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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



