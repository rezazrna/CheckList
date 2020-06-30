package com.example.rezazr.checklist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rezazr.checklist.API.ApiInterface;
import com.example.rezazr.checklist.API.ApiService;
import com.example.rezazr.checklist.Model.RegisterResponse;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText EmailRegister, UsernameRegister, PasswordRegister ;
    private Button SubmitRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EmailRegister = findViewById(R.id.EmailTextRegister);
        UsernameRegister = findViewById(R.id.UsernameTextRegister);
        PasswordRegister = findViewById(R.id.PasswordTextRegister);
        SubmitRegister = findViewById(R.id.SubmitRegisterButton);

        SubmitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = EmailRegister.getText().toString().trim();
                final String username = UsernameRegister.getText().toString().trim();
                final String password = PasswordRegister.getText().toString().trim();
                ApiInterface api = ApiService.getKoneksi().create(ApiInterface.class);
                api.Register(email, username, password).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<RegisterResponse>() {

                            @Override
                            public void onNext(RegisterResponse value) {
                                if (value.getStatusCode() == 200) {
                                    Intent intent = new Intent(Register.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
