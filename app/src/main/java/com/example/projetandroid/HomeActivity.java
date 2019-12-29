package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Db.AutomateRepository;
import com.example.projetandroid.Entity.Adaptater.BitAdaptater;
import com.example.projetandroid.Entity.Automate;
import com.example.projetandroid.Entity.Adaptater.CustomListAdapter;
import com.example.projetandroid.Fragment.MenuFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private int id = -1;
    private EditText ip;
    private EditText slot;
    private EditText rack;
    private EditText description;
    private ReadTaskS7 readS7;
    private WriteTaskS7 writeS7;
    private int idAutomate = 0;
    private ArrayList<Automate> automates;
    private NetworkInfo network;
    private ConnectivityManager connexStatus;
    private ListView list;
    private String test = null;
    private AutomateRepository automateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        this.ip = (EditText) findViewById(R.id.edt_ip);
        this.slot = (EditText) findViewById(R.id.edt_slot);
        this.rack = (EditText) findViewById(R.id.edt_rack);
        this.description = (EditText) findViewById(R.id.edt_description);
        this.list = (ListView) findViewById(R.id.lst_automate);
        automateRepository = new AutomateRepository(this);
        automates = new ArrayList<>();
        automateRepository.open();
        automates = automateRepository.findAll(sharedpreferences.getInt("id", 0));
        automateRepository.close();
        if(automates != null) {
            CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), automates);
            this.list.setAdapter(adapter);
        }
        this.list.setOnItemClickListener(listview_listerner);
        connexStatus = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
    }

    public void save(View view) {

        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        automateRepository.open();
        if (id == -1) {
            automateRepository.insert(new Automate(description.getText().toString().trim(), ip.getText().toString().trim(), Integer.parseInt(slot.getText().toString().trim()), Integer.parseInt(rack.getText().toString().trim()), sharedpreferences.getInt("id", -1)));
            id = automateRepository.findLast();
        }
        else {
            automateRepository.update(id,new Automate(description.getText().toString().trim(), ip.getText().toString().trim(), Integer.parseInt(slot.getText().toString().trim()), Integer.parseInt(rack.getText().toString().trim()), sharedpreferences.getInt("id", -1)));
        }
        automates = automateRepository.findAll(sharedpreferences.getInt("id", 0));
        automateRepository.close();
        System.out.println("id " + sharedpreferences.getInt("id", 0));
        CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), automates);
        this.list.setAdapter(adapter);
        this.list.setVisibility(View.VISIBLE);
    }

    public void connexion(View view) {
        if(network != null && network.isConnectedOrConnecting()) {
            SharedPreferences sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("ip", ip.getText().toString().trim());
            editor.putString("slot", slot.getText().toString().trim());
            editor.putString("rack", rack.getText().toString().trim());
            editor.putString("description", description.getText().toString().trim());
            editor.commit();
            startActivity( new Intent(this, InfoActivity.class));
        }

        else
        {
            Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
        }

    }

    AdapterView.OnItemClickListener listview_listerner = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long ids) {
             ip.setText(((TextView)view.findViewById(R.id.txt_ip)).getText());
             slot.setText(((TextView)view.findViewById(R.id.txt_slot)).getText());
             rack.setText(((TextView)view.findViewById(R.id.txt_rack)).getText());
             description.setText(((TextView)view.findViewById(R.id.txt_description)).getText());
             id = automates.get(position).getId();
        }
    };

    public void delete(View view) {

        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        automateRepository.open();
        automateRepository.delete(id);
        automates = automateRepository.findAll(sharedpreferences.getInt("id", -1));
        automateRepository.close();

        if (automates != null) {
            CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), automates);
            this.list.setAdapter(adapter);
        }else {
           this.list.setVisibility(View.INVISIBLE);
        }

        clear(view);

    }

    public void clear(View view) {
        ip.setText("");
        slot.setText("");
        rack.setText("");
        description.setText("");
        id = -1;
    }
}
