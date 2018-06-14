package hami.hamibelit.Reminder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UtilReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Rise and Shine!", Toast.LENGTH_LONG).show();
    }
}
