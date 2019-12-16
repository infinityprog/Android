package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class InfoActivity extends AppCompatActivity {

    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;
    private TextView num;
    private TextView version;
    private TextView statut;
    private TextView description;

    private EditText dataBlock;
    private Spinner size;
    private EditText address;

    //byte
    private ListView lst_byte;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        num = (TextView) findViewById(R.id.txv_name) ;
        version = (TextView) findViewById(R.id.txv_version);
        statut = (TextView)  findViewById(R.id.txv_status);
        description = (TextView) findViewById(R.id.txv_description);
        dataBlock = (EditText) findViewById(R.id.edt_dtblck);
        size = (Spinner) findViewById(R.id.cbx_size);
        address = (EditText) findViewById(R.id.edt_address);
        lst_byte = (ListView) findViewById(R.id.ltv_byte);
        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                readS7.setSize(size.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        address.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                // you can call or do what you want with your EditText here

                // yourEditText...
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(address.getText().toString().equals("")){

                }
                else {
                    readS7.setAddress(Integer.valueOf(address.getText().toString().trim()));
                }
            }
        });
        readS7 = new ReadTaskS7(this.findViewById(android.R.id.content) , num,version,statut,lst_byte,Integer.parseInt(address.getText().toString()),size.getSelectedItem().toString(),Integer.parseInt(dataBlock.getText().toString()),getApplicationContext());
        SharedPreferences sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        description.setText(sharedpreferences.getString("description",null));
        if(sharedpreferences.getString("role",null) == "ADMIN") {
            writeS7 = new WriteTaskS7();
            writeS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        }
    }

    public void deconnexion(View view) {
    }

    public void write(View view) {
    }

}
