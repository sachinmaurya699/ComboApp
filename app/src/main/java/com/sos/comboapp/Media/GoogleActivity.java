package com.sos.comboapp.Media;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.sos.comboapp.First_Activity;
import com.sos.comboapp.R;

public class GoogleActivity extends AppCompatActivity
{

    private GoogleSignInClient client;
    private SignInButton  sign_button;
    private int RC_SIGN_IN=100;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
       client = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        sign_button=findViewById(R.id.sign_in_button);
        sign_button.setSize(SignInButton.SIZE_STANDARD);
        sign_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signIn();
            }
        });

    }

    private void signIn()
    {
        Intent signInIntent =  client.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask)
    {
        try {
              account = completedTask.getResult(ApiException.class);

            account = GoogleSignIn.getLastSignedInAccount(GoogleActivity.this);
            if (account != null)
            {
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();

                SharedPreferences preferences=getSharedPreferences("My_detail",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("Personal_name",personName);
                editor.putString("personal_fmaily",personFamilyName);
                editor.putString("personal_email",personEmail);
                editor.putString("personal_id",personId);
                editor.putString("persolnal_img", String.valueOf(personPhoto));
                editor.apply();

            }

            Intent intent = new Intent(GoogleActivity.this, First_Activity.class);
            startActivity(intent);

            // Signed in successfully, show authenticated UI.
           // updateUI(account);

        } catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("My_life", "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
        }


    }


}