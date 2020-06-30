package com.example.rezazr.checklist.API;

import com.example.rezazr.checklist.Model.LoginResponse;
import com.example.rezazr.checklist.Model.RegisterResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("/register")
    @FormUrlEncoded
    Observable<RegisterResponse> Register(@Field("username")String username,
                                          @Field("password")String password,
                                          @Field("email")String email);

    @POST("/login")
    @FormUrlEncoded
    Observable<LoginResponse> Login(@Field("username")String username,
                                       @Field("password")String password);

}
