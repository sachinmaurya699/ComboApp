package com.sos.comboapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sos.comboapp.Calss.Constant;
import com.sos.comboapp.Firbase.Home_Activity;
import com.sos.comboapp.Firbase.SignUp_Activity;
import com.sos.comboapp.Utilites.PreferenceManager;
import com.sos.comboapp.databinding.ActivitySignInBinding;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity
{

    private ActivitySignInBinding binding;
    private PreferenceManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        manager= new PreferenceManager(this);
        if(manager.getBoolean(Constant.Key_IS_SIGNED_IN))
        {
            Intent intent = new Intent(getApplicationContext(), Home_Activity.class);
            startActivity(intent);
            finish();
        }
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setlisteners();
    }

    private void setlisteners()
    {
        binding.button.setOnClickListener(view ->
        {
            if(isValidSignInDetails())
            {
                signIn();
            }
        });

        binding.newAccount.setOnClickListener(view -> {

            startActivity(new Intent(SignInActivity.this, SignUp_Activity.class));

        });




    }
    private void  loading(Boolean isloading)
    {
        if(isloading)
        {
            binding.button.setVisibility(View.INVISIBLE);

        }else
        {
            binding.button.setVisibility(View.VISIBLE);
        }
    }
    private void signIn()
    {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constant.Key_collection_users)
                .whereEqualTo(Constant.Key_Email,binding.myEditEmail.getText().toString())
                .whereEqualTo(Constant.Key_Password,binding.myPassword.getText().toString())
                .get()
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful() && task.getResult()!=null && task.getResult().getDocuments().size()>0)
                    {
                        DocumentSnapshot documentSnapshot =task.getResult().getDocuments().get(0);
                        manager.putBoolean(Constant.Key_IS_SIGNED_IN,true);
                        manager.putString(Constant.Key_USER_ID,documentSnapshot.getId());
                        manager.putString(Constant.Key_Name,documentSnapshot.getString(Constant.Key_Name));
                        manager.putString(Constant.Key_IMAGE,documentSnapshot.getString(Constant.Key_IMAGE));
                        Intent intent= new Intent(getApplicationContext(),Home_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else
                    {
                        loading(false);
                        showToast("Unable to sign in");
                    }

                });


    }

    private void showToast(String message) {
        Toast.makeText(SignInActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignInDetails()
    {
        if (binding.myEditEmail.getText().toString().trim().isEmpty()) {
            showToast("Enter Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.myEditEmail.getText().toString()).matches()) {
            showToast("Enter valid Email");
            return false;
        } else if (binding.myPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter the Password");
        } else {
            return true;
        }
        return true;
    }
    private void adddatafirebase()
    {
        FirebaseFirestore database= FirebaseFirestore.getInstance();
        HashMap<String,Object> data= new HashMap<>();
        data.put("first_name","Chirag");
        data.put("last_name","kachi");
        database.collection("users")
                .add(data)
                .addOnSuccessListener(documentReference ->
                {
                    Toast.makeText(SignInActivity.this, "data inserted", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(Exception ->
                {
                    Toast.makeText(SignInActivity.this, "data exception"+Exception.toString(), Toast.LENGTH_SHORT).show();

                }) ;

    }

}