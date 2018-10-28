package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.rest.response.RegisterUser;
import com.zeffah.usermanagementportal.rest.response.UpdateUser;

public interface OnUserCreateListener {
    void onCreateSuccess(RegisterUser registerUser);
    void onCreateFail(String error);
    void onThrowable(Throwable throwable);
}
