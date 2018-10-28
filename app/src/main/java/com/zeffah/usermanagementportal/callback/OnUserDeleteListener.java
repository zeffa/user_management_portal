package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.rest.response.DeleteUser;

public interface OnUserDeleteListener {
    void onDeleteSuccess(DeleteUser deleteUser);
    void onDeleteFailed(String error);
    void onDeleteThrowable(Throwable throwable);

}
