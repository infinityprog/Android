package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;
    private TextView num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        num = (TextView) findViewById(R.id.txv_name) ;
        readS7 = new ReadTaskS7(this.findViewById(android.R.id.content) , num);
        SharedPreferences sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(sharedpreferences.getString("role",null) == "ADMIN") {
            writeS7 = new WriteTaskS7();
            writeS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        }
    }

    public void deconnexion(View view) {
    }
}
