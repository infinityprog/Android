package com.example.projetandroid.Entity.Adaptater;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetandroid.Entity.Automate;
import com.example.projetandroid.R;

import java.util.ArrayList;
import java.util.LinkedList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class CustomListAdapter extends ArrayAdapter<Automate> {

    private final Context _context;
    private ArrayList<Automate> automates;

    public CustomListAdapter(Context context, ArrayList<Automate> automates) {
        super(context, R.layout.list_address_layout, automates);
        _context = context;
        this.automates = automates;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_address_layout, parent, false);
        } else {
            convertView = (LinearLayout) convertView;
        }


        TextView txt_description = (TextView) convertView.findViewById(R.id.txt_description);
        txt_description.setText(automates.get(position).getDescription());

        TextView txt_ip = (TextView) convertView.findViewById(R.id.txt_ip);
        txt_ip.setText(automates.get(position).getIp());

        TextView txt_slot = (TextView) convertView.findViewById(R.id.txt_slot);
        txt_slot.setText(String.valueOf(automates.get(position).getSlot()));

        TextView txt_rack = (TextView) convertView.findViewById(R.id.txt_rack);
        txt_rack.setText(String.valueOf(automates.get(position).getRack()));

        return convertView;
    }
}
