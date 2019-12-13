package com.example.service_base;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    EditText Tparts;
    EditText Tcostwork;

    Button btncomment;
    Button btnwork;
    Button btnworkdelete;
    Button btnworkedit;

    Spinner Tstatus;
    Spinner Tmalfunction;
    Spinner Ttype_of_repair;
    Spinner chooseSpinner;

    MenuItem safe;
    MenuItem edit_order;
    MenuItem edit_order_false;





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
         safe = menu.findItem(R.id.safe);
         edit_order = menu.findItem(R.id.edit_order);
         edit_order_false = menu.findItem(R.id.edit_order_false);
         safe.setEnabled(false);
         edit_order_false.setVisible(false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.edit_order:
                safe.setEnabled(true);
                edit_order.setVisible(false);
                edit_order_false.setVisible(true);
                enable_all();
                return true;
            case R.id.safe:
                safe.setEnabled(false);
                edit_order.setVisible(true);
                disable_all();
                edit_order_false.setVisible(false);
                new UpdateAllProducts().execute();
                return true;
            case R.id.QR:
                Toast.makeText(this,"QR",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.edit_order_false:
                edit_order_false.setVisible(false);
                safe.setEnabled(false);
                edit_order.setVisible(true);
                disable_all();
                new LoadAllProducts().execute();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




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
        disable_all();
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
        btnworkedit.setOnClickListener(Onbuttoneditwork);
        Tdate_of_warranty.setOnClickListener(Date_warranty);

    }


    //Format date
    public static String getCurrentDate(Calendar date) {
        final String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = date.getTime();
        return dateFormat.format(today);
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener date_of_warranty=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.YEAR, year);
            Tdate_of_warranty.setText(getCurrentDate(dateAndTime));
        }
    };
    Calendar dateAndTime=Calendar.getInstance();

    private View.OnClickListener Date_warranty = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new DatePickerDialog(context,date_of_warranty, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH)).show();
        }
    };

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

            showChooseworks();
           // new AddWork().execute();
        }
    };

    private View.OnClickListener Onbuttondeletework = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new DeleteWork().execute();
            onedit = false;
            btnworkdelete.setEnabled(false);
            repairWorkAdapter.HideCheckBox(true);
        }
    };

    boolean onedit = false;

    private View.OnClickListener Onbuttoneditwork = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onedit = !onedit;

            if (onedit == true)
            {
                btnworkdelete.setEnabled(true);
                repairWorkAdapter.HideCheckBox(false);
            }
            else
            {
                btnworkdelete.setEnabled(false);
                repairWorkAdapter.HideCheckBox(true);
            }

            repairWorkAdapter.notifyDataSetChanged();

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
        Tcostwork = findViewById(R.id.cost_work);

        Tcomment = findViewById(R.id.comments);
        btncomment = findViewById(R.id.comments_button);
        btnwork = findViewById(R.id.button);
        Tparts = findViewById(R.id.part);
        btnworkdelete = findViewById(R.id.button3);
        btnworkedit = findViewById(R.id.button2);
    }

    void disable_all() {
        Tid.setEnabled(false);
        Tdate.setEnabled(false);
        Tstatus.setEnabled(false);
        Ttype_of_repair.setEnabled(false);
        Tsn.setEnabled(false);
        Timei.setEnabled(false);
        Tunique_number.setEnabled(false);
        Tproduct.setEnabled(false);
        Tdate_of_warranty.setEnabled(false);
        Tappearance.setEnabled(false);
        Tadditional_description.setEnabled(false);
        Tmalfunction.setEnabled(false);
        Tcontractor.setEnabled(false);
        Tcontact_person.setEnabled(false);
        Tphone.setEnabled(false);
        Tmail.setEnabled(false);
        Tadress.setEnabled(false);

        Tstatus.setEnabled(false);
        Tmalfunction.setEnabled(false);
        Ttype_of_repair.setEnabled(false);
        btnworkdelete.setEnabled(false);
    }

    void enable_all() {
        Tstatus.setEnabled(true);
        Ttype_of_repair.setEnabled(true);
        Tsn.setEnabled(true);
        Timei.setEnabled(true);
        Tunique_number.setEnabled(true);
        Tproduct.setEnabled(true);
        Tdate_of_warranty.setEnabled(true);
        Tappearance.setEnabled(true);
        Tadditional_description.setEnabled(true);
        Tmalfunction.setEnabled(true);
        Tcontractor.setEnabled(true);
        Tcontact_person.setEnabled(true);
        Tphone.setEnabled(true);
        Tmail.setEnabled(true);
        Tadress.setEnabled(true);

        Tstatus.setEnabled(true);
        Tmalfunction.setEnabled(true);
        Ttype_of_repair.setEnabled(true);
        btnworkdelete.setEnabled(true);
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


        @SuppressLint("WrongThread")
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


                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_comment.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {

                        JSON_array = json.getJSONArray("repair_comment");

                        // перебор всех комментов
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject a = JSON_array.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id_c = a.getInt(Comment.TAG_ID_COMMENT);
                            String date_c = a.getString(Comment.TAG_DATE_COMMENT);
                            String comment_c = a.getString(Comment.TAG_COMMENT);
                            String worker = a.getString(Comment.TAG_WORKER);
                            // Создаем новый List
                            comments.add(new Comment(id_c, comment_c, worker, date_c));
                        }
                    } else {
                        Log.d("COMMENT", "No comment");
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }


                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_work.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                JSON_array = json.getJSONArray(Repair_work.TAG_REPAIR_WORK);

                // перебор всех работ
                for (int i = 0; i < JSON_array.length(); i++) {
                    JSONObject w = JSON_array.getJSONObject(i);
                    // Сохраняем каждый json елемент в переменную
                    int id_w = w.getInt(Repair_work.TAG_ID);
                    int price_w = w.getInt(Repair_work.TAG_COST);
                    String date_w = w.getString(Repair_work.TAG_DATE);
                    String repair_work = w.getString(Repair_work.TAG_WORK_NAME);
                    String worker_w = w.getString(Repair_work.TAG_WORKER);
                    // Создаем новый List
                    repair_works.add(new Repair_work(id_w, price_w, repair_work, worker_w, date_w));
                    int cost = 0;
                    for (Repair_work r:repair_works) {
                        cost += r.getPrice_w();
                    }
                    Tcostwork.setText(String.valueOf(cost)+"р");
                }
                    } else {
                        Log.d("WORK", "No work");
                    }
                } else {
                    return Repair_item.TAG_ERROR;
                }


                json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_parts.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {
                JSON_array = json.getJSONArray("repair_parts");

                // перебор всех работ
                for (int i = 0; i < JSON_array.length(); i++) {
                    JSONObject p = JSON_array.getJSONObject(i);
                    // Сохраняем каждый json елемент в переменную
                    int id_p = p.getInt(Parts.TAG_ID);
                    int cost_p = p.getInt(Parts.TAG_COST);
                    String name_p = p.getString(Parts.TAG_NAME);
                    String date_p = p.getString(Parts.TAG_DATE);
                    String sn_p = p.getString(Parts.TAG_SN);
                    String pn_p = p.getString(Parts.TAG_PN);
                    String description_p = p.getString(Parts.TAG_DESCRIPTION);
                    String worker_p = p.getString(Parts.TAG_WORKER);
                    // Создаем новый List
                    parts.add(new Parts(id_p, name_p, date_p, sn_p, pn_p, description_p, cost_p, worker_p));
                }
                    } else {
                        Log.d("PARTS", "No parts");
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
                finish();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_SHORT).show();
                finish();
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


                ArrayAdapter<String> arrayAdapterMalfunction = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, malfunction_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapterMalfunction.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                Tmalfunction.setAdapter(arrayAdapterMalfunction);
                Tmalfunction.setSelection(repair_item.getId_malfunction());


                ArrayAdapter<String> arrayAdapterType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, type_array);
                // Определяем разметку для использования при выборе элемента
                arrayAdapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                Ttype_of_repair.setAdapter(arrayAdapterType);
                Ttype_of_repair.setSelection(repair_item.getId_type_of_repair());



                partsAdapter.notifyDataSetChanged();
                repairWorkAdapter.notifyDataSetChanged();
                commentAdapter.notifyDataSetChanged();



                Toast.makeText(context, "Заказ загружен", Toast.LENGTH_SHORT).show();
            }


        }

    }

    class UpdateAllProducts extends AsyncTask<String, String, String> {


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

            comments.clear();
            repair_works.clear();
            parts.clear();

            Bundle arguments = getIntent().getExtras();
            JSONParser jsonParser = new JSONParser();

            ContentValues param = new ContentValues();
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");
            param.put("id_order", arguments.get("id").toString());
            param.put("status_id", Tstatus.getSelectedItemId());
            param.put("id_malfunction", Tmalfunction.getSelectedItemId());
            param.put("sn", Tsn.getText().toString());
            param.put("imei", Timei.getText().toString());
            param.put("unuque_number", Tunique_number.getText().toString());
            param.put("product", Tproduct.getText().toString());
            param.put("date_of_warranty", Tdate_of_warranty.getText().toString());
            param.put("appearance", Tappearance.getText().toString());
            param.put("additional_description", Tadditional_description.getText().toString());
            param.put("id_type_of_repair", Ttype_of_repair.getSelectedItemId());
            param.put("contractor", Tcontractor.getText().toString());
            param.put("contact_person", Tcontact_person.getText().toString());
            param.put("phone", Tphone.getText().toString());
            param.put("mail", Tmail.getText().toString());
            param.put("adress", Tadress.getText().toString());

            try {

                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_update_order.php", JSONParser.POST, param);
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
                finish();
            } else if (result == Repair_item.TAG_ERROR) {
                Toast.makeText(context, "BAD CONNECT TO SERVER", Toast.LENGTH_SHORT).show();
                finish();
            } else if (result == Repair_item.TAG_SUCCESS) {

                new LoadAllProducts().execute();

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
            param.put("id_order", arguments.get("id").toString());
            param.put("id_worker", "0");
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
            param.put("id_order", arguments.get("id").toString());
            param.put("id_worker", "0");
            param.put("id_works", String.valueOf(chooseSpinner.getSelectedItemId()));

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
                Toast.makeText(context, "Работа добавлена", Toast.LENGTH_SHORT).show();
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



    public class ReadSpinner extends AsyncTask<String, String, String> {

        String[] works;

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

            JSONArray JSON_array = null;
            JSONParser jsonParser = new JSONParser();

            ContentValues param = new ContentValues();
            param.put("user", "s55111_standart");
            param.put("pass", "5tva3ijjcxjh5w5het");

            try {
                JSONObject json = jsonParser.makeHttpRequest("http://s55111.hostru05.fornex.org/db_read_works.php", JSONParser.POST, param);
                if (!json.has(Repair_item.TAG_ERROR)) {
                    int success = json.getInt(Repair_item.TAG_SUCCESS);
                    if (success == 1) {

                        ArrayList<String> mString = new ArrayList<>();
                        JSON_array = json.getJSONArray("repair_works");
                        for (int i = 0; i < JSON_array.length(); i++) {
                            JSONObject c = JSON_array.getJSONObject(i);
                            // Сохраняем каждый json елемент в переменную
                            int id = c.getInt("id_works");
                            String work_name = c.getString("work_name");
                            String cost = c.getString("cost");

                            work_name += " ("+cost+"р)";
                            // Создаем новый List
                            mString.add(new String(work_name));
                        }

                        works = mString.toArray(new String[mString.size()]);


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



                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, works);
                // Определяем разметку для использования при выборе элемента
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //set
                chooseSpinner.setAdapter(arrayAdapter);

            }

        }

    }


/*
*Modal Dialog
 */


    public void showChooseworks() {
        final AlertDialog.Builder ChooseDialog = new AlertDialog.Builder(this);

        ChooseDialog.setTitle("Выбор работ");

        View linearlayout = getLayoutInflater().inflate(R.layout.modal_dialog, null);
        ChooseDialog.setView(linearlayout);

        chooseSpinner = (Spinner) linearlayout.findViewById(R.id.spinner_modal);

        new ReadSpinner().execute();

        ChooseDialog.setPositiveButton("Выбрать",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        new AddWork().execute();
                        dialog.dismiss();
                    }
                })

                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

        ChooseDialog.create();
        ChooseDialog.show();
    }


}

