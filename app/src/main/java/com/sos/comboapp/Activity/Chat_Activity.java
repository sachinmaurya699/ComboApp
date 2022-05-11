package com.sos.comboapp.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sos.comboapp.Adapter.ChatAdapter;
import com.sos.comboapp.Calss.Chatmessage;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.Model.User;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivityChatBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Chat_Activity extends AppCompatActivity
{

    private ActivityChatBinding binding;
    private User recivier_user;
    private List<Chatmessage> chatmessage;
   // private Chatmessage chatmessage;
    private ChatAdapter chatAdapter;
    private PreferenceManager manager;
    private FirebaseFirestore database;
    private String conversionId =null;
    private Boolean isReceiverAvailable = false;
    private Object obj1,obj2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        chatmessage= new ArrayList<>();
        setListeners();
        loadReciverdetails();
        init();
      //  listenMessage1();
    }

    private void sendMessage()
    {
        HashMap<String,Object> message= new HashMap<>();
        message.put(Constant.Key_SENDER_ID,manager.getString(Constant.Key_USER_ID));
        message.put(Constant.Key_RECEIVER_ID,recivier_user.id);
        message.put(Constant.key_MESSAGE,binding.textMessage.getText().toString());
        message.put(Constant.Key_TIMESTAMP,new Date());
        database.collection(Constant.Key_COLLECTION_chat).add(message);
        binding.textMessage.setText(null);
    }
    private void listenMessage1()
    {
       database.collection(Constant.Key_COLLECTION_chat)
                .whereEqualTo(Constant.Key_SENDER_ID,manager
                .getString(Constant.Key_USER_ID))
                .whereEqualTo(Constant.Key_RECEIVER_ID,recivier_user.id)
                .addSnapshotListener(eventlistener);

        database.collection(Constant.Key_COLLECTION_chat)
                .whereEqualTo(Constant.Key_SENDER_ID,recivier_user.id)
                .whereEqualTo(Constant.Key_RECEIVER_ID,manager.getString(Constant.Key_USER_ID))
                .addSnapshotListener(eventlistener);
    }
    private void init()
    {
        manager= new PreferenceManager(getApplicationContext());
        chatmessage= new ArrayList<>();
       // chatAdapter= new ChatAdapter(chatmessage,getBitmapFromEncoder(recivier_user.image),manager.getString(Constant.Key_USER_ID));
        chatAdapter= new ChatAdapter(chatmessage,null,manager.getString(Constant.Key_USER_ID));
        binding.myChatRecyclerview.setAdapter(chatAdapter);
        database=FirebaseFirestore.getInstance();
    }

    //all Clear.....
    private final EventListener<QuerySnapshot> eventlistener= ((value, error) -> {

        if(error!=null)
        {
            return;
        }
        if(value !=null)
        {
            int count=chatmessage.size();
            for(DocumentChange  documentChange: value.getDocumentChanges())
            {

                if(documentChange.getType() == DocumentChange.Type.ADDED )
                {
                     Chatmessage  chatmessage1 = new Chatmessage();
                    chatmessage1.senderId=documentChange.getDocument().getString(Constant.Key_SENDER_ID);
                    chatmessage1.receiverId=documentChange.getDocument().getString(Constant.Key_RECEIVER_ID);
                    chatmessage1.message=documentChange.getDocument().getString(Constant.key_MESSAGE);
                    chatmessage1.datetime=getReadableDateTime(documentChange.getDocument().getDate(Constant.Key_TIMESTAMP));
                    chatmessage1.dataobject=documentChange.getDocument().getDate(Constant.Key_TIMESTAMP);
                    chatmessage.add(chatmessage1);
                }
            }

            //Object obj1,obj2;
            Collections.sort(chatmessage, new Comparator<Chatmessage>()
            {
                @Override
                public int compare(Chatmessage obj1, Chatmessage obj2)
                {
                    obj1.dataobject.compareTo(obj2.dataobject);
                    return 0;
                }
                });

            if(count == 0)
            {
                chatAdapter.notifyDataSetChanged();

            }else
            {
                chatAdapter.notifyItemRangeInserted(chatmessage.size(),chatmessage.size());
                binding.myChatRecyclerview.smoothScrollToPosition(chatmessage.size()-1);
            }
            binding.myChatRecyclerview.setVisibility(View.VISIBLE);

        }
        binding.myProgressBar.setVisibility(View.GONE);

    });


    private Bitmap getBitmapFromEncoder(String encodedImage)
    {
        byte[] bytes= android.util.Base64.decode(encodedImage, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    private void setListeners()
    {
      binding.backImg.setOnClickListener(view -> {
         onBackPressed();
      });
      binding.sendSms.setOnClickListener(view -> {
          sendMessage();
      });
    }
    private String getReadableDateTime(Date data)
    {
        return new SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(data);
    }
    private void  loadReciverdetails()
    {
        recivier_user= (User) getIntent().getSerializableExtra(Constant.Key_USER);
        binding.myOnlineText.setText("sachin maurya");

    }
    private  void listenAvilablityofReciver()
    {
        database.collection(Constant.Key_collection_users).document(String.valueOf(recivier_user)
        ).addSnapshotListener(Chat_Activity.this,(value, error) ->{

            if(error!=null)
            {
                return;
            }if(value!=null)
            {
                if(value.getLong(Constant.Key_Avility)!=null)
                {
                    int avilablity = Objects.requireNonNull(
                            value.getLong(Constant.Key_Avility)
                    ).intValue();
                    isReceiverAvailable = avilablity == 1;
                }
            }
            if(isReceiverAvailable)
            {
                binding.myOnlineText.setVisibility(View.VISIBLE);
            }else
            {
                binding.myOnlineText.setVisibility(View.GONE);
            }
        });
    }

}