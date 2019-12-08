package com.example.service_base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.service_base.Repair_item.Comment;
import com.example.service_base.Repair_item.Parts;
import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.Repair_item.Repair_work;
import com.example.service_base.adapter.CommentAdapter;
import com.example.service_base.adapter.PartsAdapter;
import com.example.service_base.adapter.RepairAdapter;
import com.example.service_base.adapter.RepairWorkAdapter;

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
    private List<Repair_work> repair_works = new ArrayList<>();
    private List<Parts> parts = new ArrayList<>();
    Context context = this;


    EditText Tdate;
    EditText Tid;
    EditText Tsn;
    EditText Timei;
    EditText Tunique_number;
    EditText Tproduct;
    EditText Tdate_of_warranty;
    EditText Tappearance;
    EditText Tadditional_description;
    EditText Tcontractor;
    EditText Tcontact_person;
    EditText Tphone;
    EditText Tmail;
    EditText Tadress;

    EditText Tcomment;
    EditText Twork;
    EditText Tparts;

    Button btncomment;
    Button btnwork;
    Button btnworkdelete;

    Spinner Tstatus;
    Spinner Tmalfunction;
    Spinner Ttype_of_repair;


    private RecyclerView commentView;
    private RecyclerView repairworkView;
    private RecyclerView partsView;
    private CommentAdapter commentAdapter;
    private RepairWorkAdapter repairWorkAdapter;
    private PartsAdapter partsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_activity);
        init_all();
        commentView = findViewById(R.id.comment_rv);
        repairworkView = findViewById(R.id.repair_view);
        partsView = findViewById(R.id.parts_view);


        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        commentView.setLayoutManager(layoutManager);
        commentView.setHasFixedSize(true);
        commentAdapter = new CommentAdapter(comments, context);
        commentView.setAdapter(commentAdapter);

        LinearLayoutManager layoutManagerRepair = new LinearLayoutManager(context);
        repairworkView.setLayoutManager(layoutManagerRepair);
        repairworkView.setHasFixedSize(true);
        repairWorkAdapter = new RepairWorkAdapter(repair_works, context);
        repairworkView.setAdapter(repairWorkAdapter);

        LinearLayoutManager layoutManagerParts = new LinearLayoutManager(context);
        partsView.setLayoutManager(layoutManagerParts);
        partsView.setHasFixedSize(true);
        partsAdapter = new PartsAdapter(parts, context);
        partsView.setAdapter(partsAdapter);


        new LoadAllProducts().execute();


        btncomment.setOnClickListener(Onbutton);
        btnwork.setOnClickListener(Onbuttonwork);
        btnworkdelete.setOnClickListener(Onbuttondeletework);


    }

    /*
     * Button listener
     */


    private View.OnClickListener Onbutton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddComment().execute();
        }
    };

    private View.OnClickListener Onbuttonwork = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AddWork().execute();
        }
    };

    private View.OnClickListener Onbuttondeletework = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new DeleteWork().execute();
        }
    };


    /*
     * Initial all element;
     */

    void init_all() {
        Tid = findViewById(R.id.order);
        Tdate = findViewById(R.id.date);
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

        Tcomment = findViewById(R.id.comments);
        btncomment = findViewById(R.id.comments_button);
        Twork = findViewById(R.id.work);
        btnwork = findViewById(R.id.button);
        Tparts = findViewById(R.id.part);
        btnworkdelete = findViewById(R.id.button3);

    }


    /*
     * Dialog AsyncTask
     */

    class LoadAllProducts extends AsyncTask<String, String, String> {

        String[] statusArray;
        String[] malfunction_array;
        String[] type_array;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Загрузка. Подождите...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected String doInBackground(String... args) {


            comments.clear();
            repair_works.clear();
            parts.clear();


            Bundle arguments = getIntent().getExtras();
            JSONParser jsonParser = new JSONParser();
            JSONArray JSON_array = null;

            ContentValues param = new ContentValues();
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");
            param.put("id_order", arguments.get("id").toString());

            try {

                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_full_order.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                        // ремонт найден
                        // Получаем масив из Ремонтов
                        JSON_array = json.getJSONArray(Repair_item.TAG_REPAIR);


                        JSONObject c = JSON_array.getJSONObject(0);

                        // Сохраняем каждый json елемент в переменную
                        int id = c.getInt(Repair_item.TAG_ID_ORDER);
                        String date = c.getString(Repair_item.TAG_DATE);
                        int id_status = c.getInt(Repair_item.TAG_ID_STATUS);
                        String date_complete = c.getString(Repair_item.TAG_DATE_COMPLETE);
                        int id_type_of_repair = c.getInt(Repair_item.TAG_ID_TYPE_OF_REPAIR);
                        String sn = c.getString(Repair_item.TAG_SN);
                        int imei = c.getInt(Repair_item.TAG_IMEI);
                        String unique_number = c.getString(Repair_item.TAG_UNUQUE_NUMBER);
                        String product = c.getString(Repair_item.TAG_PRODUCT);
                        String date_of_warranty = c.getString(Repair_item.TAG_DATE_OF_WARRANTY);
                        String appearance = c.getString(Repair_item.TAG_APPEARANCE);
                        String additional_description = c.getString(Repair_item.TAG_ADDITIONAL_DESCRIPTION);
                        int id_malfunction = c.getInt(Repair_item.TAG_ID_MALFUNCTION);
                        String contractor = c.getString(Repair_item.TAG_CONTRACTOR);
                        String contact_person = c.getString(Repair_item.TAG_CONTACT_PERSON);
                        String phone = c.getString(Repair_item.TAG_PHONE);
                        String mail = c.getString(Repair_item.TAG_MAIL);
                        String adress = c.getString(Repair_item.TAG_ADRESS);

                        repair_item = new Repair_item(id, date, id_status, date_complete, id_type_of_repair, sn, imei, unique_number, product, date_of_warranty, appearance, additional_description, id_malfunction, contractor, contact_person, phone, mail, adress);

                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }


                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_status.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {

                        ArrayList<String> mString_type = new ArrayList<>();
                        JSON_array = json.getJSONArray("status_repair");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject c = JSON_array.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id_status = c.getInt("id_status");
                            String status = c.getString("status");
                            // Создаем новый List
                            mString_type.add(new String(status));
                        }

                        statusArray = mString_type.toArray(new String[mString_type.size()]);

                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }

                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_malfunction.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {

                        ArrayList<String> mString = new ArrayList<>();
                        JSON_array = json.getJSONArray("repair_malfunction");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject c = JSON_array.getJSONObject(i);
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
                        JSON_array = json.getJSONArray("repair_type_of_repair");
                        // перебор всех неисправностей
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject c = JSON_array.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt("id_type");
                            String type = c.getString("name");
                            // Создаем новый List
                            mString_type.add(new String(type));
                        }

                        type_array = mString_type.toArray(new String[mString_type.size()]);



                    } else {
                        // продукт не найден
                        return Repair_item.TAG_NOT_FOUND_REPAIR;
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }


                return Repair_item.TAG_SUCCESS;

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
                Tid.setText(String.valueOf(repair_item.getId()));
                Tdate.setText(repair_item.getDate());
                Tsn.setText(repair_item.getSn());
                Timei.setText(String.valueOf(repair_item.getImei()));
                Tunique_number.setText(repair_item.getUnique_number());
                Tproduct.setText(repair_item.getProduct());
                Tdate_of_warranty.setText(repair_item.getDate_of_warranty());
                Tappearance.setText(repair_item.getAppearance());
                Tadditional_description.setText(repair_item.getAdditional_description());
                Tcontractor.setText(repair_item.getContractor());
                Tcontact_person.setText(repair_item.getContact_person());
                Tphone.setText(repair_item.getPhone());
                Tmail.setText(repair_item.getMail());
                Tadress.setText(repair_item.getAdress());


                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, statusArray);
                // Определяем разметку для использования при выборе элемента
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                Tstatus.setAdapter(arrayAdapter);
                Tstatus.setSelection(repair_item.getId_status());
                Tstatus.setEnabled(false);



                ArrayAdapter<String> arrayAdapterMalfunction = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, malfunction_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapterMalfunction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                Tmalfunction.setAdapter(arrayAdapterMalfunction);
                Tmalfunction.setSelection(repair_item.getId_malfunction());
                Tmalfunction.setEnabled(false);

                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, type_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                Ttype_of_repair.setAdapter(arrayAdapterType);
                Ttype_of_repair.setSelection(repair_item.getId_type_of_repair());
                Ttype_of_repair.setEnabled(false);





                partsAdapter.notifyDataSetChanged();
                repairWorkAdapter.notifyDataSetChanged();
                commentAdapter.notifyDataSetChanged();
                Toast.makeText(context, "FOUND", Toast.LENGTH_SHORT).show();
            }


        }

    }

    class AddComment extends AsyncTask<String, String, String> {


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
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");
            param.put("id_comment", arguments.get("id").toString());
            param.put("worker", "admin");
            param.put("comment", Tcomment.getText().toString());

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_write_comment.php", JSONParser.POST, param);
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
                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                Tcomment.setText("");
                new LoadAllProducts().execute();
            }


        }

    }

    class AddWork extends AsyncTask<String, String, String> {


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
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");
            param.put("id_work", arguments.get("id").toString());
            param.put("worker_w", "admin");
            param.put("work_name", Twork.getText().toString());
            param.put("cost", "");

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_write_work.php", JSONParser.POST, param);
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
                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                Twork.setText("");
                new LoadAllProducts().execute();
            }


        }

    }




    class DeleteWork extends AsyncTask<String, String, String> {


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
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");


            boolean[] checked = repairWorkAdapter.getChecked();
            String work_d = new String();
            for (int i = 0; i < checked.length; i++) {
                if (checked[i] == true)
                    work_d += "," + repair_works.get(i).getId_w();
            }

            if (work_d.length() > 0)
                work_d = work_d.substring(1);

            param.put("id_w", work_d);


            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_delete_work.php", JSONParser.POST, param);
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
                Toast.makeText(context, "update", Toast.LENGTH_SHORT).show();
                new LoadAllProducts().execute();
            }

        }

    }


}




                        /*
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
                            comments.add(new Comment(id_c, comment_c, worker, date_c));
                        }

                        JSON_array_repair_work = json.getJSONArray(Repair_work.TAG_REPAIR_WORK);

                        // перебор всех работ
                        for (int i = 0; i < JSON_array_repair_work.length(); i++) {
                            JSONObject w = JSON_array_repair_work.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id_w = w.getInt(Repair_work.TAG_ID);
                            int price_w = w.getInt(Repair_work.TAG_COST);
                            int time = w.getInt(Repair_work.TAG_TIME);
                            String date_w = w.getString(Repair_work.TAG_DATE);
                            String repair_work = w.getString(Repair_work.TAG_WORK_NAME);
                            String worker_w = w.getString(Repair_work.TAG_WORKER);
                            // Создаем новый List
                            repair_works.add(new Repair_work(id_w, price_w, time, repair_work, worker_w, date_w));
                        }


                        JSON_array_parts = json.getJSONArray(Parts.TAG_PARTS);

                        // перебор всех работ
                        for (int i = 0; i < JSON_array_parts.length(); i++) {
                            JSONObject p = JSON_array_parts.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id_p = p.getInt(Parts.TAG_ID);
                            int cost_p = p.getInt(Parts.TAG_COST);
                            String name_p = p.getString(Parts.TAG_NAME);
                            String date_p = p.getString(Parts.TAG_DATE);
                            String sn_p = p.getString(Parts.TAG_SN);
                            String pn_p = p.getString(Parts.TAG_PN);
                            String description_p = p.getString(Parts.TAG_DESCRIPTION);
                            // Создаем новый List
                            parts.add(new Parts(id_p, name_p, date_p, sn_p, pn_p, description_p, cost_p));
                        }



                         */