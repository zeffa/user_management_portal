package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.rest.response.FetchUsers;

import java.util.List;

public interface OnUserFetchListener {
    void OnUSerFetchSuccess(List<UserDAO> users);
    void OnUserFetchError(String error);
    void onThrowable(Throwable throwable);
}
