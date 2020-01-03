package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projetandroid.Comprime.ReadComprimeActivity;
import com.example.projetandroid.Cuve.ReadCuveActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InfoActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private ReadTaskS7 readS7;
    private TextView num;
    private TextView version;
    private TextView statut;
    private TextView description;
    private LinearLayout comprime;
    private LinearLayout cuve;
    private TextView error;
    private TextView type;
    private TextView serialNumber;
    private TextView moduleName;

    private EditText dataBlock;
    private TextView nbrCPB;
    private TextView nbrBouteille;

    private ImageView read;
    private ImageView write;

    private ProgressBar niveau;
    private TextView pourcent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
        num = (TextView) findViewById(R.id.txv_name) ;
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);
        version = (TextView) findViewById(R.id.txv_version);
        statut = (TextView)  findViewById(R.id.txv_status);
        description = (TextView) findViewById(R.id.txv_description);
        dataBlock = (EditText) findViewById(R.id.edt_dtblck);
        nbrCPB = (TextView) findViewById(R.id.txv_nbrCPB);
        nbrBouteille = (TextView) findViewById(R.id.txv_nbrB);
        type = (TextView) findViewById(R.id.type);
        moduleName = (TextView) findViewById(R.id.module_name);
        serialNumber = (TextView) findViewById(R.id.serial_number);
        error = (TextView) findViewById(R.id.error);
        comprime = findViewById(R.id.layout_comprime);
        cuve = findViewById(R.id.layout_cuve);
        niveau = findViewById(R.id.niv_liquide);
        pourcent = findViewById(R.id.pourcent);

        this.configureBottomView();


        sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        description.setText(sharedpreferences.getString("nav",null));
        if(sharedpreferences.getString("nav",null) == "Comprimé"){
            cuve.setVisibility(View.GONE);
            readS7 = new ReadTaskS7(this.findViewById(android.R.id.content),type,moduleName,serialNumber , num,version,statut,nbrCPB,nbrBouteille,/*Integer.parseInt(dataBlock.getText().toString().trim())*/0);
        }
        else{
            comprime.setVisibility(View.GONE);
            readS7 = new ReadTaskS7(this.findViewById(android.R.id.content) ,type,moduleName,serialNumber  , num,version,statut,/*Integer.parseInt(dataBlock.getText().toString().trim())*/0,niveau,pourcent);
        }

        sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(!sharedpreferences.getString("role",null).equals("ADMIN")) {
                this.bottomNavigationView.getMenu().findItem(R.id.btn_write).setVisible(false);
        }

        dataBlock.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!dataBlock.getText().toString().trim().equals("")) {
                    readS7.setDbNumber(Integer.parseInt(dataBlock.getText().toString().trim()));
                }
                error.setText("");
            }
        });
    }

    private void configureBottomView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return InfoActivity.this.updateMainFragment(item.getItemId());
            }
        });
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer) {
            case R.id.btn_read:
                this.read();
                break;
            case R.id.btn_deco:
                this.deconnexion();
                break;
            case R.id.btn_write:
                this.write();
                break;
            case R.id.btn_web:
                this.web();
                break;
        }
        return true;
    }

    public void deconnexion() {

        readS7.Stop();
        finish();
    }

    public void write() {
        if(dataBlock.getText().toString().equals("")){
            error.setText("data block ne peut etre vide");
        }
        else {
            SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("db", Integer.parseInt(dataBlock.getText().toString().trim()));
            editor.commit();
            startActivity(new Intent(InfoActivity.this, WriteActivity.class));

        }
    }

    public void web(){
        startActivity(new Intent(this, WebActivity.class));

    }

    public void read() {

        if(dataBlock.getText().toString().equals("")){
            error.setText("data block ne peut etre vide");
        }
        else {
            SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("db", Integer.parseInt(dataBlock.getText().toString()));
            editor.commit();
            sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
            description.setText(sharedpreferences.getString("nav", null));
            if (sharedpreferences.getString("nav", null) == "Comprimé") {
                startActivity(new Intent(InfoActivity.this, ReadComprimeActivity.class));
            } else {
                startActivity(new Intent(InfoActivity.this, ReadCuveActivity.class));
            }

        }
    }

    /*@Override
    protected void onRestart() {

        super.onRestart();
        System.out.println("debut activiter");
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();


        sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        System.out.println("voir activiter");


    }*/

   /* @Override
    protected void onStart() {
        System.out.println("voir activiter");
        super.onStart();
    }*/
}
