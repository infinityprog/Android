package com.example.projetandroid.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projetandroid.ChoiceActivity;
import com.example.projetandroid.HomeActivity;
import com.example.projetandroid.R;
import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChoiceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoiceFragment extends Fragment  {

    private Context context;
    private Button comprime;
    private Button cuve;

    private OnFragmentInteractionListener mListener;

    public ChoiceFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment ChoiceFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChoiceFragment newInstance() {
        ChoiceFragment fragment = new ChoiceFragment();
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
        View view =  inflater.inflate(R.layout.fragment_choice, container, false);
        comprime = view.findViewById(R.id.btn_comprime);
        cuve = view.findViewById(R.id.btn_cuve);

        comprime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToComprime();
            }
        });

        cuve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navToCuve();
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

    private void navToComprime() {
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","Comprimé");
        editor.commit();
        startActivity( new Intent(getContext(), HomeActivity.class));

    }

    private void navToCuve() {
        SharedPreferences sharedpreferences = getContext().getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","cuve");
        editor.commit();
        startActivity( new Intent(getContext(), HomeActivity.class));

    }

    /*public void navToUniversel(View view) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("navigation", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("nav","universel");
        editor.commit();
        startActivity( new Intent(getContext(), HomeActivity.class));

    }*/
}
