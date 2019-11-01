package com.example.service_base;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.adapter.RepairAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class fragment_find extends Fragment {

    private RecyclerView repairView;
    private RepairAdapter repairAdapter;
    private List<Repair_item> repair_items = new ArrayList<>();
    private ProgressDialog pDialog;

    //url
    private static String url_all_repair_list = "http://s55111.hostru05.fornex.org/db_read_main_list.php";
    HttpURLConnection urlConnection;
    //array
    JSONArray repair = null;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_DATE = "date";
    private static final String TAG_ID = "id";
    private static final String TAG_STATUS = "status";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find, container, false);


        // Загружаем продукты в фоновом потоке
        new LoadAllProducts().execute();

        setInitialData();

        repairView = v.findViewById(R.id.recycleView_repair);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        repairView.setLayoutManager(layoutManager);

        repairView.setHasFixedSize(true);

        repairAdapter = new RepairAdapter(repair_items);

        repairView.setAdapter(repairAdapter);

        return v;
    }

    private void setInitialData(){

        repair_items.add(new Repair_item (1, "22.02", "Open"));
        repair_items.add(new Repair_item (2, "21.02", "Open"));
        repair_items.add(new Repair_item (3, "20.02", "Open"));
        repair_items.add(new Repair_item (4, "10.02", "Close"));
    }





    /**
     * Фоновый Async Task для загрузки всех продуктов по HTTP запросу
     * */
    class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Перед началом фонового потока Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Загрузка продуктов. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Получаем все продукт из url
         * */
        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();
            JSONArray repairs;

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_main_list.php",JSONParser.GET);
                repairs = json.getJSONArray("repair");



            } catch (JSONException e) {
                e.printStackTrace();
            }




            return null;
        }

        /**
         * После завершения фоновой задачи закрываем прогрес диалог
         * **/
        protected void onPostExecute(String file_url) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();


        }

    }


}



