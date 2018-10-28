package com.zeffah.usermanagementportal.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.callback.OnUserEditListener;
import com.zeffah.usermanagementportal.callback.OnUserUpdateListener;
import com.zeffah.usermanagementportal.dao.UserDAO;
import com.zeffah.usermanagementportal.rest.response.UpdateUser;
import com.zeffah.usermanagementportal.rest.webservice.UserAPI;

import org.w3c.dom.Text;

import java.io.Serializable;

public class EditUserDialog extends DialogFragment {
    public static final String EDIT_USER = "EDIT_USER";
    private EditText edtFirstName, edtLastName, edtJOb;
    private Button btnUpdateUser, btnCancel;
    private OnUserEditListener onUserEditListener;
    private UserDAO userDAO;

    public static EditUserDialog newInstance(UserDAO user, OnUserEditListener onUserEditListener) {
        EditUserDialog editUserDialog = new EditUserDialog();
        editUserDialog.setOnUserEditListener(onUserEditListener);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EDIT_USER, (Serializable) user);
        editUserDialog.setArguments(bundle);
        return editUserDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Update User");
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_user_layout, container, false);
        edtFirstName = view.findViewById(R.id.edt_first_name);
        edtLastName = view.findViewById(R.id.edt_last_name);
        edtJOb = view.findViewById(R.id.edt_job);
        btnUpdateUser = view.findViewById(R.id.btn_update_user);
        btnCancel = view.findViewById(R.id.btn_cancel_dialog);

        btnUpdateUser.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            userDAO = (UserDAO) getArguments().getSerializable(EDIT_USER);
            if (userDAO != null) {
                edtLastName.setText(userDAO.getLast_name());
                edtFirstName.setText(userDAO.getFirst_name());
            }
        }
    }

    public void setOnUserEditListener(OnUserEditListener onUserEditListener) {
        this.onUserEditListener = onUserEditListener;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            if (v == btnCancel){
                dismiss();
                return;
            }
            if (v == btnUpdateUser){
                String firstName = edtFirstName.getText().toString().trim();
                String lastName = edtLastName.getText().toString().trim();
                String job = edtJOb.getText().toString().trim();

                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(job)){
                    Toast.makeText(v.getContext(), "Please provide all the information required", Toast.LENGTH_SHORT).show();
                    return;
                }
                final UserDAO newUser = new UserDAO();
                newUser.setFirst_name(firstName);
                newUser.setLast_name(lastName);
                newUser.setId(userDAO.getId());
                newUser.setAvatar(userDAO.getAvatar());
                newUser.setJob(job);
                UserAPI.updateUser(newUser, new OnUserUpdateListener() {
                    @Override
                    public void onUpdateSuccess(UpdateUser updateUser) {
                        onUserEditListener.onEdited(userDAO, newUser);
                    }

                    @Override
                    public void onUpdateFail(String error) {
                        Toast.makeText(v.getContext(), ""+error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onThrowable(Throwable throwable) {
                        Toast.makeText(v.getContext(), ""+throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dismiss();
            }
        }
    };
}
