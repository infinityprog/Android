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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.projetandroid.Db.UserRepository;
import com.example.projetandroid.Entity.Adaptater.CustomListAdapter;
import com.example.projetandroid.Entity.Adaptater.UsersAdaptater;
import com.example.projetandroid.Entity.User;
import com.example.projetandroid.InfoActivity;
import com.example.projetandroid.R;
import com.example.projetandroid.UpdateUsersActivity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListUsersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListUsersFragment extends Fragment {

    private UserRepository userRepository;
    private ArrayList<User> listUser;
    private OnFragmentInteractionListener mListener;

    public ListUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static ListUsersFragment newInstance() {
        ListUsersFragment fragment = new ListUsersFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_users, container, false);
        ListView users = view.findViewById(R.id.list_users);
        userRepository = new UserRepository(getContext());
        userRepository.open();
        try {
           listUser = userRepository.findAll();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        UsersAdaptater adapter = new UsersAdaptater(getContext(),listUser);
        userRepository.close();
        users.setAdapter(adapter);
        users.setOnItemClickListener(listview_listerner);
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    AdapterView.OnItemClickListener listview_listerner = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

            SharedPreferences sharedpreferences = getContext().getSharedPreferences("update", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("id", listUser.get(position).getId());
            editor.putString("name", listUser.get(position).getName());
            editor.putString("lastName", listUser.get(position).getLastName());
            editor.putString("login", listUser.get(position).getLogin());
            editor.putString("role", listUser.get(position).getRole());
            editor.commit();
            startActivity( new Intent(getActivity(), UpdateUsersActivity.class));
        }
    };
}
