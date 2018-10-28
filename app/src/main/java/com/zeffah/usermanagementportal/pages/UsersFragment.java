package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.adapters.UserListAdapter;
import com.zeffah.usermanagementportal.callback.OnSelectDeleteListener;
import com.zeffah.usermanagementportal.callback.OnUserAuthenticateListener;
import com.zeffah.usermanagementportal.callback.OnUserDeleteListener;
import com.zeffah.usermanagementportal.callback.OnUserEditListener;
import com.zeffah.usermanagementportal.callback.OnUserFetchListener;
import com.zeffah.usermanagementportal.callback.OnUserSelectedListener;
import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.dialogs.CommonDialog;
import com.zeffah.usermanagementportal.dialogs.EditUserDialog;
import com.zeffah.usermanagementportal.methods.GlobalMethods;
import com.zeffah.usermanagementportal.rest.response.AuthenticateUser;
import com.zeffah.usermanagementportal.rest.response.DeleteUser;
import com.zeffah.usermanagementportal.rest.webservice.UserAPI;

import java.util.List;

public class UsersFragment extends Fragment implements OnUserSelectedListener {
    private RecyclerView usersRecyclerView;
    private Context context;
    private UserListAdapter userListAdapter;
    public UsersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        usersRecyclerView = view.findViewById(R.id.list_users);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final AlertDialog dialog = CommonDialog.actionProgressDialog(view.getContext());
        dialog.show();
        UserAPI.fetchUsers(new OnUserFetchListener() {
            @Override
            public void OnUSerFetchSuccess(List<UserDAO> users) {
                initList(users, dialog);
            }

            @Override
            public void OnUserFetchError(String error) {
                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onThrowable(Throwable throwable) {
                Toast.makeText(context, ""+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    private void initList(List<UserDAO> users, AlertDialog dialog){
        userListAdapter = new UserListAdapter(context, users, this);
        usersRecyclerView.setAdapter(userListAdapter);
        dialog.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @Override
    public void deleteUser(final UserDAO user) {
        CommonDialog.deleteUser(context, user, new OnSelectDeleteListener() {
            @Override
            public void onSelectDelete() {
                UserAPI.deleteUser(user.getId(), new OnUserDeleteListener() {
                    @Override
                    public void onDeleteSuccess(DeleteUser deleteUser) {
                        Toast.makeText(context, ""+deleteUser.getMessage(), Toast.LENGTH_SHORT).show();
                        userListAdapter.removeItem(user);
                    }

                    @Override
                    public void onDeleteFailed(String error) {
                        Log.d("ErrorMessage", error);
                    }

                    @Override
                    public void onDeleteThrowable(Throwable throwable) {
                        Toast.makeText(context, ""+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).show();
    }

    @Override
    public void editUser(UserDAO user) {
        GlobalMethods.startDialog(context, EditUserDialog.newInstance(user, new OnUserEditListener() {
            @Override
            public void onEdited(UserDAO oldUser, UserDAO newUser) {
                userListAdapter.updateUser(oldUser, newUser);
            }
        }));
    }
}
