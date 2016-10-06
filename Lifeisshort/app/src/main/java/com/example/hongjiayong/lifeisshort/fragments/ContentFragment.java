package com.example.hongjiayong.lifeisshort.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hongjiayong.lifeisshort.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment {
    
    private TextView nameView;
    private TextView descriptionView;
    private ImageView coverView;

    private static String name;
    private static String description;
    private static int cover;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(String Name, String Description, int Cover){
        ContentFragment c = new ContentFragment();
        name = Name;
        description = Description;
        cover = Cover;

        return c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        nameView = (TextView) view.findViewById(R.id.content_title);
        descriptionView = (TextView) view.findViewById(R.id.content_description);
        coverView = (ImageView) view.findViewById(R.id.content_backdrop);

        nameView.setText(name);
        descriptionView.setText(description);
        coverView.setImageResource(cover);



        return view;
    }

}
