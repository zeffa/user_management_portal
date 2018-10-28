package com.zeffah.usermanagementportal.rest.response;

import com.google.gson.annotations.SerializedName;
import com.zeffah.usermanagementportal.dao.UserDAO;

import java.util.List;

public class FetchUsers {
    @SerializedName("data")
    private List<UserDAO> users;

    public List<UserDAO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDAO> users) {
        this.users = users;
    }
}
