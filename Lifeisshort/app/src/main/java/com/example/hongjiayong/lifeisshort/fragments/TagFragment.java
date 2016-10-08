package com.example.hongjiayong.lifeisshort.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hongjiayong.lifeisshort.BooksAdapter;
import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.Tag;
import com.example.hongjiayong.lifeisshort.TagAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        recyclerView = (RecyclerView) view.findViewById(R.id.rvTags);
        adapter = new TagAdapter(this.getContext(), tags, getFragmentManager());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        prepareTags();

        return view;
    }

    private void prepareTags(){
        final int[] flag = {0};
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", "xiaowang");

        String url = "http://www.hjyheart.com/getTags?username=" + username;
        Log.e("url_getTags", url);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(getView(), "未知网络错误", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                    String responseData = response.body().string();
                    JSONArray jsonArray = new JSONArray(responseData);

                    for (int i = 0; i < jsonArray.length(); i++){
                        String tag = jsonArray.get(i).toString();
                        Log.e("tag", tag);
                        tags.add(new Tag(tag));
                    }
                }catch (JSONException e){

                }
                flag[0] = 1;
            }

        });

        while (flag[0] == 0){}
        adapter.notifyDataSetChanged();

    }

}
