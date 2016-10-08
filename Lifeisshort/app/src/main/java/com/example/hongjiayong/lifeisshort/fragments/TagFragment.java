package com.example.hongjiayong.lifeisshort.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hongjiayong.lifeisshort.BooksAdapter;
import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.Tag;
import com.example.hongjiayong.lifeisshort.TagAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TagFragment extends Fragment{

    private RecyclerView recyclerView;
    private ArrayList<Tag> tags;
    private TagAdapter adapter;



    public TagFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_tag, container, false);

        tags = new ArrayList<Tag>();
        tags.add(new Tag("test"));
        tags.add(new Tag("test1"));

        recyclerView = (RecyclerView) view.findViewById(R.id.rvTags);
        adapter = new TagAdapter(this.getContext(), tags, getFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

}
