package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.callback.OnUserAuthenticateListener;
import com.zeffah.usermanagementportal.dialogs.CommonDialog;
import com.zeffah.usermanagementportal.methods.GlobalMethods;
import com.zeffah.usermanagementportal.rest.response.AuthenticateUser;
import com.zeffah.usermanagementportal.rest.webservice.UserAPI;

public class UserRegisterFragment extends Fragment {
    private EditText edtEmail, edtPassword;
    private Button btnSignUp, btnToLogin;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (view == btnSignUp) {
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(view.getContext(), "Provide both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                final AlertDialog dialog = CommonDialog.actionProgressDialog(view.getContext());
                dialog.show();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(view.getContext(), "Invalid email address", Toast.LENGTH_LONG).show();
                    return;
                }
                UserAPI.userSignUp(email, password, new OnUserAuthenticateListener() {
                    @Override
                    public void onAuthenticateSuccess(AuthenticateUser authenticateUser) {
                        Toast.makeText(view.getContext(), "" + authenticateUser.getToken(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        Log.d("authentication", authenticateUser.getToken());
                        if (authenticateUser.getToken() != null) {
                            GlobalMethods.startFragment(view.getContext(), new UserAuthenticateFragment(), false);
                        }
                    }

                    @Override
                    public void onAuthenticateFailed(String error) {
                        Toast.makeText(view.getContext(), "" + error, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onThrowError(String msg) {
                        Toast.makeText(view.getContext(), "" + msg, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                return;
            }
            if (view == btnToLogin) {
                GlobalMethods.startFragment(view.getContext(), new UserAuthenticateFragment(), true);
            }
        }
    };

    public UserRegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_register, container, false);
        edtEmail = view.findViewById(R.id.edt_email);
        edtPassword = view.findViewById(R.id.edt_password);
        btnSignUp = view.findViewById(R.id.btn_sign_up);
        btnToLogin = view.findViewById(R.id.btn_go_to_login);
        btnToLogin.setOnClickListener(onClickListener);
        btnSignUp.setOnClickListener(onClickListener);
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
