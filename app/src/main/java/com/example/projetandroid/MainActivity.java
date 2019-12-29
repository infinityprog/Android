package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Db.AutomateRepository;
import com.example.projetandroid.Db.Database;
import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText lastName;
    private EditText login;
    private EditText password;
    private EditText password2;
    private TextView validation;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.name = (EditText) findViewById(R.id.edt_name);
        this.login = (EditText) findViewById(R.id.edt_login);
        this.password = (EditText) findViewById(R.id.edt_password);
        this.password2 = (EditText) findViewById(R.id.edt_passwordC);
        this.validation = (TextView) findViewById(R.id.validation);
        this.lastName = findViewById(R.id.edt_last_name);
        userRepository = new UserRepository(this);
        userRepository.open();
        boolean admin = false;
        try {
            admin = userRepository.adminExist();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        userRepository.close();

        if(admin == true){
            startActivity( new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public void valid(View view) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ArrayList<String> error = new ArrayList<>();

        Pattern pattern;
        final String PASSWORD = ".{4,}";
        pattern = Pattern.compile(PASSWORD);

        if(this.name.getText().toString().matches("")){
            error.add("il manque le nom \n");
        }
        if(this.lastName.getText().toString().matches("")){
            error.add("il manque le prénom \n");
        }
        if (this.login.getText().toString().matches("")){
            error.add("il manque l'eamil \n");
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login.getText().toString().trim()).matches()){
            error.add("le mail n'est pas au bon format \n");
        }
        if ( this.password.getText().toString().matches("")){
            error.add("il manque le mot de passe \n");
        }
        else if (!pattern.matcher(password.getText().toString()).matches()){
            error.add("Le nouveau mot de passe doit avoir ou moin 4 charactères \n");
        }
        if ( this.password2.getText().toString().matches("")){
            error.add("Vous avez oublié de confirmer le mot de passe \n");
        }
        if ( this.password.getText().toString().equals(this.password.getText())){
            error.add("Les mots de passes ne sont pas les même \n");
        }

        if(error.isEmpty()){
            User user = new User(this.name.getText().toString().trim(),this.lastName.getText().toString().trim(),this.login.getText().toString().trim(),this.password.getText().toString().trim(),"ADMIN");
            userRepository.open();
            userRepository.insert(user);
            User v = userRepository.findUser(this.login.getText().toString().trim());
            userRepository.close();
            this.validation.setText("Votre utilisateur " + v.getName() + " " + v.getLastName() + " à bien été créé");
            startActivity( new Intent(this, LoginActivity.class));
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
