/**
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */package owusu.agyei.liz.tryy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        try {
            if (remoteMessage.getData() != null) {

                BaseActivity.addNotificationToCache(this, remoteMessage);
                if (!MyApp.isActivityVisible()) {
                    showNotificationInSystemTray(remoteMessage);
                }
                Intent intent = new Intent();
                intent.setAction("ACTION_DETAILS");
                intent.putExtra("texting", (remoteMessage.getData().containsKey("texting")) ? remoteMessage.getData().get("texting") : "");
                sendBroadcast(intent);
                Log.d(TAG, "FCM Message Id: " + remoteMessage.getMessageId());
                //Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification().getBody());
                Log.d(TAG, "FCM Data Message: " + remoteMessage.getData());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void showNotificationInSystemTray(RemoteMessage remoteMessage) {

        String paddedMessage = (BaseActivity.getNotificationCount(this) > 1 ? " and "+ (BaseActivity.getNotificationCount(this) - 1) + " more Notifications":"");
        Intent actionIntent = new Intent("ACTIVITY_DETAILS");
        actionIntent.putExtra("texting", (remoteMessage.getData().containsKey("texting")) ? remoteMessage.getData().get("texting") : "");
        PendingIntent pi = PendingIntent.getActivity(this, 0, actionIntent, 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker((remoteMessage.getData().containsKey("title")) ? remoteMessage.getData().get("title") : "")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle((remoteMessage.getData().containsKey("title")) ? remoteMessage.getData().get("title") : "")
                .setContentText((remoteMessage.getData().containsKey("description")) ? remoteMessage.getData().get("description")+ paddedMessage: "")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);

    }
}
