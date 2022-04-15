package com.example.recyclerview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CallAPI {
    @Headers({"app-id: 62444dc711de0a49ff5dc57a"})
    @GET("user")
    Call<UserWrapper> getUsers();
}
