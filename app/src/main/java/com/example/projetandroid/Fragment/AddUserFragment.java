package com.example.projetandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;
import com.example.projetandroid.LoginActivity;
import com.example.projetandroid.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserFragment extends Fragment {

    private EditText name;
    private EditText lastName;
    private EditText login;
    private EditText password;
    private EditText password2;
    private TextView validation;
    private Button add;
    private Spinner role;
    private UserRepository userRepository;

    private OnFragmentInteractionListener mListener;

    public AddUserFragment() {
        // Required empty public constructor
    }

    public static AddUserFragment newInstance() {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_user, container, false);
        this.name = (EditText) view.findViewById(R.id.edt_name);
        this.login = (EditText) view.findViewById(R.id.edt_login);
        this.password = (EditText) view.findViewById(R.id.edt_password);
        this.password2 = (EditText) view.findViewById(R.id.edt_passwordC);
        this.validation = (TextView) view.findViewById(R.id.validation);
        this.lastName = view.findViewById(R.id.edt_last_name);
        role = view.findViewById(R.id.role);
        add =  view.findViewById(R.id.btn_add);
        userRepository = new UserRepository(getContext());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> error = new ArrayList<>();

                Pattern pattern;
                final String PASSWORD = ".{4,}";
                pattern = Pattern.compile(PASSWORD);

                if(name.getText().toString().matches("")){
                    error.add("il manque le nom \n");
                }
                if(lastName.getText().toString().matches("")){
                    error.add("il manque le prénom \n");
                }
                if (login.getText().toString().matches("")){
                    error.add("il manque l'eamil \n");
                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(login.getText().toString().trim()).matches()){
                    error.add("le mail n'est pas au bon format \n");
                }
                if ( password.getText().toString().matches("")){
                    error.add("il manque le mot de passe \n");
                }
                else if (!pattern.matcher(password.getText().toString()).matches()){
                    error.add("Le nouveau mot de passe doit avoir ou moin 4 charactères \n");
                }
                if ( password2.getText().toString().matches("")){
                    error.add("Vous avez oublié de confirmer le mot de passe \n");
                }
                if ( password.getText().toString().equals(password.getText())){
                    error.add("Les mots de passes ne sont pas les même \n");
                }

                if(error.isEmpty()){
                    User user = null;
                    try {
                        user = new User(name.getText().toString().trim(),lastName.getText().toString().trim(),login.getText().toString().trim(),password.getText().toString().trim(),role.getSelectedItem().toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    userRepository.open();
                    userRepository.insert(user);
                    User v = null;
                    try {
                        v = userRepository.findUser(login.getText().toString().trim());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    userRepository.close();
                    validation.setText("Votre utilisateur " + v.getName() + " " + v.getLastName() + " à bien été créé");
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
}
