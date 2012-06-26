package cityseek.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ImageView;

public class ChallengeActivity extends Activity {

	private double targetLatitude;
	private double targetLongitude;
	
	private static final double RADIUS = 0.00001;

	private LocationListener locationListener;
	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge);
		ImageView image = (ImageView) findViewById(R.id.art);
		
		Intent i = getIntent();
		targetLatitude = i.getDoubleExtra("latitude", 361);
		targetLongitude = i.getDoubleExtra("longitude", 361);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		locationListener = new GPSListener();

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 3, locationListener);
	}

	public void newActivity() {

		Intent intent = new Intent(this, QuizActivity.class);
		
		startActivity(intent);
	}

	private class GPSListener implements LocationListener {

		public void onLocationChanged(Location location) {

			if (calculateDistance(location.getLongitude(), location.getLatitude(),
					targetLongitude, targetLatitude) <= RADIUS) {
				newActivity();
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
	
    private double calculateDistance(double long1, double lat1, double long2, double lat2) {
    	
		double a = Math.abs(long1-long2);
    	double b = Math.abs(lat1-lat2);
    	return Math.sqrt((a*a)+(b*b));
    }
}
