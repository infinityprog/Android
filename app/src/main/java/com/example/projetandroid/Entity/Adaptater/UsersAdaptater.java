package com.example.projetandroid.Entity.Adaptater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.projetandroid.Entity.User;
import com.example.projetandroid.R;

import java.util.ArrayList;

public class UsersAdaptater extends ArrayAdapter<User> {

    private final Context context;
    private ArrayList<User> users;

    public UsersAdaptater(@NonNull Context context, ArrayList<User> users) {
        super(context, R.layout.list_users_layout,users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_users_layout, parent, false);
        } else {
            convertView = (LinearLayout) convertView;
        }

        TextView txt_id = convertView.findViewById(R.id.txt_id);
        txt_id.setText(String.valueOf(users.get(position).getId()));

        TextView txt_name = (TextView) convertView.findViewById(R.id.txt_name);
        txt_name.setText(users.get(position).getName() + " " +users.get(position).getLastName() );

        TextView login = (TextView) convertView.findViewById(R.id.txt_login);
        login.setText(users.get(position).getLogin());

        TextView role = (TextView) convertView.findViewById(R.id.txt_role);
        role.setText(users.get(position).getRole());
        if(users.get(position).getRole().equals("ADMIN")) {
            role.setTextColor(Color.parseColor("#3f6ad8"));
        }
        else {
            role.setTextColor(Color.parseColor("#0ba360"));
        }

        return convertView;
    }
}
