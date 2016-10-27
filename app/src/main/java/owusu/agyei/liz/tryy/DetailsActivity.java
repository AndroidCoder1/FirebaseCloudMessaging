package owusu.agyei.liz.tryy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

public class DetailsActivity extends BaseActivity {

    TextView detailsText;
    Intent intent;
    String details = "";
    String TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Show back button in app
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        setDetailsText();

    }

    public void setDetailsText() {
        details = getNotificationContent(DetailsActivity.this);
        detailsText = (TextView) findViewById(R.id.textDisplay);
        detailsText.setText(details);
        //Randomise text color
        randomDetailsTextColor(detailsText);
    }

    private void randomDetailsTextColor(TextView textView){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        textView.setTextColor(color);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Notication broadcast recieved when app is in foreground
            setDetailsText();
        }
    };

    private void registerNotificationReceivedReciever(){
        IntentFilter filter = new IntentFilter("ACTION_DETAILS");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Register receiver to changet text color when notification is received
        registerNotificationReceivedReciever();

    }

    @Override
    protected void onPause() {
        super.onPause();
        BaseActivity.clearNotificationToCache(this);
        unregisterReceiver(receiver);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Check if this activity is the first in the stack, if so go to default activity else just finish
                if (isTaskRoot()) {
                    startActivity(new Intent(DetailsActivity.this, MainActivity.class));
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
