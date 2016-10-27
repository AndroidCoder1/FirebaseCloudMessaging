package owusu.agyei.liz.tryy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends BaseActivity {

    RecyclerView recyclerView;
    Button notificationBtn;
    String detailsText = "";
    String[] friendsList = {"Lisa", "Ben", "Padmore", "Paul", "Martin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.friendsRecyclerView);
        notificationBtn = (Button) findViewById(R.id.notificationBtn);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(new RecyclerViewAdapter(MainActivity.this, friendsList));

        setButtonClickListener(notificationBtn);
        subscribeToTopics(friendsList);

    }

    private void setButtonClickListener(Button btn){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("texting",detailsText);
                startActivity(intent);
            }
        });
    }

    private void subscribeToTopics(final String[] friendsList){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (String topic : friendsList) {
                    FirebaseMessaging.getInstance().subscribeToTopic(topic);
                }
            }
        }).start();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Notication broadcast recieved when app is in foreground
            detailsText = (intent.hasExtra("texting")) ? intent.getStringExtra("texting") : "";
            notificationBtn.setVisibility(View.VISIBLE);
        }
    };

    private void registerNotificationReceivedReciever(){
        IntentFilter filter = new IntentFilter("ACTION_DETAILS");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register receiver to change text color when notification is received
        registerNotificationReceivedReciever();
        if(getNotificationCount(MainActivity.this) > 0){
            notificationBtn.setVisibility(View.VISIBLE);
        }else {
            notificationBtn.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
