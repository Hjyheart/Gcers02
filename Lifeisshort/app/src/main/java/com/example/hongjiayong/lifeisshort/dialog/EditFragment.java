package com.example.hongjiayong.lifeisshort.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.SuccessActivity;
import com.example.hongjiayong.lifeisshort.fragments.BooksFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends DialogFragment {

    private EditText name;
    private EditText author;
    private EditText publisher;
    private EditText tag;
    private EditText des;
    private RadioButton stateR;
    private RadioButton stateO;
    private RadioButton stateL;
    private Button submit;

    private static String bookName;

    private static String urlPublic = new String("http://www.hjyheart.com/editBook?name=");


    public EditFragment() {
        // Required empty public constructor
    }

    public static EditFragment newInstance(String name){
        EditFragment frag = new EditFragment();
        Bundle args = new Bundle();
        args.putString("bookName", name);
        frag.setArguments(args);
        bookName = name;

        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        name = (EditText) view.findViewById(R.id.edit_book_name);
        author = (EditText) view.findViewById(R.id.edit_book_author);
        publisher = (EditText) view.findViewById(R.id.edit_book_publisher);
        tag = (EditText) view.findViewById(R.id.edit_book_tag);
        des = (EditText) view.findViewById(R.id.edit_book_des);
        stateR = (RadioButton) view.findViewById(R.id.edit_book_reading);
        stateO = (RadioButton) view.findViewById(R.id.edit_book_onshelf);
        stateL = (RadioButton) view.findViewById(R.id.edit_book_lend);
        submit = (Button) view.findViewById(R.id.edit_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("")){
                    sendHttp(view, "name", "newName", name.getText().toString());
                }
                if (!author.getText().toString().equals("")){
                    sendHttp(view, "author", "author", author.getText().toString());
                }
                if (!tag.getText().toString().equals("")){
                    sendHttp(view, "tag", "tag", tag.getText().toString());
                }
                if (!publisher.getText().toString().equals("")){
                    sendHttp(view, "publisher", "publisher", publisher.getText().toString());
                }
                if (!des.getText().toString().equals("")){
                    sendHttp(view, "des", "des", des.getText().toString());
                }
                if (stateR.isChecked()){
                    sendHttp(view, "state", "state", "Reading");
                }
                if (stateO.isChecked()){
                    sendHttp(view, "state", "state", "On_Bookshelf");
                }
                if (stateL.isChecked()){
                    sendHttp(view, "state", "state", "Lend");
                }
                dismiss();

                Intent intent = new Intent(getContext(), SuccessActivity.class);
                startActivity(intent);
            }
        });


        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    private void sendHttp(final View v, String id, String target, String newContent){
        OkHttpClient client = new OkHttpClient();
        String url = urlPublic + bookName + "&id=" + id + "&" + target +  "=" + newContent;
        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.e("url_edit", url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Snackbar.make(v, "网络未知错误", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                if (responseData.equals("true")){
                    Snackbar.make(v, "修改成功", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
