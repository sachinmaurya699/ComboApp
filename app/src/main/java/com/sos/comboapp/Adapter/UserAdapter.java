package com.sos.comboapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.Lisener.Userlistener;
import com.sos.comboapp.Model.User;
import com.sos.comboapp.databinding.ItemContainerUserBinding;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> user;
    private  Userlistener userlistener;
    private Context context;
    private ItemContainerUserBinding binding;

    public UserAdapter(Context context,List<User> user,Userlistener userlistener)
    {
        this.context=context;
        this.user = user;
        this.userlistener=userlistener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        ItemContainerUserBinding binding = ItemContainerUserBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder userViewHolder, int position)
    {
        userViewHolder.setUserData(user.get(position));
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder
    {

        UserViewHolder(ItemContainerUserBinding itemContainerUserlayoutBinding) {
            super(itemContainerUserlayoutBinding.getRoot());
            binding = itemContainerUserlayoutBinding;
        }

        void setUserData(User user)
        {
            binding.textName.setText(user.name);
            binding.myUnssenSms.setText(user.email);

            //binding.profilePic.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(view ->
            {
                userlistener.onUserClicked(user);
            });
        }

    }

   /* private Bitmap getUserImage(String encoding)
    {
        byte[] bytes= android.util.Base64.decode(encoding,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);

    }*/


}
