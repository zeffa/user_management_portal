package com.zeffah.usermanagementportal.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.callback.OnSelectDeleteListener;
import com.zeffah.usermanagementportal.dao.UserDAO;

public class CommonDialog {
    public static AlertDialog deleteUser(Context context, UserDAO user, final OnSelectDeleteListener userDeleteListener){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("Are you sure you want to delete "+user.getFirst_name()+" "+user.getLast_name());
        alertDialog.setCancelable(true);
        AlertDialog alert = alertDialog.create();
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userDeleteListener.onSelectDelete();
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return alert;
    }

    public static AlertDialog actionProgressDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(R.layout.loading_indicator_dialog);
        AlertDialog alertDialog = builder.create();
        Window viewWindow = alertDialog.getWindow();
        if (viewWindow != null){
            viewWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        return alertDialog;
    }
}
