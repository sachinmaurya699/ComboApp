package com.sos.comboapp.Firbase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessingService extends FirebaseMessagingService
{
    @Override
    public void onNewToken(@NonNull String token)
    {
        super.onNewToken(token);
        Log.d("FCM","Token"+token);
        //Toast.makeText(getApplicationContext(), ""+token, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        Log.d("FCM","message"+remoteMessage.getNotification().getBody());

    }

}
