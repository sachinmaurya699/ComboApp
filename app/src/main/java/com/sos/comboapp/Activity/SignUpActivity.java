package com.sos.comboapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.sos.comboapp.databinding.ActivitySignUp2Binding;
import com.sos.comboapp.databinding.ActivitySignUpBinding;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private ProgressDialog dialog;
    private ActivitySignUp2Binding binding;
    private SignInActivity signInActivity;
    private String Email,Password,Name,Conformpasswprd;
    private EditText editText_name;
    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();

        dialog=new ProgressDialog(SignUpActivity.this);

        binding.mySignupRegistr.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                Name=binding.mynameid.getText().toString();
                Email=binding.edittextEmailid.getText().toString();
                Password=binding.myPasswprdid.getText().toString();
                Conformpasswprd=binding.myPasswprdidConform.getText().toString();

                if(Name.isEmpty())
                {
                    binding.mynameid.setError("Enter the name");
                    binding.mynameid.setFocusable(true);

                }else if(Email.isEmpty())
                {
                    binding.edittextEmailid.setError("Enter the Email");
                    binding.edittextEmailid.setFocusable(true);

                }else if(!EMAIL_ADDRESS_PATTERN.matcher(Email).matches())
                {
                    Toast.makeText(SignUpActivity.this, "not valid email", Toast.LENGTH_SHORT).show();

                }
                else if(Password.isEmpty())
                {
                    binding.myPasswprdid.setError("Enter the Password");
                    binding.myPasswprdid.setFocusable(true);

                }
                else if(Conformpasswprd.isEmpty())
                {
                    binding.myPasswprdidConform.setError("Enter the Conform Password");
                    binding.myPasswprdidConform.setFocusable(true);

                }else if(!Password.equals(Conformpasswprd))
                {
                    Toast.makeText(SignUpActivity.this, "not password match", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    dialog.setMessage("Please wait while Registration...");
                    dialog.setTitle("Registration");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    //signup(Name,Email,Password,Conformpasswprd);

                    mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                dialog.dismiss();
                                sendusertonextActivity();
                                Toast.makeText(SignUpActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                            }else
                                {
                                dialog.dismiss();
                                Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    });


                }

            }
        });
    }

    private void sendusertonextActivity()
    {

        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            //reload();
        }
    }
}