package cityseek.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;

public class HotColdActivity extends Activity {
	
	private static final double THERE = 0.00001;
	
	private double targetLongitude;
	private double targetLatitude;
	private double radius;
	private double radius1;
	private double radius2;
	private double radius3;
	private double radius4;
	private String question;
	private String answer;
	private LocationListener locationListener;
	private LocationManager locationManager;
	private ImageView relative;
	private ImageView overall;
	private double previousDistance = -1;
	private short currentRadius = -1;
	
	public void onCreate(Bundle savedInstanceState) {
        
		// Standard Stuff
		super.onCreate(savedInstanceState);
        setContentView(R.layout.hotcold);
        
        getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // Set up GPS
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSListener();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 2, locationListener);
	
	    // Images
        overall = (ImageView) findViewById(R.id.overall);
	    relative = (ImageView) findViewById(R.id.relative);
	    
	    // variables
	    Intent i = getIntent();
	    question = i.getStringExtra("question");
	    answer = i.getStringExtra("answer");
	    targetLongitude = i.getDoubleExtra("longitude", 361);
	    targetLatitude = i.getDoubleExtra("latitude", 361);
	    radius = i.getDoubleExtra("radius", -1);
	    radius1 = radius / 5;
	    radius2 = radius1 * 2;
	    radius3 = radius1 * 3;
	    radius4 = radius1 * 4;
	}
	
   /**
    * Calculates distance IN DEGREES
    */
	private double calculateDistance(double long1, double lat1, double long2, double lat2) {
    	
		double a = Math.abs(long1-long2);
    	double b = Math.abs(lat1-lat2);
    	return Math.sqrt((a*a)+(b*b));
    }
	
	private void updateRelativeDisplay(double distance) {
		
		if (previousDistance != -1) {
			if (distance > previousDistance) {
				relative.setImageResource(R.drawable.colder);
			} else {
				if (currentRadius == 5) {
					relative.setImageResource(R.drawable.hottext);
				} else if (currentRadius == 4) {
					relative.setImageResource(R.drawable.warmer3);
				} else if (currentRadius == 3) {
					relative.setImageResource(R.drawable.warmer2);
				} else {
					relative.setImageResource(R.drawable.warmer1);
				}
			}
		}
		previousDistance = distance;
	}
	
	private void updateOverallDisplay(double distance) {
		
		if (distance > radius4) {
			overall.setImageResource(R.drawable.cold);
			currentRadius = 1;
		} else if (distance > radius3) {
			overall.setImageResource(R.drawable.cool);
			currentRadius = 2;
		} else if (distance > radius2) {
			overall.setImageResource(R.drawable.mid);
			currentRadius = 3;
		} else if (distance > radius1) {
			overall.setImageResource(R.drawable.warm);
			currentRadius = 4;
		} else {
			overall.setImageResource(R.drawable.hot);
			currentRadius = 5;
		}
	}
	
	private void checkForComplete(double distance) {
		
		if (distance <= THERE) {
			Intent intent = new Intent(this, QuizActivity.class);
			intent.putExtra("question", question);
			intent.putExtra("answer", answer);
			startActivity(intent);
		}
	}
	
	private class GPSListener implements LocationListener {

		public void onLocationChanged(Location location) {
			double distance = calculateDistance(location.getLongitude(), location.getLatitude(), 
				targetLongitude, targetLatitude);
			updateOverallDisplay(distance);
			updateRelativeDisplay(distance);
			checkForComplete(distance);
		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
    }
}
