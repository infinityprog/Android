package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private TextView validation;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userRepository = new UserRepository(this);
        this.login = (EditText)  findViewById(R.id.edt_login);
        this.password = (EditText) findViewById(R.id.edt_password);
        this.validation = (TextView) findViewById(R.id.validation);
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("login",null) != null){
            startActivity( new Intent(this, ChoiceActivity.class));
            finish();
        }
    }

    public void signIn(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        ArrayList<String> error = new ArrayList<>();

        if (this.login.getText().toString().matches("")){
            error.add("il manque l'eamil \n");
        }
        if ( this.password.getText().toString().matches("")){
            error.add("il manque le mot de passe \n");
        }


        if(error.isEmpty()){
            User user;
            userRepository.open();
            user = new User(login.getText().toString().trim(),password.getText().toString().trim());
            user = userRepository.login(user.getLogin(),user.getPassword());
            userRepository.close();
            if(user == null){
                error.add("Ce compte n'existe pas \n");
                String e = "" ;
                for (String r : error){
                    e += r;
                }
                this.validation.setText(e);
            }
            else {
                SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("id", user.getId());
                editor.putString("name", user.getName());
                editor.putString("lastName", user.getLastName());
                editor.putString("login", user.getLogin());
                editor.putString("role", user.getRole());
                editor.commit();
                startActivity( new Intent(this, ChoiceActivity.class));
                finish();
            }
        }
        else {
            String e = "" ;
            for (String r : error){
                e += r;
            }
            this.validation.setText(e);
        }
    }
}
