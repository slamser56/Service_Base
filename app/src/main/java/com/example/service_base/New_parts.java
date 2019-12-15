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

import com.example.service_base.JSONParser;
import com.example.service_base.R;
import com.example.service_base.Repair_item.Auth;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.order_activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class New_parts extends AppCompatActivity {
    Context context = this;
    private ProgressDialog pDialog;
    EditText Tid_order;
    EditText Tname_parts;
    EditText Tsn;
    EditText Tpn;
    EditText Tdescription;
    EditText Tcost;

    Button  Tparts_add;

    SharedPreferences auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parts);
        auth = getSharedPreferences(Auth.APP_PREFERENCES, Context.MODE_PRIVATE);

        init_all();
        Tparts_add.setOnClickListener(AddParts);
    }


    void init_all()
    {
        Tid_order = findViewById(R.id.id_order);
        Tname_parts = findViewById(R.id.name_parts);
        Tsn = findViewById(R.id.sn);
        Tpn = findViewById(R.id.pn);
        Tdescription = findViewById(R.id.description);
        Tcost = findViewById(R.id.cost);
        Tparts_add = findViewById(R.id.btn_parts_add);

    }


    View.OnClickListener AddParts = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddParts().execute();
        }
    };





    class AddParts extends AsyncTask<String, String, String> {


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
            Bundle arguments = getIntent().getExtras();
            JSONParser jsonParser = new JSONParser();
            ContentValues param = new ContentValues();
            param.put("user", auth.getString(Auth.APP_PREFERENCES_LOGIN, "") );
            param.put("pass", auth.getString(Auth.APP_PREFERENCES_PASS, ""));
            param.put("id_order", Tid_order.getText().toString());
            param.put("name_p", Tname_parts.getText().toString());
            param.put("sn_p", Tsn.getText().toString());
            param.put("pn_p", Tpn.getText().toString());
            param.put("description_p", Tdescription.getText().toString());
            param.put("cost_p", Tcost.getText().toString());
            param.put("id_worker", auth.getString(Auth.APP_PREFERENCES_ID_WORKER, null));

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_write_parts.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
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


        protected void onPostExecute(String result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();

            if (result == Repair_item.TAG_NOT_FOUND_REPAIR) {
                Toast.makeText(context, "REPAIR NOT FOUND", Toast.LENGTH_SHORT).show();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_SHORT).show();
            } else if (result == Repair_item.TAG_SUCCESS) {
                Toast.makeText(context, "Запчасть добавлена", Toast.LENGTH_SHORT).show();

            }

        }

    }


}
