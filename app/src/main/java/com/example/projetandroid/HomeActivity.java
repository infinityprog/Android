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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.Db.AutomateRepository;
import com.example.projetandroid.Entity.Automate;
import com.example.projetandroid.Entity.Adaptater.CustomListAdapter;
import com.example.projetandroid.Fragment.MenuFragment;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

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
        MenuFragment menu = (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.menu);
        ((MenuFragment)menu).setTitle("Connectea vous");
        SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        this.ip = (EditText) findViewById(R.id.edt_ip);
        this.slot = (EditText) findViewById(R.id.edt_slot);
        this.rack = (EditText) findViewById(R.id.edt_rack);
        this.description = (EditText) findViewById(R.id.edt_description);
        this.list = (ListView) findViewById(R.id.lst_automate);
        automateRepository = new AutomateRepository(this);
        automates = new ArrayList<>();
        automates.add(new Automate(1,"ev5","cuve","192.168.25.26",1,2,sharedpreferences.getInt("id",-1)));
        automates.add(new Automate(1,"ev5","cuve","192.168.25.56",1,2,sharedpreferences.getInt("id",-1)));
        CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), automates);
        this.list.setAdapter(adapter);
        this.list.setOnItemClickListener(listview_listerner);
        connexStatus = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        network = connexStatus.getActiveNetworkInfo();
    }

    public void save(View view) throws InterruptedException {

        /*if(network != null && network.isConnectedOrConnecting())
        {
            readS7 = new ReadTaskS7(view);
            readS7.Start("192.168.10.130","0", "2");
            while (!readS7.isFinish())
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(readS7.getName() + "hugo");
        }
        else
        {
            Toast.makeText(this,"! Connexion réseau IMPOSSIBLE !",Toast.LENGTH_SHORT).show();
        }
        /*SharedPreferences sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);
        automateRepository.open();
        automateRepository.insert(new Automate("name",description.getText().toString(),ip.getText().toString(), Integer.parseInt(slot.getText().toString()),Integer.parseInt(rack.getText().toString()),sharedpreferences.getInt("id",-1)));
    */}

    public void connexion(View view) {
        if(network != null && network.isConnectedOrConnecting()) {
            SharedPreferences sharedpreferences = getSharedPreferences("automate", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("ip", ip.getText().toString());
            editor.putString("slot", slot.getText().toString());
            editor.putString("rack", rack.getText().toString());
            editor.putString("description", description.getText().toString());
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
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
             ip.setText(((TextView)view.findViewById(R.id.txt_ip)).getText());
             slot.setText(((TextView)view.findViewById(R.id.txt_slot)).getText());
             rack.setText(((TextView)view.findViewById(R.id.txt_rack)).getText());
             description.setText(((TextView)view.findViewById(R.id.txt_description)).getText());
        }
    };
}
