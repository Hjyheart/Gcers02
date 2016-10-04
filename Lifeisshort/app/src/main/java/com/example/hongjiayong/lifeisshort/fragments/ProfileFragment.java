package com.example.hongjiayong.lifeisshort.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hongjiayong.lifeisshort.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private TextView username;
    private TextView name;
    private TextView sex;
    private TextView sign;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        username = (TextView) view.findViewById(R.id.profile_username);
        name = (TextView) view.findViewById(R.id.profile_name);
        sex = (TextView) view.findViewById(R.id.profile_sex);
        sign = (TextView) view.findViewById(R.id.profile_sign);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("username", "囧"));
        name.setText(sharedPreferences.getString("name", "囧"));
        if (sharedPreferences.getBoolean("sex", true)){
            sex.setText("male");
        }else{
            sex.setText("female");
        }
        sign.setText(sharedPreferences.getString("sign", "囧"));


        return view;
    }

}
