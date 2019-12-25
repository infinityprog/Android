package com.example.projetandroid.Comprime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetandroid.R;
import com.example.projetandroid.ReadTaskS7;
import com.example.projetandroid.WriteTaskS7;

public class WriteComprimeActivity extends AppCompatActivity {

    private TextView statut;
    private EditText address;
    private Spinner size;
    private LinearLayout linearLayoutPosition;
    private EditText position;
    private EditText valeur;
    private TextView txv_address;
    private TextView txv_valeur;
    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comprime);

        statut =  findViewById(R.id.txv_status);
        address = findViewById(R.id.edt_address);
        size = findViewById(R.id.cbx_size);
        linearLayoutPosition = findViewById(R.id.lil_position);
        position = findViewById(R.id.edt_position);
        valeur = findViewById(R.id.edt_valeur);
        txv_address = findViewById(R.id.txv_address);
        txv_valeur =  findViewById(R.id.txv_valeur);
        SharedPreferences sharedpreferences = getSharedPreferences("datablock", Context.MODE_PRIVATE);
        int db = sharedpreferences.getInt("db",-1);
        readS7 = new ReadTaskS7(this.findViewById(android.R.id.content) , db,address,statut,size,position,txv_valeur);
        sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
        readS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeS7 = new WriteTaskS7(db,address);
        writeS7.Start(sharedpreferences.getString("ip",null), sharedpreferences.getString("rack",null), sharedpreferences.getString("slot",null));

        address.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (size.getSelectedItem().equals("Bit")){
                    txv_address.setText(address.getText() + "." + position.getText());
                }
                else {
                    txv_address.setText(address.getText());
                }
            }
        });

        position.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


                    txv_address.setText(address.getText() + "." + position.getText());

            }
        });

        size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //Choix étage
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                if (size.getSelectedItem().equals("Bit")){
                    linearLayoutPosition.setVisibility(View.VISIBLE);
                    txv_address.setText(address.getText() + "." + position.getText());

                }
                else {
                    linearLayoutPosition.setVisibility(View.GONE);
                    txv_address.setText(address.getText());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


    }

    public void valider(View view) throws InterruptedException {

        if (size.getSelectedItem().equals("Bit")){

           /*boolean result;
            if(valeur.getText().toString().equals("1")){
                result = true;
                System.out.println(result);
            }
            else {
                result = false;
            }
            writeS7.setWriteBool(Integer.parseInt(position.getText().toString()), result);*/
            double v = Double.valueOf(position.getText().toString());
            int pos = (int) Math.pow(2,v);
            System.out.println("valeur exposant : " + pos);
            writeS7.setWriteBool(pos,Integer.parseInt(valeur.getText().toString()));

        }
        else {
            writeS7.setWriteWord(Integer.parseInt(valeur.getText().toString()));
        }
    }
}
