package com.example.projetandroid.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;
import com.example.projetandroid.LoginActivity;
import com.example.projetandroid.R;
import com.google.android.material.navigation.NavigationView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfilFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfilFragment extends Fragment {

    private EditText name;
    private EditText lastName;
    private EditText login;
    private TextView validation;
    private Spinner role;
    private NavigationView nav;

    private UserRepository userRepository;

    private OnFragmentInteractionListener mListener;

    public ProfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilFragment newInstance() {
        ProfilFragment fragment = new ProfilFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_update_users, container, false);
        this.name = (EditText) view.findViewById(R.id.edt_name);
        this.login = (EditText) view.findViewById(R.id.edt_login);
        this.validation = (TextView) view.findViewById(R.id.validation);
        this.lastName = view.findViewById(R.id.edt_last_name);
        role = view.findViewById(R.id.role);
        userRepository = new UserRepository(getContext());

        TextView title = view.findViewById(R.id.title);
        title.setVisibility(View.GONE);

        SharedPreferences sharedpreferences = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
        this.name.setText(sharedpreferences.getString("name",null));
        this.lastName.setText(sharedpreferences.getString("lastName",null));
        this.login.setText(sharedpreferences.getString("login",null));
        if(sharedpreferences.getString("role",null).equals("BASIC")) {
            this.role.setVisibility(View.INVISIBLE);
        }
        else {
            this.role.setSelection(1);
        }

        Button delete = view.findViewById(R.id.btn_delete);
        LinearLayout password = view.findViewById(R.id.btn_password);
        Button update = view.findViewById(R.id.btn_update);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setTitle("Suppression");
                builder1.setMessage("Etes vous sur de vouloir supprimer le compte ?");

                builder1.setPositiveButton(
                        "Oui",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences sharedpreferences = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                                userRepository.open();
                                userRepository.delete(sharedpreferences.getInt("id",-1));
                                userRepository.close();
                                sharedpreferences.edit().clear().commit();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                getActivity().finish();
                            }
                        });

                builder1.setNegativeButton(
                        "Non",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        password.setVisibility(View.GONE);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> error = new ArrayList<>();

                if(name.getText().toString().matches("")){
                    error.add("il manque le nom \n");
                }
                if(lastName.getText().toString().matches("")){
                    error.add("il manque le prénom \n");
                }
                if (login.getText().toString().matches("")){
                    error.add("il manque le mail \n");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login.getText().toString()).matches()){
                    error.add("le mail n'est pas au bon format \n");
                }

                if(error.isEmpty()){
                    SharedPreferences sharedpreferences = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                    User user = null;

                    if(sharedpreferences.getString("role",null).equals("BASIC")) {
                        user = new User(name.getText().toString().trim(),lastName.getText().toString().trim(),login.getText().toString().trim(),"BASIC");
                    }
                    else {
                        user = new User(name.getText().toString().trim(),lastName.getText().toString().trim(),login.getText().toString().trim(),role.getSelectedItem().toString());
                    }

                    userRepository.open();
                    userRepository.update(sharedpreferences.getInt("id",-1),user);
                    User v = null;
                    try {
                        v = userRepository.findUser(login.getText().toString().trim());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    userRepository.close();
                    validation.setText("Votre utilisateur " + v.getName() + " " + v.getLastName() + " à bien été modifier");
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("id", v.getId());
                    editor.putString("name", v.getName());
                    editor.putString("lastName", v.getLastName());
                    editor.putString("login", v.getLogin());
                    editor.putString("role", v.getRole());
                    editor.commit();

                    View viewNav = nav.getHeaderView(0);
                    TextView nameMenu = viewNav.findViewById(R.id.name);
                    if(sharedpreferences.getString("role",null).equals("BASIC")){
                        Menu menu = nav.getMenu();
                        MenuItem item = menu.findItem(R.id.users);
                        item.setVisible(false);
                    }
                    nameMenu.setText(sharedpreferences.getString("name",null) + " " + sharedpreferences.getString("lastName",null) );
                }
                else {
                    String e = "" ;
                    for (String r : error){
                        e += r;
                    }
                    validation.setText(e);
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setNav(NavigationView nav) {
        this.nav = nav;
    }
}
