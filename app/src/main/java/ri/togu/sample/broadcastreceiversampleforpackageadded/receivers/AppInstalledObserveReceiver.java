package ri.togu.sample.broadcastreceiversampleforpackageadded.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by a13694 on 14/12/22.
 */
public class AppInstalledObserveReceiver extends BroadcastReceiver {

    private static final String TAG = AppInstalledObserveReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG, "onReceive action:" + action);
        String packagePath = intent.getDataString();
        Log.d(TAG, "onReceive packagePath:" + packagePath);
        Log.d(TAG, "onReceive myPid:" + android.os.Process.myPid());
    }
}
