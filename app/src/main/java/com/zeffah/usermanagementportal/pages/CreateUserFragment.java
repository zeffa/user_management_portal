package com.zeffah.usermanagementportal.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.callback.OnUserCreateListener;
import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.dialogs.CommonDialog;
import com.zeffah.usermanagementportal.rest.response.RegisterUser;
import com.zeffah.usermanagementportal.rest.webservice.UserAPI;

import org.w3c.dom.Text;

public class CreateUserFragment extends Fragment {
    private EditText edtPersonName, edtJob;
    private Button btnCreateUser;

    public CreateUserFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user, container, false);
        edtPersonName = view.findViewById(R.id.edt_person_name);
        edtJob = view.findViewById(R.id.edt_job);
        btnCreateUser = view.findViewById(R.id.btn_create_user);
        btnCreateUser.setOnClickListener(onClickListener);
        return view;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (v == btnCreateUser){
                String job = edtJob.getText().toString().trim();
                String personName = edtPersonName.getText().toString().trim();
                if (TextUtils.isEmpty(personName) || TextUtils.isEmpty(job)){
                    Toast.makeText(v.getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                final AlertDialog dialog = CommonDialog.actionProgressDialog(v.getContext());
                dialog.show();
                UserDAO user = new UserDAO();
                user.setJob(job);
                user.setFirst_name(personName);
                user.setLast_name(personName);
                UserAPI.createUser(user, new OnUserCreateListener() {
                    @Override
                    public void onCreateSuccess(RegisterUser registerUser) {
                        Toast.makeText(v.getContext(), "User Created Successfully", Toast.LENGTH_SHORT).show();
                        edtJob.setText("");
                        edtPersonName.setText("");
                        dialog.dismiss();
                    }

                    @Override
                    public void onCreateFail(String error) {
                        Toast.makeText(v.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onThrowable(Throwable throwable) {
                        Toast.makeText(v.getContext(), ""+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
