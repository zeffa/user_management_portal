package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

public class UserAuthenticateFragment extends Fragment {
    private Context context;
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnToRegister;

    public UserAuthenticateFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_authenticate, container, false);
        btnLogin = view.findViewById(R.id.btn_login);
        btnToRegister = view.findViewById(R.id.btn_go_to_register);
        edtPassword = view.findViewById(R.id.edt_password);
        edtEmail = view.findViewById(R.id.edt_email);
        btnLogin.setOnClickListener(clickListener);
        btnToRegister.setOnClickListener(clickListener);
        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            if (view == btnToRegister){
                GlobalMethods.startFragment(view.getContext(), new UserRegisterFragment(), true);
                return;
            }
            if (view == btnLogin){
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(view.getContext(), "Provide both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                final AlertDialog dialog = CommonDialog.actionProgressDialog(view.getContext());
                dialog.show();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(view.getContext(), "Invalid email address", Toast.LENGTH_LONG).show();
                    return;
                }
                UserAPI.authenticate(email, password, new OnUserAuthenticateListener() {
                    @Override
                    public void onAuthenticateSuccess(AuthenticateUser authenticateUser) {
                        Toast.makeText(view.getContext(), ""+authenticateUser.getToken(), Toast.LENGTH_SHORT).show();
                        GlobalMethods.startFragment(view.getContext(), new LandingFragment(), false);
                        dialog.dismiss();
                    }

                    @Override
                    public void onAuthenticateFailed(String error) {
                        Toast.makeText(view.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onThrowError(String msg) {
                        Toast.makeText(view.getContext(), ""+msg, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }
}
