package com.sos.comboapp.Firbase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sos.comboapp.Activity.User_Activity;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.R;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivityHome2Binding;
import com.sos.comboapp.databinding.ActivityHomeBinding;

import java.util.Base64;
import java.util.HashMap;

public class Home_Activity extends AppCompatActivity
{

    private ActivityHome2Binding homeBinding;
    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        homeBinding=ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        manager= new PreferenceManager(getApplicationContext());
        //loaduserDetails();
        setListener();
        getToken();
        loadurlDetails();

    }

    private void setListener()
    {
        homeBinding.myLogout.setOnClickListener(view -> {
            signout();
        });
        homeBinding.febNewCaht.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), User_Activity.class));
        });
    }
    private void loadurlDetails()
    {
        homeBinding.myProfileName.setText(manager.getString(Constant.Key_Name));
   //     byte[] bytes= android.util.Base64.decode(manager.getString(Constant.Key_IMAGE), android.util.Base64.DEFAULT);
       // byte[] bytes= Base64.decode(manager.getString(Constant.Key_IMAGE),Base64.Default);
       //Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //homeBinding.myProfileImg.setImageBitmap(bitmap);
    }
    private void showToast(String message)
    {
        Toast.makeText(Home_Activity.this,message, Toast.LENGTH_SHORT).show();
    }
    private void getToken()
    {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);

    }
    private void updateToken(String token)
    {
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        DocumentReference documentReference=database.collection(Constant.Key_collection_users).document(
                manager.getString(Constant.Key_USER_ID)

        );
        documentReference.update(Constant.Key_FCM_TOKEN,token)
                .addOnSuccessListener(unused -> showToast("Token Update Successfully"))
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }
    private void signout()
    {
        showToast("Signing out........");

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constant.Key_collection_users).document(
                manager.getString(Constant.Key_USER_ID)
        );
        HashMap<String, Object> update = new HashMap<>();
        update.put(Constant.Key_FCM_TOKEN, FieldValue.delete());
        documentReference.update(update)
          .addOnSuccessListener(unused -> {
              manager.clear();
              startActivity(new Intent(Home_Activity.this,SignInActivity.class));
              finish();
              })
                .addOnFailureListener(e -> {
                    showToast("unable to sign out");
                });
    }
}