package eighteen.cmp.mani.personalsecurity;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class Accelarometerservice extends Service implements
		SensorEventListener,LocationListener {
	//DBAdapter db;
	DatabaseHelper db;
	public static String loc;
	//GPSTracker gps;
	String s1,s2;
	LocationManager locationManager;
	/*double latitude, longtitude;
	private static final int FORCE_THRESHOLD = 350;
	private static final int TIME_THRESHOLD = 100;
	private static final int SHAKE_TIMEOUT = 500;
	private static final int SHAKE_DURATION = 1000;
	private static final int SHAKE_COUNT = 3;*/

	double latitude, longtitude;
	private static final int FORCE_THRESHOLD = 750;
	private static final int TIME_THRESHOLD = 100;
	private static final int SHAKE_TIMEOUT = 2000;
	private static final int SHAKE_DURATION = 5000;
	private static final int SHAKE_COUNT = 3;
	Double lat,lan;
	final static public String HEART="heart";
	final static public String TEMP="temp";
	public final static String HEALTH="hh";

	final static public String BG="bg";
	// private SensorManager mSensorMgr;
	private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
	private long mLastTime;
	// private OnShakeListener mShakeListener;
	private Context mContext;
	private int mShakeCount = 0;
	private long mLastShake;
	private long mLastForce;
	String heart,bg,hh;
	String locc;
	DatabaseHelper dbh;
	String temp,name,age;
	final static public String NAME="name";

	SensorManager mSensorEventManager;

	Sensor mSensor;

	// BroadcastReceiver for handling ACTION_SCREEN_OFF.
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Check action just to be on the safe side.
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				//Log.v("shake mediator screen off", "trying re-registration");
				// Unregisters the listener and registers it again.
				mSensorEventManager
						.unregisterListener(Accelarometerservice.this);
				mSensorEventManager.registerListener(Accelarometerservice.this,
						mSensor, SensorManager.SENSOR_DELAY_NORMAL);
			}
		}
	};

	@Override
	public void onCreate() {
		super.onCreate();

		db = new DatabaseHelper(Accelarometerservice.this);
		Log.v("shake service startup", "registering for shake");
		//gps = new GPSTracker(Accelarometerservice.this);

		mContext = getApplicationContext();
		// Obtain a reference to system-wide sensor event manager.
		mSensorEventManager = (SensorManager) mContext
				.getSystemService(Context.SENSOR_SERVICE);

		// Get the default sensor for accel
		mSensor = mSensorEventManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		// Register for events.
		mSensorEventManager.registerListener(this, mSensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		// TODO I'll only register at screen off. I don't have a use for shake
		// while not in sleep (yet)

		// Register our receiver for the ACTION_SCREEN_OFF action. This will
		// make our receiver
		// code be called whenever the phone enters standby mode.



/*	SharedPreferences sp=getSharedPreferences(HEART,Context.MODE_PRIVATE);
		heart=sp.getString("heart","");

		SharedPreferences sp1=getSharedPreferences(TEMP,Context.MODE_PRIVATE);
		temp=sp1.getString("temp","");


		SharedPreferences spb=getSharedPreferences(BG,Context.MODE_PRIVATE);
		bg=spb.getString("bg","");

		SharedPreferences sph=getSharedPreferences(HEALTH,Context.MODE_PRIVATE);
		hh=sph.getString("hh","");

		SharedPreferences sp2=getSharedPreferences(NAME,Context.MODE_PRIVATE);
		name=sp2.getString("name","");
		dbh=new DatabaseHelper(Accelarometerservice.this);
		dbh.open();
		age=dbh.retrieveage(name);


		Toast.makeText(getApplicationContext(),
				"Heart"+heart, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),
				"Temp"+temp, Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),
				"Name"+name, Toast.LENGTH_LONG).show();*/
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(mReceiver, filter);
		getLocation();

	}

	@Override
	public void onDestroy() {
		// Unregister our receiver.
		unregisterReceiver(mReceiver);

		// Unregister from SensorManager.
		mSensorEventManager.unregisterListener(this);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// We don't need a IBinder interface.
		return null;
	}

	// -------------end of the tutorial besides the accuracy and sensor change
	// stubs

	public void onShake() {
		// Poke a user activity to cause wake?
		Log.v("onShake", "doing wakeup");
		// send in a broadcast for exit request to the main mediator

		showNotification();
		getLocation();
		sendalertmessage();
		sendalertmessage1();

	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// not used right now
	}

	// Used to decide if it is a shake
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
			return;

		Log.v("sensor", "sensor change is verifying");
		long now = System.currentTimeMillis();

		if ((now - mLastForce) > SHAKE_TIMEOUT) {
			mShakeCount = 0;
		}

		if ((now - mLastTime) > TIME_THRESHOLD) {
			long diff = now - mLastTime;
			float speed = Math.abs(event.values[SensorManager.DATA_X]
					+ event.values[SensorManager.DATA_Y]
					+ event.values[SensorManager.DATA_Z] - mLastX - mLastY
					- mLastZ)
					/ diff * 10000;
			if (speed > FORCE_THRESHOLD) {
				if ((++mShakeCount >= SHAKE_COUNT)
						&& (now - mLastShake > SHAKE_DURATION)) {
					mLastShake = now;
					mShakeCount = 0;

					// call the reaction you want to have happen
					onShake();
				}
				mLastForce = now;
			}
			mLastTime = now;
			mLastX = event.values[SensorManager.DATA_X];
			mLastY = event.values[SensorManager.DATA_Y];
			mLastZ = event.values[SensorManager.DATA_Z];
		}

	}

	public void showNotification() {

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// intent triggered, you can add other intent for other actions
		// Intent intent = new Intent(MainActivity.this,
		// NotificationReceiver.class);
		// PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,
		// 0, intent, 0);

		// this is it, we'll build the notification!
		// in the addAction method, if you don't want any icon, just set the
		// first param to 0
		Notification mNotification = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
			mNotification = new Notification.Builder(this)

                    .setContentTitle("Location Traced")
                    .setContentText(
                            "Your Location traced and sent to Saved Numbers")
                    .setSmallIcon(R.drawable.hit)

                    .setSound(soundUri)

                    .build();
		}

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// If you want to hide the notification after it was selected, do the
		// code below
		// myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, mNotification);
	}

	public void cancelNotification(int notificationId) {

		if (Context.NOTIFICATION_SERVICE != null) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager nMgr = (NotificationManager) getApplicationContext()
					.getSystemService(ns);
			nMgr.cancel(notificationId);
		}
	}

	public void sendalertmessage() {
		try {
			db.open();
			Cursor c = db.getAllContacts1();
			if (c.moveToFirst()) {
				do {
					// DisplayContact(c);
					Toast.makeText(getApplicationContext(),"string   "+c.getString(0),Toast.LENGTH_SHORT).show();
					sendSMSMessage(c.getString(0));

				} while (c.moveToNext());
			}
			db.close();
		} catch (Exception exp) {
			System.out.println("Message   " + exp.getMessage());
		}
	}
	
	
	public void sendalertmessage1() {
		try {
			db.open();
			Cursor c = db.getAllTemp();
			if (c.moveToFirst()) {
				do {
					// DisplayContact(c);
					s1=c.getString(0);
					s2=c.getString(1);

				} while (c.moveToNext());
			}
			db.close();
		} catch (Exception exp) {
			System.out.println("Message" + exp.getMessage());
		}
	}


	protected void sendSMSMessage(String phone) {
	
		Log.i("Send SMS", "");
		String phoneNo = phone;

		getLocation();
/*
		int age_res=Integer.parseInt(age);
		int res_heart=Integer.parseInt(heart);
		int res_temp=Integer.parseInt(temp);*/
		String msg1="Location: http://maps.google.com/?q="+ latitude + "," + longtitude;
		String message = "Location traced  "+temp;

		try {
			android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNo, null, msg1, null, null);
			Toast.makeText(getApplicationContext(), "SMS sent.",
					Toast.LENGTH_LONG).show();
		}

		catch (Exception e) {
			Toast.makeText(getApplicationContext(),
					"SMS faild, please try again.", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		/*if(age_res < 7 && res_heart > 140 && res_temp > 100 ){




			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
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
		if(age_res > 7 && age_res < 14 &&  res_heart > 85 && res_temp > 100 ){




			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
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

		if(age_res > 14 && age_res < 19 &&  res_heart > 80 && res_temp > 100 ){




			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
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


		if(age_res > 19 && age_res < 30 &&  res_heart > 114 && res_temp > 100 ){




			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
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

		if(age_res > 30  &&  res_heart > 144 && res_temp > 100 ){




			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
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
else{
			try {
                android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
				Toast.makeText(getApplicationContext(), "SMS sent.",
						Toast.LENGTH_LONG).show();
			}

			catch (Exception e) {
				Toast.makeText(getApplicationContext(),
						"SMS faild, please try again.", Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}*/

	}

	@Override
	public void onLocationChanged(Location location) {
		/*try {
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


				lat=location.getLatitude();
			lan=location.getLongitude();
		}catch(Exception e)
		{

		}*/

		try {
			latitude=location.getLatitude();
			longtitude=location.getLongitude();
			locc="Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude();
			Geocoder geocoder = new Geocoder(this, Locale.getDefault());
			List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
			temp=addresses.get(0).getAddressLine(0);


		}catch(Exception e)
		{

		}
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {
Toast.makeText(getApplicationContext(),"Enable GPS",Toast.LENGTH_SHORT).show();
	}
	//}

	void getLocation() {
		try {
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);

		}
		catch(SecurityException e) {
			e.printStackTrace();
		}
	}

}