package com.sos.comboapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sos.comboapp.R;
import com.sos.comboapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity
{

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    private EditText editText_email;
    private EditText editText_password;
    private String Email,Password;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());







        binding.myLoginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Email=binding.myEmail.getText().toString();
                Password=binding.myPasswordId.getText().toString();

                if(Email.isEmpty())
                {
                    binding.myEmail.setError("Enter the Email");
                    binding.myEmail.setFocusable(true);

                }else if(Password.isEmpty())
                {
                    binding.myEmail.setError("Enter the password");
                    binding.myEmail.setFocusable(true);
                }else
                {
                    my_login(Email,Password);
                }

            }
        });

    }

    private void my_login(String email, String password)
    {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if (task.isSuccessful())
                        {

                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);

                            showgetdata();


                        } else
                            {

                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);

                            }
                    }
                });



    }

    private void showgetdata()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }



    }
}