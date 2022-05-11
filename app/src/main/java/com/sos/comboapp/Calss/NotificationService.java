package com.sos.comboapp.Calss;

import android.app.Notification;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sos.comboapp.MainActivity;
import com.sos.comboapp.R;

public class NotificationService extends FirebaseMessagingService
{

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message)
    {
        super.onMessageReceived(message);

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("All_data", "From: " + message.getFrom());

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>()
                {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("all_value", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(Integer.parseInt("hhhh"), token);
                        Log.d("My_value", msg);
                        Toast.makeText(NotificationService.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });






        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(123, notification);


    }

    private void handleNow()
    {


    }

    private void scheduleJob()
    {


    }
}
