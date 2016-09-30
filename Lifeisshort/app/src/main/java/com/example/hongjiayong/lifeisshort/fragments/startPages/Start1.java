package com.example.hongjiayong.lifeisshort.fragments.startPages;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.fragments.Register;
import com.example.hongjiayong.lifeisshort.tab.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class Start1 extends BaseFragment {


    private static final String DATA_NAME = "name";

    private String title = "Start1";

    public Start1() {
        // Required empty public constructor
    }

    public static Start1 newInstance(String title, int indicatorColor, int dividerColor){
        Start1 f = new Start1();
        f.setTitle(title);
        f.setIndicatorColor(indicatorColor);
        f.setDividerColor(dividerColor);

        //pass data
        Bundle args = new Bundle();
        args.putString(DATA_NAME, title);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start1, container, false);


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get data
        title = getArguments().getString(DATA_NAME);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
