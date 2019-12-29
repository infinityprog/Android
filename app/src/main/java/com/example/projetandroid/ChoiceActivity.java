package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projetandroid.Cuve.ReadCuveActivity;
import com.example.projetandroid.Fragment.AddUserFragment;
import com.example.projetandroid.Fragment.ChoiceFragment;
import com.example.projetandroid.Fragment.ListUsersFragment;
import com.example.projetandroid.Fragment.MenuFragment;
import com.example.projetandroid.Fragment.ProfilFragment;
import com.example.projetandroid.Fragment.UpdatePasswordFragment;
import com.google.android.material.navigation.NavigationView;

public class ChoiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private Toolbar toolbar;
    private DrawerLayout  drawerLayout;
    private NavigationView navigationView;
    private TextView title;
    private TextView name;

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

    public static final int OPEN_NEW_ACTIVITY = 10;



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

    @Override
    protected void onRestart() {
        super.onRestart();
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
                this.showFragment(FRAGMENT_ACCUEIL);
                break;
            case R.id.add_user:
                this.showFragment(FRAGMENT_ADDUSER);
                break;
            case R.id.update_user:
                this.showFragment(FRAGMENT_UPDATEUSER);
                break;
            case R.id.profil :
                this.showFragment(FRAGMENT_PROFIL);
                break;
            case R.id.update_password:
                this.showFragment(FRAGMENT_UPDATEPASSWORD);
                break;
            case R.id.logout:
                this.logOut();
                finish();
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
        View view = navigationView.getHeaderView(0);
        name = view.findViewById(R.id.name);
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("role",null).equals("BASIC")){
           Menu menu = navigationView.getMenu();
            MenuItem item = menu.findItem(R.id.users);
            item.setVisible(false);
        }
        name.setText(sharedpreferences.getString("name",null) + " " + sharedpreferences.getString("lastName",null) );
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void showFragment(int fragmentIdentifier){
        switch (fragmentIdentifier){
            case FRAGMENT_ACCUEIL :
                this.showAcceuil();
                break;
            case FRAGMENT_ADDUSER:
                this.showAddUser();
                break;
            case FRAGMENT_UPDATEUSER:
                this.showAllUser();
                break;
            case FRAGMENT_PROFIL :
                this.showProfil();
                break;
            case FRAGMENT_UPDATEPASSWORD:
                this.showUpdatePassword();
                break;
            default:
                break;
        }
    }

    private void showAcceuil(){
        if (this.accueil == null) this.accueil = ChoiceFragment.newInstance();
        this.startTransactionFragment(this.accueil);
    }

    private void showAddUser(){
        if (this.addUser == null) this.addUser = AddUserFragment.newInstance();
        this.startTransactionFragment(this.addUser);
        title.setText("Ajouter un utilisateur");
    }

    private void showAllUser(){
        if (this.updateUser == null) this.updateUser = ListUsersFragment.newInstance();
        this.startTransactionFragment(this.updateUser);
        title.setText("Liste des utilisateurs");
    }

    private void showProfil(){
        if (this.profil == null) this.profil = ProfilFragment.newInstance();
        ((ProfilFragment)this.profil).setNav(navigationView);
        this.startTransactionFragment(this.profil);
        title.setText("Modifier mes information");
    }

    private void showUpdatePassword(){
        if (this.updatePassword == null) this.updatePassword = UpdatePasswordFragment.newInstance();
        this.startTransactionFragment(this.updatePassword);
        title.setText("Modifier votre mot de passe");
    }


    private void startTransactionFragment(Fragment fragment){
        if (!fragment.isVisible()){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }



    private void logOut(){
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        sharedpreferences.edit().clear().commit();
        startActivity(new Intent(this, LoginActivity.class));
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
