package com.example.projetandroid.Comprime;

import androidx.appcompat.app.AppCompatActivity;

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

public class WriteComprimeActivity extends AppCompatActivity {

    private TextView statut;
    private EditText address;
    private Spinner size;
    private LinearLayout linearLayoutPosition;
    private EditText position;
    private EditText valeur;
    private TextView txv_address;
    private TextView txv_valeur;


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

    public void valider(View view) {
    }
}
