package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.dao.UserDAO;

import java.io.Serializable;

public class ProfileFragment extends Fragment {
    private static final String LOGGED_USER = "USER";
    private UserDAO user;
    public ProfileFragment() { }

    public static ProfileFragment newInstance(UserDAO user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(LOGGED_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserDAO) getArguments().getSerializable(LOGGED_USER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
