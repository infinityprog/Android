package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Entity.Automate;
import com.example.projetandroid.Entity.CustomListAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private EditText ip;
    private EditText slot;
    private EditText rack;
    private EditText description;
    private NetworkInfo network;
    private ListView list;

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
        ArrayList<Automate> automates = new ArrayList<>();
        automates.add(new Automate(1,"ev5","cuve","192.168.25.26",1,2,sharedpreferences.getInt("id",-1)));
        automates.add(new Automate(1,"ev5","cuve","192.168.25.56",1,2,sharedpreferences.getInt("id",-1)));


        CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), automates);
        this.list.setAdapter(adapter);
        this.list.setOnItemClickListener(listview_listerner);
    }

    public void save(View view) {
        if(network != null && network.isConnectedOrConnecting())
        {

        }
        else
        {
            Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
        }
    }

    public void connexion(View view) {
    }

    AdapterView.OnItemClickListener listview_listerner = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
             ip.setText(((TextView)view.findViewById(R.id.txt_ip)).getText());
             slot.setText(((TextView)view.findViewById(R.id.txt_slot)).getText());
             rack.setText(((TextView)view.findViewById(R.id.txt_rack)).getText());
             description.setText(((TextView)view.findViewById(R.id.txt_description)).getText());
        }
    };
}
