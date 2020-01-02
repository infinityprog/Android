package com.example.projetandroid;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class UpdateUsersActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText login;
    private TextView validation;
    private Spinner role;

    private UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_users);

        this.name = (EditText) findViewById(R.id.edt_name);
        this.login = (EditText) findViewById(R.id.edt_login);
        this.validation = (TextView) findViewById(R.id.validation);
        this.lastName = findViewById(R.id.edt_last_name);
        role = findViewById(R.id.role);
        userRepository = new UserRepository(this);

        SharedPreferences sharedpreferences = getSharedPreferences("update", Context.MODE_PRIVATE);
        this.name.setText(sharedpreferences.getString("name",null));
        this.lastName.setText(sharedpreferences.getString("lastName",null));
        this.login.setText(sharedpreferences.getString("login",null));
        if(sharedpreferences.getString("role",null).equals("BASIC")) {
            this.role.setSelection(0);
        }
        else {
            this.role.setSelection(1);
        }
    }

    public void update(View view) {

        ArrayList<String> error = new ArrayList<>();

        if(name.getText().toString().matches("")){
            error.add("il manque le nom \n");
        }
        if(lastName.getText().toString().matches("")){
            error.add("il manque le prénom \n");
        }
        if (login.getText().toString().matches("")){
            error.add("il manque le mail \n");
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login.getText().toString()).matches()){
            error.add("le mail n'est pas au bon format \n");
        }

        if(error.isEmpty()){
            User user = new User(name.getText().toString().trim(),lastName.getText().toString().trim(),login.getText().toString().trim(),role.getSelectedItem().toString());

            SharedPreferences sharedpreferences = getSharedPreferences("update", Context.MODE_PRIVATE);
            userRepository.open();
            userRepository.update(sharedpreferences.getInt("id",-1),user);
            User v = null;
            try {
                v = userRepository.findUser(login.getText().toString().trim());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            userRepository.close();
            validation.setText("Votre utilisateur " + v.getName() + " " + v.getLastName() + " à bien été modifier");
        }
        else {
            String e = "" ;
            for (String r : error){
                e += r;
            }
            validation.setText(e);
        }
    }

    @Override
    public void onBackPressed() {

        SharedPreferences sharedpreferences = getSharedPreferences("update", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
        finish();
    }


    public void updatePassword(View view) {
        startActivity(new Intent(this,UpdatePasswordActivity.class));
    }

    public void delete(View view) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Suppression");
        builder1.setMessage("Etes vous sur de vouloir supprimer le compte ?");

        builder1.setPositiveButton(
                "Oui",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences sharedpreferences = getSharedPreferences("update", Context.MODE_PRIVATE);
                        userRepository.open();
                        userRepository.delete(sharedpreferences.getInt("id",-1));
                        userRepository.close();
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "Non",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
