package com.example.retvolley.retrofit;

import com.example.retvolley.model.Request;
import com.example.retvolley.model.User;
import com.example.retvolley.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface MethodHTTP {
    @GET("User_Registration.php")
    Call<UserResponse> getUser();

    @GET("User_Registration.php")
    Call<Request> sendUser(@Body User user);

}
