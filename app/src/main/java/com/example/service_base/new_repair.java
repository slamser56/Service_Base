package com.example.service_base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.service_base.Repair_item.Repair_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class new_repair extends AppCompatActivity {

    private ProgressDialog pDialog;
    Context context = this;


    //TEXT
    EditText Tstatus;
    EditText Ttype_of_repair;
    EditText Tsn;
    EditText Timei;
    EditText Tunique_number;
    EditText Tproduct;
    EditText Tdate_of_warranty;
    EditText Tappearance;
    EditText Tadditional_description;
    EditText Tmalfunction;
    EditText Tcontractor;
    EditText Tcontact_person;
    EditText Tphone;
    EditText Tmail;
    EditText Tadress;
    Button button;
    Spinner malfunction_spinner;
    Spinner type_of_repair_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_add);
        initAll();

        new ReadSpinner().execute();

        button.setOnClickListener(Onbutton);

    }


    private View.OnClickListener Onbutton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddProduct().execute();
        }
    };


    private void initAll() {
        Tstatus = findViewById(R.id.status);
        Ttype_of_repair = findViewById(R.id.type_of_repair);
        Tsn = findViewById(R.id.serial_number);
        Timei = findViewById(R.id.imei);
        Tunique_number = findViewById(R.id.unique_number);
        Tproduct = findViewById(R.id.product);
        Tdate_of_warranty = findViewById(R.id.date_of_warranty);
        Tappearance = findViewById(R.id.appearance);
        Tadditional_description = findViewById(R.id.additional_description);
        Tmalfunction = findViewById(R.id.malfunction);
        Tcontractor = findViewById(R.id.contractor);
        Tcontact_person = findViewById(R.id.contact_person);
        Tphone = findViewById(R.id.phone);
        Tmail = findViewById(R.id.mail);
        Tadress = findViewById(R.id.adress);
        button = findViewById(R.id.btn_order_add);
        malfunction_spinner = findViewById(R.id.malfunction_spinner);
        type_of_repair_spinner = findViewById(R.id.type_of_repair_spinner);
    }



    public class AddProduct extends AsyncTask<String, String, String> {

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
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");
            param.put("status_id", "0");
            param.put("id_malfunction", String.valueOf(malfunction_spinner.getSelectedItemId()));
            param.put("sn", Tsn.getText().toString());
            param.put("imei", Timei.getText().toString());
            param.put("unique_number", Tunique_number.getText().toString());
            param.put("product", Tproduct.getText().toString());
            param.put("date_of_warranty", Tdate_of_warranty.getText().toString());
            param.put("appearance", Tappearance.getText().toString());
            param.put("additional_description", Tadditional_description.getText().toString());
            param.put("id_type_of_repair", String.valueOf(type_of_repair_spinner.getSelectedItemId()));
            param.put("contractor", Tcontractor.getText().toString());
            param.put("contact_person", Tcontact_person.getText().toString());
            param.put("phone", Tphone.getText().toString());
            param.put("mail", Tmail.getText().toString());
            param.put("adress", Tadress.getText().toString());

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_new_order.php", JSONParser.POST, param);
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
                Toast.makeText(context, "REPAIR CREATE", Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }


    public class ReadSpinner extends AsyncTask<String, String, String> {

        String[] type_array;
        String[] malfunction_array;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка. Подождите...");

            pDialog.setCancelable(false);
            pDialog.show();
        }

        @SuppressLint("WrongThread")
        protected String doInBackground(String... args) {

            JSONArray JSON_array_malfunction = null;
            JSONArray JSON_array_type_of_repair = null;
            JSONParser jsonParser = new JSONParser();

            ContentValues param = new ContentValues();
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_malfunction.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {


                        ArrayList<String> mString = new ArrayList<>();
                        JSON_array_malfunction = json.getJSONArray("repair_malfunction");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array_malfunction.length(); i++) {
                            JSONObject c = JSON_array_malfunction.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt("id_malfunction");
                            String malfunction = c.getString("malfunction");
                            // Создаем новый List
                            mString.add(new String(malfunction));
                        }

                        malfunction_array = mString.toArray(new String[mString.size()]);



                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }


                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_type_of_repair.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {


                        ArrayList<String> mString_type = new ArrayList<>();
                        JSON_array_type_of_repair = json.getJSONArray("repair_type_of_repair");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array_type_of_repair.length(); i++) {
                            JSONObject c = JSON_array_type_of_repair.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt("id_type");
                            String type = c.getString("name");
                            // Создаем новый List
                            mString_type.add(new String(type));
                        }

                        type_array = mString_type.toArray(new String[mString_type.size()]);




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
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_LONG).show();
                finish();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_LONG).show();
                finish();
            } else if (result == Repair_item.TAG_SUCCESS) {



                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, malfunction_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                malfunction_spinner.setAdapter(arrayAdapter);


                ArrayAdapter<String> arrayAdapter_type = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, type_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                type_of_repair_spinner.setAdapter(arrayAdapter_type);
            }

        }

    }

















}
