package com.example.rezazr.checklist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rezazr.checklist.API.ApiInterface;
import com.example.rezazr.checklist.API.ApiService;
import com.example.rezazr.checklist.Model.LoginResponse;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText UsernameLogin, PasswordLogin;
    private Button LoginButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UsernameLogin = findViewById(R.id.UsernameText);
        PasswordLogin = findViewById(R.id.PasswordText);

        LoginButon = findViewById(R.id.LoginButton);

        LoginButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = UsernameLogin.getText().toString();
                final String password = PasswordLogin.getText().toString();

                ApiInterface api = ApiService.getKoneksi().create(ApiInterface.class);
                api.Login(username, password).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<LoginResponse>() {
                            @Override
                            public void onNext(LoginResponse loginResponse) {
                                SharedPreferences sharedPreferences = getSharedPreferences("loginData", Context.MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("username", username);
                                editor.putString("password", password);
                                editor.putString("token", loginResponse.getToken);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        })
            }
        });
    }
}
