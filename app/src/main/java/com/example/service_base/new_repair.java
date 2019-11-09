package com.example.service_base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service_base.Repair_item.Repair_item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_add);
        initAll();

        button.setOnClickListener(Onbutton);

    }



    private View.OnClickListener Onbutton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context, "Загрузка", Toast.LENGTH_SHORT).show();
            new LoadAllProducts().execute();
        }
    };



    private void initAll()
    {
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
    }


    /**
     * Фоновый Async Task для загрузки всех продуктов по HTTP запросу
     * */
    public class LoadAllProducts extends AsyncTask<String, String, String> {

        /**
         * Перед началом фонового потока Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка продуктов. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Получаем все продукт из url
         * */
        @SuppressLint("WrongThread")
        protected String doInBackground(String... args) {

            JSONParser jsonParser = new JSONParser();

            ContentValues param=new ContentValues();
            param.put("user","s55111_standart");
            param.put("pass","5tva3ijjcxjh5w5het");
            param.put("status", Tstatus.getText().toString());
            param.put("type_of_repair",Ttype_of_repair.getText().toString());
            param.put("sn",Tsn.getText().toString());
            param.put("imei",Timei.getText().toString());
            param.put("unique_number",Tunique_number.getText().toString());
            param.put("product",Tproduct.getText().toString());
            param.put("date_of_warranty",Tdate_of_warranty.getText().toString());
            param.put("appearance",Tappearance.getText().toString());
            param.put("additional_description",Tadditional_description.getText().toString());
            param.put("malfunction",Tmalfunction.getText().toString());
            param.put("contractor",Tcontractor.getText().toString());
            param.put("contact_person",Tcontact_person.getText().toString());
            param.put("phone",Tphone.getText().toString());
            param.put("mail",Tmail.getText().toString());
            param.put("adress",Tadress.getText().toString());

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_write_order.php",JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                        return Repair_item.TAG_SUCCESS;
                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                }
                else {
                    return Repair_item.TAG_ERROR;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        /**
         * После завершения фоновой задачи закрываем прогрес диалог
         * **/
        protected void onPostExecute(String result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();

            if (result == Repair_item.TAG_NOT_FOUND_REPAIR)
            {
                Toast.makeText(context,"REPAIR NOT FOUND",Toast.LENGTH_SHORT).show();
            }
            else if (result == Repair_item.TAG_ERROR)
            {
                Toast.makeText(context,"BAD CONNECT TO SERVER",Toast.LENGTH_SHORT).show();
            }
            else if (result == Repair_item.TAG_SUCCESS)
            {
                Toast.makeText(context,"FOUND",Toast.LENGTH_SHORT).show();
                finish();
            }

        }

    }
}
