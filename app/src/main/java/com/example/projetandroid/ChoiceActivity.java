package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class ChoiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
    }

    public void navToComprime(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","Comprimé");
        editor.commit();
        startActivity( new Intent(this, HomeActivity.class));

    }

    public void navToCuve(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","cuve");
        editor.commit();
        startActivity( new Intent(this, HomeActivity.class));

    }

    public void navToUniversel(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","universel");
        editor.commit();
        startActivity( new Intent(this, HomeActivity.class));

    }
}
