package com.sos.comboapp.Adapter;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.sos.comboapp.Calss.ChatClass;
import com.sos.comboapp.R;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterChat extends RecyclerView.Adapter<AdapterChat.Myholder>
{

    private static final int MSG_TYPE_LEFT = 0;
    private static final int MSG_TYPR_RIGHT = 1;
    private Context context;
    private List<ChatClass> list;
    private String imageurl;
    private FirebaseUser firebaseUser;

    public AdapterChat(Context context, List<ChatClass> list, String imageurl)
    {
        this.context = context;
        this.list = list;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (viewType == MSG_TYPE_LEFT)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.chatsectionright, parent, false);
            return new Myholder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.chatsectionleft, parent, false);
            return new Myholder(view);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position)
    {
        String message = list.get(position).getMessage();
        String timeStamp = list.get(position).getTimestamp();
        String type = list.get(position).getType();
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        calendar.setTimeInMillis(Long.parseLong(timeStamp));
        String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.message.setText(message);
        holder.time.setText(timedate);

        try {

            Glide.with(context).load(imageurl).into(holder.image);

        } catch (Exception e)
        {

        }

        if (type.equals("text"))
        {
            holder.message.setVisibility(View.VISIBLE);
            holder.mimage.setVisibility(View.GONE);
            holder.message.setText(message);
        } else
            {
            holder.message.setVisibility(View.GONE);
            holder.mimage.setVisibility(View.VISIBLE);
            Glide.with(context).load(message).into(holder.mimage);
            }
    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    public class Myholder extends RecyclerView.ViewHolder
    {

        private CircleImageView image;
        private ImageView mimage;
        private TextView message, time, isSee;
        private LinearLayout msglayput;


        public Myholder(@NonNull View itemView)
        {
            super(itemView);

            image = itemView.findViewById(R.id.profilec);
            message = itemView.findViewById(R.id.msgc);
            time = itemView.findViewById(R.id.timetv);
            isSee = itemView.findViewById(R.id.isSeen);
            msglayput = itemView.findViewById(R.id.msglayout);
            mimage = itemView.findViewById(R.id.images);
        }
    }
}
