package com.example.hongjiayong.lifeisshort;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hongjiayong.lifeisshort.fragments.BooksFragment;
import com.example.hongjiayong.lifeisshort.fragments.TagBooksFragment;
import com.example.hongjiayong.lifeisshort.fragments.TagFragment;

import java.util.List;

/**
 * Created by hongjiayong on 2016/10/8.
 */

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder>{

    private List<Tag> mTags;
    private Context mContext;
    private FragmentManager fragmentmanager;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tag_name);
        }
    }

    public TagAdapter(Context context, List<Tag> tags, FragmentManager fragmentmanager){
        mContext = context;
        mTags = tags;
        this.fragmentmanager = fragmentmanager;
    }

    private Context getContext() {
        return mContext;
    }


    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_tag, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TagAdapter.ViewHolder holder, final int position) {
        // Get the data model based on position
        final Tag tag = mTags.get(position);

        // Set item views based on your views and data model
        holder.name.setText(tag.getTagName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = (Fragment) TagBooksFragment.newInstence(tag.getTagName());
                fragmentmanager.beginTransaction().replace(R.id.flContent, fragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }
}
