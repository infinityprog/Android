package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText oldPassword;
    private EditText password;
    private EditText password2;
    private TextView validation;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        this.oldPassword = (EditText) findViewById(R.id.edt_old_password);
        this.oldPassword.setVisibility(View.GONE);
        TextView labelPassword =  findViewById(R.id.txv_old_password);
        labelPassword.setVisibility(View.GONE);
        this.password = (EditText) findViewById(R.id.edt_password);
        this.password2 = (EditText) findViewById(R.id.edt_passwordC);
        this.validation = (TextView) findViewById(R.id.validation);
        userRepository = new UserRepository(this);
    }

    public void update(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        ArrayList<String> error = new ArrayList<>();

        Pattern pattern;
        final String PASSWORD = ".{4,}";

        pattern = Pattern.compile(PASSWORD);

        if ( password.getText().toString().matches("")){
            error.add("il manque le nouveau mot de passe \n");
        }
        else if (!pattern.matcher(password.getText().toString()).matches()){
            error.add("Le nouveau mot de passe doit avoir ou moin 4 charactères \n");
        }
        if ( password2.getText().toString().matches("")){
            error.add("Vous avez oublié de confirmer le mot de passe \n");
        }
        if ( password.getText().toString().equals(password.getText())){
            error.add("Les mots de passes ne sont pas les même \n");
        }

        if(error.isEmpty()){
            User user = new User();
            SharedPreferences sharedpreferences = getSharedPreferences("update", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            user.setPassword(password.getText().toString().trim());
            userRepository.open();
            userRepository.updatePassword(sharedpreferences.getInt("id",-1),user.getPassword());
            validation.setText("Le mot de passe à bien été modifié");
            userRepository.close();
        }
        else {
            String e = "" ;
            for (String r : error){
                e += r;
            }
            validation.setText(e);
        }
    }
}
