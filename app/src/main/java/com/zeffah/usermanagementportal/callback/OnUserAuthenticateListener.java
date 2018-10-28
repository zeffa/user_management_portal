package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.rest.response.AuthenticateUser;

public interface OnUserAuthenticateListener {
    void onAuthenticateSuccess(AuthenticateUser authenticateUser);

    void onAuthenticateFailed(String error);

    void onThrowError(String msg);
}
