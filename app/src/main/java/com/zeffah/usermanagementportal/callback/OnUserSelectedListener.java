package com.zeffah.usermanagementportal.callback;

import com.zeffah.usermanagementportal.dao.UserDAO;

public interface OnUserSelectedListener {
    void deleteUser(UserDAO user);
    void editUser(UserDAO user);
}
