package com.sos.comboapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.R;
import com.sos.comboapp.Utilites.PreferenceManager;

public class Base_Activity extends AppCompatActivity
{
    private DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        PreferenceManager preferenceManager= new PreferenceManager(getApplicationContext());
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        documentReference= database.collection(Constant.Key_collection_users)
                .document(preferenceManager.getString(Constant.Key_USER_ID));

    }
    @Override
    protected void onPause()
    {
        super.onPause();
        documentReference.update(Constant.Key_Avility,0);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        documentReference.update(Constant.Key_Avility,1);

    }
}