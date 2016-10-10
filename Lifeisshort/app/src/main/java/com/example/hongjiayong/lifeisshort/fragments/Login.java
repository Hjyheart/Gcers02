package com.example.hongjiayong.lifeisshort.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class Login extends BaseFragment {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private static final String DATA_NAME = "name";

    private String title = "Login";

    public Login() {
        // Required empty public constructor
    }

    public static Login newInstance(String title, int indicatorColor,int dividerColor){
        Login f = new Login();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        loginButton = (Button) view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("")){
                    Toast.makeText(getContext(), "plwase input username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals("")){
                    Toast.makeText(getContext(), "please input password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().length() < 8){
                    Toast.makeText(getContext(), "password should longer than 8", Toast.LENGTH_SHORT).show();
                }

                // start login
                OkHttpClient client = new OkHttpClient();
                String url = "http://www.hjyheart.com/login?username=" +
                        username.getText().toString() + "&password=" +
                        password.getText().toString();
                Log.e("url", url);
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Snackbar.make(getView(), "connect error", Snackbar.LENGTH_SHORT).show();
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
                            editor.putString("password", password.getText().toString());
                            editor.commit();

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);

                            getActivity().finish();


                        } catch (JSONException e) {
                            Snackbar.make(getView(), "wrong password or no user", Snackbar.LENGTH_SHORT).show();
                        }
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
