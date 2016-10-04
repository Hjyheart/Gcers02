package com.example.hongjiayong.lifeisshort.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.hongjiayong.lifeisshort.MainActivity;
import com.example.hongjiayong.lifeisshort.R;
import com.example.hongjiayong.lifeisshort.tab.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends BaseFragment {
    private static final String DATA_NAME = "name";

    private String title = "Register";

    private EditText username;
    private EditText name;
    private EditText password;
    private EditText confirmPassword;
    private EditText sign;
    private RadioButton male;
    private RadioButton female;
    private Button submit;

    public Register() {
        // Required empty public constructor
    }

    public static Register newInstance(String title, int indicatorColor,int dividerColor){
        Register f = new Register();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        username = (EditText) view.findViewById(R.id.register_username);
        name = (EditText) view.findViewById(R.id.register_name);
        password = (EditText) view.findViewById(R.id.register_password);
        confirmPassword = (EditText) view.findViewById(R.id.register_confirm_password);
        sign = (EditText) view.findViewById(R.id.register_sign);
        male = (RadioButton) view.findViewById(R.id.male_button);
        female = (RadioButton) view.findViewById(R.id.female_button);
        submit = (Button) view.findViewById(R.id.register_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    Snackbar.make(getView(), "请输入username", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (name.getText().toString().equals("")){
                    Snackbar.make(getView(), "请输入name", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals("")){
                    Snackbar.make(getView(), "请输入password", Snackbar.LENGTH_SHORT).show();
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Snackbar.make(getView(), "两次密码不相同", Snackbar.LENGTH_SHORT).show();
                }


                String sex = new String("");
                if (male.isChecked())
                    sex = "True";
                else
                    sex = "False";

                // start register
                OkHttpClient client = new OkHttpClient();
                String url = new String("http://www.hjyheart.com/addUser?username=" +
                        username.getText().toString() + "&password=" +
                        password.getText().toString() + "&sex=" +
                        sex + "&sign=" +
                        sign.getText().toString() + "&name=" +
                        name.getText().toString());
                Log.e("register", url);
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Snackbar.make(getView(), "网络未知错误", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (responseData.equals("exsist")){
                            Snackbar.make(getView(), "用户已经存在", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        if (responseData.equals("false")){
                            Snackbar.make(getView(), "注册失败,请重试", Snackbar.LENGTH_SHORT).show();
                            return;
                        }

                        // login in
                        OkHttpClient loginClient = new OkHttpClient();
                        String url = new String("http://www.hjyheart.com/login?username=" +
                                username.getText().toString() + "&password=" +
                                password.getText().toString());
                        Log.e("register", url);
                        Request request = new Request.Builder()
                                .url(url)
                                .build();
                        loginClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Snackbar.make(getView(), "未知网络错误", Snackbar.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                try {
                                    String responseData = response.body().string();
                                    JSONObject json = new JSONObject(responseData);
                                    final String name = json.getString("name");
                                    final Boolean sex = json.getBoolean("sex");
                                    final String sign = json.getString("sign");
                                    final String username = json.getString("username");

                                    // store
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("name", name);
                                    editor.putBoolean("sex", sex);
                                    editor.putString("sign", sign);
                                    editor.putString("username", username);
                                    editor.commit();

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);


                                } catch (JSONException e) {
                                    Snackbar.make(getView(), "账号或密码错误", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

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
