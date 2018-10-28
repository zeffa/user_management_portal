package com.zeffah.usermanagementportal.rest.response;

public class DeleteUser {
    private int success;
    private String message;

    public DeleteUser(int success, String message){
        this.success = success;
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
