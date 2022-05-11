package com.sos.comboapp.Media;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sos.comboapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Facebook_Activity extends AppCompatActivity
{

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private LoginButton  loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook2);
        //loginButton.setReadPermissions("email", "public_profile", "user_friends");
        LoginManager.getInstance().logInWithReadPermissions(Facebook_Activity.this, Arrays.asList("public_profile"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                handleFacebookAccessToken(loginResult.getAccessToken());

                Intent intent = new Intent(Facebook_Activity.this,Facebook_Home_Activity.class);

                startActivity(intent);
                finish();
                Toast.makeText(Facebook_Activity.this, "login_success"+loginResult.toString(), Toast.LENGTH_SHORT).show();

              /*  GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {

                        Log.d("res", object.toString());
                        Log.d("res_obj", response.toString());

                        try {

                            String id = object.getString("id");
                            try {
                                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                                Log.i("profile_pic", profile_pic + "");

                                String f_name = object.getString("first_name");
                                String l_name = object.getString("last_name");
                                String name = f_name + " " + l_name;
                                String email = object.getString("email");
                                String image = profile_pic.toString();

                                Log.d("data", email + name + image);
                                String type = "facebook";

                                if (email == null)
                                {

                                }
                            } catch (MalformedURLException e)
                            {
                                Toast.makeText(Facebook_Activity.this, "exception"+e.toString(), Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }


                        } catch (JSONException e)
                        {
                            Toast.makeText(Facebook_Activity.this, "exception2"+e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }

                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender");
                request.setParameters(parameters);
                request.executeAsync();*/
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(Facebook_Activity.this, "cancel by user", Toast.LENGTH_SHORT).show();
                Log.d("fb_exception", "cancel by user");
            }

            @Override
            public void onError(FacebookException exception)
            {
                Log.d("fb_exception", exception.toString());
                Toast.makeText(Facebook_Activity.this, "exception"+exception.toString(), Toast.LENGTH_SHORT).show();

            }

        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LoginManager.getInstance().logInWithReadPermissions(Facebook_Activity.this, Arrays.asList("public_profile"));
                Toast.makeText(Facebook_Activity.this, "Facebook login", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void handleFacebookAccessToken(AccessToken accessToken)
    {
       // Log.d(TAG, "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = auth.getCurrentUser();
                            updateUI(user);

                        } else
                            {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Facebook_Activity.this, "Authentication failed"+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                           }
                    }
                });

    }
    private void updateUI(FirebaseUser user)
    {
        Intent intent = new Intent(Facebook_Activity.this,Facebook_Home_Activity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}