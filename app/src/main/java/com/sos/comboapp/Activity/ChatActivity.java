package com.sos.comboapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sos.comboapp.Adapter.AdapterChat;
import com.sos.comboapp.Calss.ChatClass;
import com.sos.comboapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity
{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ImageView profile, block;
    private TextView name, userstatus;
    private EditText msg;
    private ImageButton send, attach;
    private FirebaseAuth firebaseAuth;
    private String uid, myuid, image;
    private ValueEventListener valueEventListener;
    private List<ChatClass> chatList;
    private AdapterChat adapterChat;
    private static final int IMAGEPICK_GALLERY_REQUEST = 300;
    private static final int IMAGE_PICKCAMERA_REQUEST = 400;
    private static final int CAMERA_REQUEST = 100;
    private static final int STORAGE_REQUEST = 200;
    private String cameraPermission[];
    private String storagePermission[];
    private Uri imageuri = null;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference users;
    boolean notify = false;
    boolean isBlocked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat2);
        //all code onCreate
        firebaseAuth = FirebaseAuth.getInstance();
        // initialise the text views and layouts
        profile = findViewById(R.id.profiletv);
        name = findViewById(R.id.nameptv);
        userstatus = findViewById(R.id.onlinetv);
        msg = findViewById(R.id.messaget);
        send = findViewById(R.id.sendmsg);
        attach = findViewById(R.id.attachbtn);
        block = findViewById(R.id.block);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.chatrecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        uid = getIntent().getStringExtra("uid");
        // getting uid of another user using intent
        firebaseDatabase = FirebaseDatabase.getInstance();
        // initialising permissions
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //checkUserStatus();

        users = firebaseDatabase.getReference("Users");
        attach.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               // showImagePicDialog();


            }
        });

        chatList=new ArrayList<>();
        DatabaseReference dbref= FirebaseDatabase.getInstance().getReference().child("Chats");

       // ChatClass modelChat= dbref.getValue( ChatClass.class);
       /* if(modelChat.getSender().equals(myuid)&&
                modelChat.getReceiver().equals(uid)||
                modelChat.getReceiver().equals(myuid)
                        && modelChat.getSender().equals(uid)){
            chatList.add(modelChat);//add the chat in chatlist
        }*/
        adapterChat=new AdapterChat(ChatActivity.this,chatList,image);
        adapterChat.notifyDataSetChanged();
        recyclerView.setAdapter(adapterChat);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        String timestamp=String.valueOf(System.currentTimeMillis());
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("sender",myuid);
        hashMap.put("receiver",uid);
       // hashMap.put("message",message);
        hashMap.put("timestamp",timestamp);
        hashMap.put("dilihat",false);
        hashMap.put("type","text");
        databaseReference.child("Chats").push().setValue(hashMap);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify = true;
                String message = msg.getText().toString().trim();
                if (TextUtils.isEmpty(message))
                {//if empty
                    Toast.makeText(ChatActivity.this, "Please Write Something Here", Toast.LENGTH_LONG).show();
                } else
                    {
                  //  sendmessage(message);
                    }
                msg.setText("");
            }
        });

        Query userquery = users.orderByChild("uid").equalTo(uid);
        userquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // retrieve user data
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String nameh = "" + dataSnapshot1.child("name").getValue();
                    image = "" + dataSnapshot1.child("image").getValue();
                    String onlinestatus = "" + dataSnapshot1.child("onlineStatus").getValue();
                    String typingto = "" + dataSnapshot1.child("typingTo").getValue();
                    if (typingto.equals(myuid)) {// if user is typing to my chat
                        userstatus.setText("Typing....");// type status as typing
                    } else {
                        if (onlinestatus.equals("online")) {
                            userstatus.setText(onlinestatus);
                        } else {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.parseLong(onlinestatus));
                            String timedate = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();
                            userstatus.setText("Last Seen:" + timedate);
                        }
                    }
                    name.setText(nameh);
                    try {
                       // Glide.with(ChatActivity.this).load(image).placeholder(R.drawable.profile_image).into(profile);
                    } catch (Exception e)
                    {
                       //
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });


   }
}