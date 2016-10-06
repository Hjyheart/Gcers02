package com.example.hongjiayong.lifeisshort.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.hongjiayong.lifeisshort.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends DialogFragment {

    private EditText name;
    private EditText author;
    private EditText publisher;
    private EditText tag;
    private EditText des;
    private RadioButton stateR;
    private RadioButton stateO;
    private RadioButton stateL;
    private Button submit;

    public AddFragment() {
        // Required empty public constructor
    }
    


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
