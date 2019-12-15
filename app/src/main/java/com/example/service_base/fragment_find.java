package com.example.service_base;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.Repair_item.Auth;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.adapter.RepairAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class fragment_find extends Fragment {

    private RecyclerView repairView;
    private RepairAdapter repairAdapter;
    private List<Repair_item> repair_items = new ArrayList<>();
    private ProgressDialog pDialog;
    private boolean firstVisit;
    SharedPreferences auth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find, container, false);

        auth = this.getActivity().getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);

        repairView = v.findViewById(R.id.recycleView_repair);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        repairView.setLayoutManager(layoutManager);

        repairView.setHasFixedSize(true);

        repairAdapter = new RepairAdapter(repair_items, getActivity());

        repairView.setAdapter(repairAdapter);

        // Загружаем продукты в фоновом потоке
        new LoadAllProducts().execute();


        firstVisit = true;
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();

        if (firstVisit) {
            //do stuff for first visit only

            firstVisit = false;
        } else {
            // Загружаем продукты в фоновом потоке
            new LoadAllProducts().execute();
        }


    }

    /**
     * Фоновый Async Task для загрузки всех продуктов по HTTP запросу
     */
    public class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Перед началом фонового потока Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Загрузка. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Получаем все продукт из url
         */
        protected String doInBackground(String... args) {


            repair_items.clear();
            JSONParser jsonParser = new JSONParser();
            JSONArray JSON_array_repairs = null;

            ContentValues param = new ContentValues();
            param.put("user", auth.getString(Auth.APP_PREFERENCES_LOGIN, "guest"));
            param.put("pass", auth.getString(Auth.APP_PREFERENCES_PASS,""));


            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_lite_order.php", JSONParser.POST, param);


                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                        // ремонт найден
                        // Получаем масив из Ремонтов
                        JSON_array_repairs = json.getJSONArray(Repair_item.TAG_REPAIR);

                        // перебор всех ремонтов
                        for (int i = 0; i < JSON_array_repairs.length(); i++) {
                            JSONObject c = JSON_array_repairs.getJSONObject(i);

                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt(Repair_item.TAG_ID_ORDER);
                            String date = c.getString(Repair_item.TAG_DATE);
                            String status = c.getString(Repair_item.TAG_STATUS);
                            // Создаем новый List
                            repair_items.add(new Repair_item(id, date, status));
                        }
                        return Repair_item.TAG_SUCCESS;
                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * После завершения фоновой задачи закрываем прогрес диалог
         **/
        protected void onPostExecute(String result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();

            if (result == Repair_item.TAG_NOT_FOUND_REPAIR) {
                Toast.makeText(getActivity(), "REPAIR NOT FOUND", Toast.LENGTH_LONG).show();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(getActivity(), "BAD CONNECT TO SERVER", Toast.LENGTH_LONG).show();
            } else if (result == Repair_item.TAG_SUCCESS) {
                repairAdapter.notifyDataSetChanged();
            }


        }

    }


}



