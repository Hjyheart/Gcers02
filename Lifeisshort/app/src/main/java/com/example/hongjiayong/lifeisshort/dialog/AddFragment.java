package com.example.hongjiayong.lifeisshort.dialog;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.SuccessActivity;
import com.example.hongjiayong.lifeisshort.fragments.ContentFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    public static AddFragment newInstance(){
        AddFragment frag = new AddFragment();

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        name = (EditText) view.findViewById(R.id.add_book_name);
        author = (EditText) view.findViewById(R.id.add_book_author);
        publisher = (EditText) view.findViewById(R.id.add_book_publisher);
        tag = (EditText) view.findViewById(R.id.add_book_tag);
        des = (EditText) view.findViewById(R.id.add_book_des);
        stateR = (RadioButton) view.findViewById(R.id.add_book_reading);
        stateO = (RadioButton) view.findViewById(R.id.add_book_onshelf);
        stateL = (RadioButton) view.findViewById(R.id.add_book_lend);
        submit = (Button) view.findViewById(R.id.add_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "xiaowang");

                if (name.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (author.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入author", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (publisher.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入publisher", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (des.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tag.getText().toString().equals("")){
                    Toast.makeText(getContext(), "请输入tag", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(stateL.isChecked() || stateO.isChecked() || stateR.isChecked())){
                    Toast.makeText(getContext(), "请勾选状态", Toast.LENGTH_SHORT).show();
                    return;
                }

                dismiss();

                String state = new String();
                if (stateL.isChecked())
                    state = "Lend";
                else if (stateR.isChecked())
                    state = "Reading";
                else
                    state = "On_Bookshelf";


                OkHttpClient client = new OkHttpClient();
                String url = "http://www.hjyheart.com/addBooks?username=" +
                        username + "&name=" + name.getText().toString() +
                        "&author=" + author.getText().toString() + "&publisher=" +
                        publisher.getText().toString() + "&description=" +
                        des.getText().toString() + "&state=" + state +
                        "&tag=" + tag.getText().toString();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Log.e("add_url", url);

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Snackbar.make(view, "网络未知错误", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();

                        if (responseData.equals("true")){
                            Snackbar.make(view, "添加成功", Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            Snackbar.make(view, "添加失败", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

                Intent intent = new Intent(getContext(), SuccessActivity.class);
                startActivity(intent);
            }
        });

        getDialog().setTitle("Add Book");
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
