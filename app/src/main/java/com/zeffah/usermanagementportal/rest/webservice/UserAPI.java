package com.zeffah.usermanagementportal.rest.webservice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.zeffah.usermanagementportal.callback.OnUserAuthenticateListener;
import com.zeffah.usermanagementportal.callback.OnUserCreateListener;
import com.zeffah.usermanagementportal.callback.OnUserDeleteListener;
import com.zeffah.usermanagementportal.callback.OnUserFetchListener;
import com.zeffah.usermanagementportal.callback.OnUserUpdateListener;
import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.rest.ApiClient;
import com.zeffah.usermanagementportal.rest.ApiInterface;
import com.zeffah.usermanagementportal.rest.response.AuthenticateUser;
import com.zeffah.usermanagementportal.rest.response.DeleteUser;
import com.zeffah.usermanagementportal.rest.response.FetchUsers;
import com.zeffah.usermanagementportal.rest.response.RegisterUser;
import com.zeffah.usermanagementportal.rest.response.UpdateUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPI {
    public static void authenticate(String email, String password, final OnUserAuthenticateListener userAuthenticateListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        UserDAO userDAO = new UserDAO(email, password);
        final Call<AuthenticateUser> authenticateUserCall = apiInterface.authenticate(userDAO);
        authenticateUserCall.enqueue(new Callback<AuthenticateUser>() {
            @Override
            public void onResponse(@NonNull Call<AuthenticateUser> call, @NonNull Response<AuthenticateUser> response) {
                AuthenticateUser loginResponse = response.body();
                if (loginResponse != null) {
                    if (loginResponse.getToken() != null) {
                        userAuthenticateListener.onAuthenticateSuccess(loginResponse);
                    } else {
                        userAuthenticateListener.onAuthenticateFailed(loginResponse.getError());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthenticateUser> call, @NonNull Throwable throwable) {
                userAuthenticateListener.onThrowError(throwable.getLocalizedMessage());
            }
        });
    }

    public static void userSignUp(String email, String password, final OnUserAuthenticateListener userAuthenticateListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        UserDAO userDAO = new UserDAO(email, password);
        final Call<AuthenticateUser> signUpUserCall = apiInterface.register(userDAO);
        signUpUserCall.enqueue(new Callback<AuthenticateUser>() {
            @Override
            public void onResponse(@NonNull Call<AuthenticateUser> call, @NonNull Response<AuthenticateUser> response) {
                AuthenticateUser loginResponse = response.body();
                if (loginResponse != null) {
                    if (loginResponse.getToken() != null) {
                        userAuthenticateListener.onAuthenticateSuccess(loginResponse);
                    } else {
                        userAuthenticateListener.onAuthenticateFailed(loginResponse.getError());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthenticateUser> call, @NonNull Throwable throwable) {
                userAuthenticateListener.onThrowError(throwable.getLocalizedMessage());
            }
        });
    }

    public static void fetchUsers(final OnUserFetchListener userFetchListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<FetchUsers> fetchUsersCall = apiInterface.getAllUsers(1);
        fetchUsersCall.enqueue(new Callback<FetchUsers>() {
            @Override
            public void onResponse(@NonNull Call<FetchUsers> call, @NonNull Response<FetchUsers> response) {
                FetchUsers fetchUsers = response.body();
                if (fetchUsers != null) {
                    List<UserDAO> userDAOList = fetchUsers.getUsers();
                    Log.d("usersList", new Gson().toJson(userDAOList));
                    userFetchListener.OnUSerFetchSuccess(userDAOList);
                } else {
                    userFetchListener.OnUserFetchError("Fetching error...");
                }
            }

            @Override
            public void onFailure(@NonNull Call<FetchUsers> call, @NonNull Throwable t) {
                userFetchListener.onThrowable(t);
            }
        });
    }

    public static void deleteUser(int userId, final OnUserDeleteListener deleteListener){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<DeleteUser> deleteUserCall = apiInterface.deleteUser(userId);
        deleteUserCall.enqueue(new Callback<DeleteUser>() {
            @Override
            public void onResponse(@NonNull Call<DeleteUser> call, @NonNull Response<DeleteUser> response) {
                DeleteUser deleteUser = new DeleteUser(1, "Successful");
                    deleteListener.onDeleteSuccess(deleteUser);
            }

            @Override
            public void onFailure(@NonNull Call<DeleteUser> call, @NonNull Throwable t) {
                deleteListener.onDeleteThrowable(t);
            }
        });
    }

    public static void updateUser(UserDAO user, final OnUserUpdateListener onUserUpdateListener){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject userObject = new JSONObject();
            userObject.put("name", user.getFirst_name()+" "+user.getLast_name());
            userObject.put("job", user.getJob());
            final Call<UpdateUser> updateUserCall = apiInterface.updateUser(user.getId(), userObject);

            updateUserCall.enqueue(new Callback<UpdateUser>() {
                @Override
                public void onResponse(@NonNull Call<UpdateUser> call, @NonNull Response<UpdateUser> response) {
                    UpdateUser updateUser = response.body();
                    if (updateUser != null){
                        onUserUpdateListener.onUpdateSuccess(updateUser);
                    }else {
                        onUserUpdateListener.onUpdateFail("User update failed");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdateUser> call, @NonNull Throwable t) {
                    onUserUpdateListener.onThrowable(t);
                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static void createUser(UserDAO user, final OnUserCreateListener onUserCreated){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        try {
            JSONObject userObject = new JSONObject();
            userObject.put("name", user.getFirst_name()+" "+user.getLast_name());
            userObject.put("job", user.getJob());

            final Call<RegisterUser> createUserCall = apiInterface.createUser(userObject);
            createUserCall.enqueue(new Callback<RegisterUser>() {
                @Override
                public void onResponse(@NonNull Call<RegisterUser> call, @NonNull Response<RegisterUser> response) {
                    RegisterUser registerUser = response.body();
                    if (registerUser != null){
                        onUserCreated.onCreateSuccess(registerUser);
                    }else {
                        onUserCreated.onCreateFail("Error creating user");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RegisterUser> call, @NonNull Throwable t) {
                    onUserCreated.onThrowable(t);
                }
            });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
