package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.dao.UserDAO;

public interface OnUserEditListener {
    void onEdited(UserDAO oldUser, UserDAO newUser);
}
