package com.example.projetandroid.Entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projetandroid.R;

import java.util.ArrayList;

public class BitAdaptater extends ArrayAdapter<Integer> {

    private final Context _context;
    private ArrayList<Integer> bits;
    private String valeur;

    public BitAdaptater(Context context, ArrayList<Integer> bits, String valeur) {
        super(context, R.layout.list_address_layout, bits);
        _context = context;
        this.bits = bits;
        this.valeur = valeur;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.bite_layout, parent, false);
        } else {
            convertView = (LinearLayout) convertView;
        }

        TextView txv_nb = (TextView) convertView.findViewById(R.id.txv_nb);
        txv_nb.setText(valeur + String.valueOf(position));

        TextView txv_vb = (TextView) convertView.findViewById(R.id.txv_vb);
        txv_vb.setText(bits.get(position).toString());

        System.out.println("valeur bit : " + bits.get(position).toString());

        return convertView;
    }
}
