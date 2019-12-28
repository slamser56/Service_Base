package com.example.service_base;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.service_base.Repair_item.Auth;
import com.example.service_base.Repair_item.Repair_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class login extends AppCompatActivity {
    Context context = this;
    private ProgressDialog pDialog;

    EditText Tlogin;
    EditText Tpass;
    Button Tsignup;
    int id_worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Tlogin = findViewById(R.id.login);
        Tpass = findViewById(R.id.pass);
        Tsignup = findViewById(R.id.btnlogin);

        Tsignup.setOnClickListener(singin);
        setTitle("Авторизация");
    }





    View.OnClickListener singin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences auth = getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = auth.edit();
            editor.clear().apply();
            if (Tlogin.getText().length() == 0 && Tpass.getText().length() == 0)
            {
                Toast.makeText(context, "Введите логин или пароль", Toast.LENGTH_SHORT).show();
            }
            else {
                new Login().execute();
            }

        }
    };



    class Login extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @SuppressLint("WrongThread")
        protected String doInBackground(String... args) {
            JSONParser jsonParser = new JSONParser();
            ContentValues param = new ContentValues();
            JSONArray JSON_array = null;
            param.put("user", Tlogin.getText().toString());
            param.put("pass", Tpass.getText().toString());

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_signin.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {



                        ArrayList<String> mString_type = new ArrayList<>();
                        JSON_array = json.getJSONArray("worker");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject c = JSON_array.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            id_worker = c.getInt("id_worker");
                        }


                        return Repair_item.TAG_SUCCESS;
                    } else {
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


        protected void onPostExecute(String result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();

            if (result == Repair_item.TAG_NOT_FOUND_REPAIR) {
                Toast.makeText(context, "Авторизация прошла неуспешно", Toast.LENGTH_SHORT).show();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_SHORT).show();
            } else if (result == Repair_item.TAG_SUCCESS) {
                Toast.makeText(context, "Авторизация прошла успешно", Toast.LENGTH_SHORT).show();
                SharedPreferences auth = getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = auth.edit();
                editor.putString(Auth.APP_PREFERENCES_LOGIN, Tlogin.getText().toString());
                editor.putString(Auth.APP_PREFERENCES_PASS, Tpass.getText().toString());
                editor.putBoolean(Auth.APP_PREFERENCES_AUTORIZED, true);
                editor.putString(Auth.APP_PREFERENCES_ID_WORKER, String.valueOf(id_worker));
                editor.apply();
                finish();
            }

        }

    }





}
