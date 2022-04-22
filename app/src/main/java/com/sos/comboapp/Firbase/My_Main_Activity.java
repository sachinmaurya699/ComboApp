package com.sos.comboapp.Firbase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.R;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivityMyMainBinding;

import java.util.Base64;
import java.util.HashMap;

public class My_Main_Activity extends AppCompatActivity {

    private ActivityMyMainBinding binding;
    private PreferenceManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        manager = new PreferenceManager(getApplicationContext());
        LoadUserDetails();
        gettoken();
        setLister();
    }

    private void LoadUserDetails() {
        binding.mainTextId.setText(manager.getString(Constant.Key_Name));
        //byte[] bytes= Base64.decode()
        //Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        //binding.imageMain.setImageBitmap(bitmap);
    }
    private void setLister()
    {
        binding.imageMain.setOnClickListener(view -> signOut());
    }

    private void gettoken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void showToast(String message) {
        Toast.makeText(My_Main_Activity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateToken(String token) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constant.Key_collection_users).document(manager.getString(Constant.Key_USER_ID));
        documentReference.update(Constant.Key_FCM_TOKEN, token)
                .addOnSuccessListener(unused -> showToast("Token update successfully"))
                .addOnFailureListener(e -> showToast("Unable to update token"));


    }

    private void signOut()
    {
        showToast("Siging out......");
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(Constant.Key_collection_users).document(manager.getString(Constant.Key_USER_ID));
        HashMap<String, Object> update = new HashMap<>();
        update.put(Constant.Key_FCM_TOKEN, FieldValue.delete());
        documentReference.update(update)
                .addOnSuccessListener(unused -> manager.clear());
        startActivity(new Intent(getApplicationContext(), SignInActivity.class));

    }

}