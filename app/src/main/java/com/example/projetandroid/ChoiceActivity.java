package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projetandroid.Fragment.ChoiceFragment;
import com.example.projetandroid.Fragment.MenuFragment;
import com.google.android.material.navigation.NavigationView;

public class ChoiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private Toolbar toolbar;
    private DrawerLayout  drawerLayout;
    private NavigationView navigationView;
    private TextView title;

    //Fragments

    private Fragment accueil;
    private Fragment addUser;
    private Fragment updateUser;
    private Fragment profil;
    private Fragment updatePassword;


    private static final int FRAGMENT_ACCUEIL = 0;
    private static final int FRAGMENT_ADDUSER = 1;
    private static final int FRAGMENT_UPDATEUSER = 2;
    private static final int FRAGMENT_PROFIL = 3;
    private static final int FRAGMENT_UPDATEPASSWORD = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();

        title = findViewById(R.id.title);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.showFirstFragment();
    }




    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    public void navToUniversel(View view) {
        SharedPreferences sharedpreferences = getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","universel");
        editor.commit();
        startActivity( new Intent(this, HomeActivity.class));

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id){
            case R.id.accueil :
                System.out.println("accueil");
                break;
            case R.id.add_user:
                break;
            case R.id.update_user:
                break;
            case R.id.profil :
                break;
            case R.id.update_password:
                break;
            case R.id.logout:
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        this.toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_ACCUEIL :
                this.showAcceuil();
                break;
            case FRAGMENT_ADDUSER:
                //this.showProfileFragment();
                break;
            case FRAGMENT_UPDATEUSER:
                //this.showParamsFragment();
                break;
            case FRAGMENT_PROFIL :
                //this.showNewsFragment();
                break;
            case FRAGMENT_UPDATEPASSWORD:
                //this.showProfileFragment();
                break;
            default:
                break;
        }
    }

    private void showAcceuil(){
        if (this.accueil == null) this.accueil = ChoiceFragment.newInstance();
        this.startTransactionFragment(this.accueil);
    }
    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void showFirstFragment(){
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (visibleFragment == null){
            // 1.1 - Show News Fragment
            title.setText("Choisisez votre projet");
            this.showFragment(FRAGMENT_ACCUEIL);
            // 1.2 - Mark as selected the menu item corresponding to NewsFragment
            this.navigationView.getMenu().getItem(0).setChecked(true);
        }
    }

}
