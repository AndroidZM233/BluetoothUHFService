package speedata.com.uhfservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 张明_ on 2017/8/14.
 */

public class PowerStateChangeReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        if ("android.intent.action.ACTION_POWER_CONNECTED".equals(intent
                .getAction())) {
            //Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
        } else if ("android.intent.action.ACTION_POWER_DISCONNECTED"
                .equals(intent.getAction())) {
            //Toast.makeText(context, "DISCONNECTED", Toast.LENGTH_SHORT).show();
            try {
                Intent ootStopIntent = new Intent(context, UHFService.class);
                ootStopIntent.setPackage("speedata.com.uhfservice");
                context.stopService(ootStopIntent);
            } catch (Exception e) {
                e.printStackTrace();
            }

            new Thread(new shutdownThread()).start();
        }
    }

    /**
     * Shutdown
     *
     * @param context
     */
    public void shutdown(Context context) {
        try {
            Intent intent = new Intent(
                    "android.intent.action.ACTION_REQUEST_SHUTDOWN");

            intent.putExtra("android.intent.extra.KEY_CONFIRM", false);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public class shutdownThread implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                shutdown(context);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



