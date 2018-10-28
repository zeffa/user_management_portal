package com.zeffah.usermanagementportal.rest;

import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.rest.response.DeleteUser;
import com.zeffah.usermanagementportal.rest.response.FetchUsers;
import com.zeffah.usermanagementportal.rest.response.AuthenticateUser;
import com.zeffah.usermanagementportal.rest.response.RegisterUser;
import com.zeffah.usermanagementportal.rest.response.UpdateUser;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/api/register")
    Call<AuthenticateUser> register(@Body UserDAO user);

    @POST("/api/login")
    Call<AuthenticateUser> authenticate(@Body UserDAO user);

    @POST("/api/users")
    Call<RegisterUser> createUser(@Body JSONObject user);

    @PUT("/api/users/{id}")
    Call<UpdateUser> updateUser(@Path("id") int userId, @Body JSONObject user);

    @DELETE("/api/users/{id}")
    Call<DeleteUser> deleteUser(@Path("id") int userId);

    @GET("/api/users")
    Call<FetchUsers> getAllUsers(@Query("page") int pageNumber);
}
