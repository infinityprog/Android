package com.example.projetandroid.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.User;
import com.example.projetandroid.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdatePasswordFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdatePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePasswordFragment extends Fragment {

    private EditText oldPassword;
    private EditText password;
    private EditText password2;
    private TextView validation;
    private UserRepository userRepository;

    private OnFragmentInteractionListener mListener;

    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpdatePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePasswordFragment newInstance() {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
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
        View view =  inflater.inflate(R.layout.activity_update_password, container, false);

        this.oldPassword = (EditText) view.findViewById(R.id.edt_old_password);
        this.password = (EditText) view.findViewById(R.id.edt_password);
        this.password2 = (EditText) view.findViewById(R.id.edt_passwordC);
        this.validation = (TextView) view.findViewById(R.id.validation);
        TextView title = view.findViewById(R.id.title);
        title.setVisibility(View.GONE);
        userRepository = new UserRepository(getContext());

        Button update = view.findViewById(R.id.btn_update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> error = new ArrayList<>();

                Pattern pattern;
                final String PASSWORD = ".{4,}";

                pattern = Pattern.compile(PASSWORD);
                if ( oldPassword.getText().toString().matches("")){
                    error.add("il manque l'ancien mot de passe \n");
                }
                if ( password.getText().toString().matches("")){
                    error.add("il manque le nouveau mot de passe \n");
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
                    User user = new User();
                    SharedPreferences sharedpreferences = getContext().getSharedPreferences("session", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    try {
                        user.setPassword(oldPassword.getText().toString().trim());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    userRepository.open();
                    User v = null;
                    try {
                        v = userRepository.login(sharedpreferences.getString("login",null),user.getPassword());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    if(v == null){
                        validation.setText("Le mot de passe est incorrect");
                    }else {
                        try {
                            user.setPassword(password.getText().toString().trim());
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        userRepository.updatePassword(sharedpreferences.getInt("id",-1),user.getPassword());
                        validation.setText("Le mot de passe à bien été modifié");
                    }
                    userRepository.close();
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
