package com.example.service_base;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class send_request extends AppCompatActivity {

    Button btn;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
        setTitle("Отправить запрос");
        btn = findViewById(R.id.btn_sent_request);
        btn.setOnClickListener(sent);
    }


    View.OnClickListener sent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(context,"Запрос отправлен",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

}
