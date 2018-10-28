package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.methods.GlobalMethods;

public class LandingFragment extends Fragment {
    private GridLayout gridLayout;

    public LandingFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        gridLayout = view.findViewById(R.id.page_grid);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int childCount = gridLayout.getChildCount();
        for (int i = 0; i < childCount; i++){
            CardView cardView = (CardView) gridLayout.getChildAt(i);
            cardView.setOnClickListener(clickListener);
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_all_users: {
                    GlobalMethods.startFragment(v.getContext(), new UsersFragment(), true);
                    return;
                }
                case R.id.card_new_user: {
                    GlobalMethods.startFragment(v.getContext(), new CreateUserFragment(), true);
                    return;

                }
                case R.id.card_profile: {
                    Toast.makeText(v.getContext(), "PROFILE COMING SOON", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
