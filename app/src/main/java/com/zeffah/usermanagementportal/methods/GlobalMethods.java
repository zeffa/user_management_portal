package com.zeffah.usermanagementportal.methods;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.zeffah.usermanagementportal.R;

public class GlobalMethods {
    public static double addToNumbers(Double... numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum;
    }

    public static void startFragment(Context context, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, fragment.getClass().getSimpleName());
        if (addToBackStack){
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        transaction.commit();
    }

    public static void startDialog(Context context, DialogFragment dialogFragment){
        FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
        dialogFragment.show(fragmentManager, "DIALOG_NAME");
    }

    public static void setActionBarTitle(Context context, String title, boolean setDisplayHomeAsUpEnabled) {
        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            if (!setDisplayHomeAsUpEnabled) {
                actionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }
}
