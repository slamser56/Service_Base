package com.example.service_base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.service_base.Repair_item.Comment;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.adapter.CommentAdapter;
import com.example.service_base.adapter.RepairAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class order_activity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private Repair_item repair_item;
    private List<Comment> comments = new ArrayList<>();
    Context context = this;

    EditText Tdate;
    EditText Tid;
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


    private RecyclerView commentView;
    private CommentAdapter commentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_activity);
        init_all();
        commentView = findViewById(R.id.comment_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        commentView.setLayoutManager(layoutManager);

        commentView.setHasFixedSize(true);

        commentAdapter = new CommentAdapter(comments , context);

        commentView.setAdapter(commentAdapter);

        new LoadAllProducts().execute();
    }



    void init_all()
    {
        Tid = findViewById(R.id.order);
        Tdate = findViewById(R.id.date);
        Tstatus= findViewById(R.id.status);
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
    }


    class LoadAllProducts extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка продуктов. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {
            Bundle arguments = getIntent().getExtras();
            JSONParser jsonParser = new JSONParser();
            JSONArray JSON_array_repairs = null;
            JSONArray JSON_array_comment = null;
            ContentValues param=new ContentValues();
            param.put("user","s55111_standart");
            param.put("pass","5tva3ijjcxjh5w5het");
            param.put("id", arguments.get("id").toString());

            try {

                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_repair.php",JSONParser.POST, param);


                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                        // ремонт найден
                        // Получаем масив из Ремонтов
                        JSON_array_repairs = json.getJSONArray(Repair_item.TAG_REPAIR);


                            JSONObject c = JSON_array_repairs.getJSONObject(0);

                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt(Repair_item.TAG_ID);
                            String date = c.getString(Repair_item.TAG_DATE);
                            String status = c.getString(Repair_item.TAG_STATUS);

                            String date_complete = c.getString(Repair_item.TAG_DATE_COMPLETE);
                            String type_of_repair = c.getString(Repair_item.TAG_TYPE_OF_REPAIR);
                            String sn = c.getString(Repair_item.TAG_SN);
                            int imei = c.getInt(Repair_item.TAG_IMEI);
                            String unique_number = c.getString(Repair_item.TAG_UNIQUE_NUMBER);
                            String product = c.getString(Repair_item.TAG_PRODUCT);
                            String date_of_warranty = c.getString(Repair_item.TAG_DATE_OF_WARRANTY);
                            String appearance = c.getString(Repair_item.TAG_APPEARANCE);
                            String additional_description = c.getString(Repair_item.TAG_ADDITIONAL_DESCRIPTION);
                            String malfunction = c.getString(Repair_item.TAG_MALFUNCTION);
                            String contractor = c.getString(Repair_item.TAG_CONTRACTOR);
                            String contact_person = c.getString(Repair_item.TAG_CONTACT_PERSON);
                            String phone = c.getString(Repair_item.TAG_PHONE);
                            String mail = c.getString(Repair_item.TAG_MAIL);
                            String adress = c.getString(Repair_item.TAG_ADRESS);


                            repair_item = new Repair_item(id, date, status, date_complete, type_of_repair, sn, imei, unique_number, product, date_of_warranty, appearance, additional_description, malfunction, contractor, contact_person, phone, mail, adress);


                        JSON_array_comment = json.getJSONArray(Comment.TAG_COMMENT);

                        // перебор всех комментов
                        for (int i = 0; i < JSON_array_comment.length(); i++) {
                            JSONObject a = JSON_array_comment.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id_c = a.getInt(Comment.TAG_ID_COMMENT);
                            String date_c = a.getString(Comment.TAG_DATE_COMMENT);
                           String comment_c = a.getString(Comment.TAG_COMMENT);
                           String worker = a.getString(Comment.TAG_WORKER);
                            // Создаем новый List
                            comments.add(new Comment (id_c, comment_c, worker, date_c));
                        }

                        return Repair_item.TAG_SUCCESS;
                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                }
                else {
                    return Repair_item.TAG_ERROR;
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        protected void onPostExecute(String result) {
            // закрываем прогресс диалог после получение все продуктов
            pDialog.dismiss();

            if (result == Repair_item.TAG_NOT_FOUND_REPAIR)
            {
                Toast.makeText(context,"REPAIR NOT FOUND",Toast.LENGTH_LONG).show();
            }
            else if (result == Repair_item.TAG_ERROR)
            {
                Toast.makeText(context,"BAD CONNECT TO SERVER",Toast.LENGTH_LONG).show();
            }
            else if (result == Repair_item.TAG_SUCCESS)
            {
                Tid.setText(String.valueOf(repair_item.getId()));
                Tdate.setText(repair_item.getDate());
                Tstatus.setText(repair_item.getStatus());
                Ttype_of_repair.setText(repair_item.getType_of_repair());
                Tsn.setText(repair_item.getSn());
                Timei.setText(String.valueOf(repair_item.getImei()));
                Tunique_number.setText(repair_item.getUnique_number());
                Tproduct.setText(repair_item.getProduct());
                Tdate_of_warranty.setText(repair_item.getDate_of_warranty());
                Tappearance.setText(repair_item.getAppearance());
                Tadditional_description.setText(repair_item.getAdditional_description());
                Tmalfunction.setText(repair_item.getMalfunction());
                Tcontractor.setText(repair_item.getContractor());
                Tcontact_person.setText(repair_item.getContact_person());
                Tphone.setText(repair_item.getPhone());
                Tmail.setText(repair_item.getMail());
                Tadress.setText(repair_item.getAdress());


                commentAdapter.notifyDataSetChanged();

                Toast.makeText(context,"FOUND",Toast.LENGTH_LONG).show();
            }


        }

    }

}
