package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Comprime.ReadComprimeActivity;

public class InfoActivity extends AppCompatActivity {

    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;
    private TextView num;
    private TextView version;
    private TextView statut;
    private TextView description;

    private EditText dataBlock;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
        num = (TextView) findViewById(R.id.txv_name) ;
        version = (TextView) findViewById(R.id.txv_version);
        statut = (TextView)  findViewById(R.id.txv_status);
        description = (TextView) findViewById(R.id.txv_description);
        dataBlock = (EditText) findViewById(R.id.edt_dtblck);
        readS7 = new ReadTaskS7(this.findViewById(android.R.id.content) , num,version,statut);
        sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        description.setText(sharedpreferences.getString("nav",null));
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("role",null) == "ADMIN") {
            writeS7 = new WriteTaskS7();
            writeS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        }
    }

    public void deconnexion(View view) {
    }

    public void write(View view) {
    }

    public void read(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("db",Integer.parseInt(dataBlock.getText().toString()));
        editor.commit();
        startActivity( new Intent(this, ReadComprimeActivity.class));
    }
}
