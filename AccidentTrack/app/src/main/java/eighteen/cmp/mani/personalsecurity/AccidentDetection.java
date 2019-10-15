package eighteen.cmp.mani.personalsecurity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class AccidentDetection extends AppCompatActivity implements AccelerometerListener {
    TextView xval, yval, zval;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_detection);

        xval = (TextView) findViewById(R.id.xlabel);
        yval = (TextView) findViewById(R.id.ylabel);
        zval = (TextView) findViewById(R.id.zlabel);

        xval.setText("x:0");
        yval.setText("y:0");
        zval.setText("z:0");
    }

    @Override
    public void onAccelerationChanged(float x, float y, float z) {
        xval.setText("X:" + x);
        yval.setText("Y:" + y);
        zval.setText("Z:" + z);
    }

    @Override
    public void onShake(float force) {


    }
    @Override
    public void onResume() {
        super.onResume();
        // Toast.makeText(getBaseContext(), "onResume Accelerometer Started",
        // Toast.LENGTH_LONG).show();

        // Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isSupported(this)) {

            // Start Accelerometer Listening
            AccelerometerManager.startListening(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            // Start Accelerometer Listening
            AccelerometerManager.stopListening();

            // Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
            // Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Sensor", "Service  distroy");

        // Check device supported Accelerometer senssor or not
        if (AccelerometerManager.isListening()) {

            // Start Accelerometer Listening
            AccelerometerManager.stopListening();

            // Toast.makeText(getBaseContext(),
            // "onDestroy Accelerometer Stoped",
            // Toast.LENGTH_LONG).show();
        }

    }

    protected void sendSMSMessage(String mobileno) {
        Log.i("Send SMS", "");
        String phoneNo = "";
        String message = "Your Friend Was An Accident ";

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }

        catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "SMS faild, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        //Intent intent = new Intent(MainActivity.this, NotificationReceiver.class);
        // PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = new Notification.Builder(this)

                    .setContentTitle("Accident Detected")
                    .setContentText("You Are Strugled In Accident Emergency Alert Send With In 10 Secs")
                    .setSmallIcon(R.drawable.hit)

                    .setSound(soundUri)



                    .build();
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }

    public void cancelNotification(int notificationId){

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }

}
