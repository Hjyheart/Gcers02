package com.example.hongjiayong.lifeisshort.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.dialog.EditFragment;

import org.w3c.dom.Text;

import java.security.PublicKey;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContentFragment extends Fragment{

    private TextView nameView;
    private TextView authorView;
    private TextView publisherView;
    private TextView descriptionView;
    private TextView tagView;
    private ImageView coverView;
    private FloatingActionButton fab;

    private static String name;
    private static String description;
    private static String author;
    private static String publisher;
    private static String tag;
    private static int cover;

    public ContentFragment() {
        // Required empty public constructor
    }

    public static ContentFragment newInstance(String Name, String Author, String Publisher, String Description, String Tag, int Cover){
        ContentFragment c = new ContentFragment();
        name = Name;
        description = Description;
        cover = Cover;
        author = Author;
        publisher = Publisher;
        tag = Tag;

        return c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        nameView = (TextView) view.findViewById(R.id.content_title);
        authorView = (TextView) view.findViewById(R.id.content_author);
        publisherView = (TextView) view.findViewById(R.id.content_publisher);
        descriptionView = (TextView) view.findViewById(R.id.content_description);
        coverView = (ImageView) view.findViewById(R.id.content_backdrop);
        tagView = (TextView) view.findViewById(R.id.content_tag);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        tagView.setText(tag);
        nameView.setText(name);
        authorView.setText(author);
        publisherView.setText(publisher);
        descriptionView.setText(description);
        coverView.setImageResource(cover);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
            }
        });

        return view;
    }

    public void showEditDialog(){
        FragmentManager fm = getFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(name);
        editFragment.setTargetFragment(ContentFragment.this, 300);
        editFragment.show(fm, "framgment_edit");
    }
}
