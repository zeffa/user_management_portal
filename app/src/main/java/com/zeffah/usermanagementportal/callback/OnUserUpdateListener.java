package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.rest.response.UpdateUser;

public interface OnUserUpdateListener {
    void onUpdateSuccess(UpdateUser updateUser);
    void onUpdateFail(String error);
    void onThrowable(Throwable throwable);
}
