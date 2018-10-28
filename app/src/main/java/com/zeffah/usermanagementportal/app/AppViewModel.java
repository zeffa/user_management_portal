package com.zeffah.usermanagementportal.app;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.zeffah.usermanagementportal.dao.UserDAO;

import java.util.List;

public class AppViewModel extends ViewModel {
    private MutableLiveData<List<UserDAO>> usersList;

    public MutableLiveData<List<UserDAO>> getUsersList() {
        if (usersList != null){
            return usersList;
        }
        return null;
    }

    public void setUsersList(List<UserDAO> usersList_) {
        usersList = new MutableLiveData<>();
        usersList.setValue(usersList_);
    }
}
