package com.zeffah.usermanagementportal.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.zeffah.usermanagementportal.R;
import com.zeffah.usermanagementportal.callback.OnUserSelectedListener;
import com.zeffah.usermanagementportal.dao.UserDAO;

import java.util.List;
import java.util.Locale;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {
    private List<UserDAO> userDAOList;
    private Context context;
    private OnUserSelectedListener userSelectedListener;
    public UserListAdapter(Context context, List<UserDAO> userDAOList, OnUserSelectedListener userSelectedListener){
       this.context = context;
       this.userDAOList = userDAOList;
       this.userSelectedListener = userSelectedListener;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserListViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final UserListViewHolder userListViewHolder, int position) {
        final UserDAO userDAO = userDAOList.get(userListViewHolder.getAdapterPosition());
        if (userDAO != null){
            userListViewHolder.txtPersonName.setText(String.format(Locale.getDefault(), "%s %s", userDAO.getFirst_name(), userDAO.getLast_name()));
            userListViewHolder.iconUserEdit.setOnClickListener(new OnViewClick(userDAO, userSelectedListener));
            userListViewHolder.iconUserDelete.setOnClickListener(new OnViewClick(userDAO, userSelectedListener));
            Picasso.with(context).load(userDAO.getAvatar())
                    .resize(120, 120)
                    .into(userListViewHolder.imgAvatar, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap imageBitmap = ((BitmapDrawable) userListViewHolder.imgAvatar.getDrawable()).getBitmap();
                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), imageBitmap);
                            imageDrawable.setCircular(true);
                            imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                            userListViewHolder.imgAvatar.setImageDrawable(imageDrawable);
                        }
                        @Override
                        public void onError() {
                            userListViewHolder.imgAvatar.setImageResource(R.drawable.ic_file_download_grey_700_24dp);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return userDAOList.size();
    }

    public void removeItem(UserDAO user){
        userDAOList.remove(user);
        notifyDataSetChanged();
    }

    public void updateUser(UserDAO oldUser, UserDAO newUser){
        if (userDAOList.remove(oldUser)){
            notifyDataSetChanged();
            userDAOList.add(newUser);
        }
        notifyDataSetChanged();
    }

    public static class UserListViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView txtPersonName;
        private ImageView imgAvatar, iconUserDelete, iconUserEdit;
        public UserListViewHolder(View view){
            super(view);
            this.view = view;
            this.imgAvatar = this.view.findViewById(R.id.img_avatar);
            this.txtPersonName = this.view.findViewById(R.id.txt_person_name);
            this.iconUserDelete = this.view.findViewById(R.id.icon_user_delete);
            this.iconUserEdit = this.view.findViewById(R.id.icon_user_edit);
        }
    }

    private class OnViewClick implements View.OnClickListener {
        private UserDAO user;
        private OnUserSelectedListener userSelectedListener;
        private OnViewClick(UserDAO user, OnUserSelectedListener userSelectedListener){
            this.userSelectedListener = userSelectedListener;
            this.user = user;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.icon_user_delete: {
                    userSelectedListener.deleteUser(this.user);
                    return;
                }
                case R.id.icon_user_edit: {
                    userSelectedListener.editUser(user);
                }
            }
        }
    }

}
