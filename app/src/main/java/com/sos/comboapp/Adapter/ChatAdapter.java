package com.sos.comboapp.Adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sos.comboapp.Calss.Chatmessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.My_chat_viewholder>
{
    private List<Chatmessage> chatmessage1;

    public ChatAdapter(List<Chatmessage> chatmessage)
    {
        this.chatmessage1 = chatmessage;
    }

    public ChatAdapter(List<Chatmessage> chatmessage, Bitmap bitmapFromEncoder, String string)
    {


    }

    @NonNull
    @Override
    public ChatAdapter.My_chat_viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.My_chat_viewholder my_chat_viewholder, int i)
    {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class My_chat_viewholder extends RecyclerView.ViewHolder
    {
        public My_chat_viewholder(@NonNull View itemView)
        {
            super(itemView);
        }
    }
}
