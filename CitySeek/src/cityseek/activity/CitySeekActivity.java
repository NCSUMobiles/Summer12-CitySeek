package cityseek.activity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;
import cityseek.activity.R;
import cityseek.overlays.OurLocationOverlay;
import cityseek.overlays.PinsOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

public class CitySeekActivity extends MapActivity {
	
	//TEMPORARY HACK FOR PULLEN PARK
	public static final double PULLEN_LAT = 35.78096;
	public static final double PULLEN_LONG = -78.6647;
	public static final double PULLEN_RAD = 0.0025;
	public static final double STATUE_LAT = 35.779669;
	public static final double STATUE_LONG = -78.663963;
	public static final String INSTRUCTIONS = "You have entered Pullen Park! Arthur Fox is looking for " +
			"something specific in the park. Can you help him find it? The thermometer lets you know how " +
			"far away you are, and warmer/colder tells you if you're heading in the right direction.";
	public static final String QUESTION = "Andy Griffith's character in The Andy Griffith Show has " +
			"a different last name than \"Griffith\". What is it?";
	public static final String ANSWER = "Taylor";
	
	//AND FOR THE HEDRON
	public static final double HEDRON_LAT = 100;
	public static final double HEDRON_LONG = 100;
	public static final String HEDRON_INSTRUCTIONS = "Congratulations, you have found the Hedron! It looks" +
			" like Arthur Fox's picture of it is all scrambled. Can you help him arrange the tiles? " +
			"Slide a tile to complete the puzzle!";
	
	//AND FOR FIND THE ART
	public static final double MUSEUM_LAT = 150;
	public static final double MUSEUM_LONG = 150;
	public static final double ART_LAT = 150;
	public static final double ART_LONG = 150;
	public static final String MUSEUM_INSTRUCTIONS = "You have entered the NC Museum of art. Arthur Fox " +
			"is looking for a specific piece in the museum, but all he has is a picture. Can you help " +
			"him find it?";
	public static final String ART_QUESTION = "In what state was the artist who made this work born?";
    public static final String ART_ANSWER = "Massachusetts";
	
	MapView mapView;
	MapController mc;
 
	private LocationManager locationManager;
	private LocationListener locationListener;
	private ArrayList<cityseek.activity.Location> pins;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		initMapView();
		initMyLocation();
		pins = getIntent()
				.getParcelableArrayListExtra("pinList");
		initPointsOfIntrest(pins);
		// initPointsOfInterest();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationListener = new GPSLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 5, locationListener);
	}
	
	private void initMapView() {
		mapView = (MapView) findViewById(R.id.mapView);
		mc = mapView.getController();
		mc.setZoom(14);
		mapView.setBuiltInZoomControls(true);
	}

	private void initMyLocation() {
		final OurLocationOverlay overlay = new OurLocationOverlay(this, mapView);
		overlay.enableMyLocation();
		mapView.getOverlays().add(overlay);
	}

	private void initPointsOfIntrest(
			ArrayList<cityseek.activity.Location> pinList) {
		Drawable drawable = new BitmapDrawable(Bitmap.createScaledBitmap(
				((BitmapDrawable) getResources()
						.getDrawable(R.drawable.redflag)).getBitmap(), 100,
				100, false));
		PinsOverlay pinOverlay = new PinsOverlay(drawable, this);
		for (int p = 0; p < pinList.size(); p++) {
			cityseek.activity.Location pin = pinList.get(p);
			OverlayItem item = new OverlayItem(new GeoPoint(
					(int) (pin.getLatitude() * 1E6),
					(int) (pin.getLongitude() * 1E6)), pin.getName(),
					pin.getAddress());
			pinOverlay.addOverlay(item);
		}
		mapView.getOverlays().add(pinOverlay);
	}

	private void checkPoints(Location location) {
		if (calculateDistance(location.getLongitude(), location.getLatitude(),
				PULLEN_LONG, PULLEN_LAT) <= PULLEN_RAD) {
			Intent intent = new Intent(this, InstructionsActivity.class);
			intent.putExtra("class", HotColdActivity.class);
			intent.putExtra("latitude", STATUE_LAT);
			intent.putExtra("longitude", STATUE_LONG);
			intent.putExtra("radius", PULLEN_RAD);
			intent.putExtra("instructions", INSTRUCTIONS);
			intent.putExtra("question", QUESTION);
			intent.putExtra("answer", ANSWER);
			startActivity(intent);
		} else if (calculateDistance(location.getLongitude(), location.getLatitude(),
				HEDRON_LONG, HEDRON_LAT) <= PULLEN_RAD) {
			Intent intent = new Intent(this, InstructionsActivity.class);
			intent.putExtra("class", SlidingPuzzleActivity.class);
			intent.putExtra("instructions", HEDRON_INSTRUCTIONS);
			startActivity(intent);
		} else if (calculateDistance(location.getLongitude(), location.getLatitude(),
				MUSEUM_LONG, MUSEUM_LAT) <= PULLEN_RAD) {
			Intent intent = new Intent(this, InstructionsActivity.class);
			intent.putExtra("class", ChallengeActivity.class);
			intent.putExtra("instructions", MUSEUM_INSTRUCTIONS);
			intent.putExtra("latitude", ART_LAT);
			intent.putExtra("longitude", ART_LONG);
			intent.putExtra("question", ART_QUESTION);
			intent.putExtra("answer", ART_ANSWER);
			startActivity(intent);
		}
	}
	
	private double calculateDistance(double long1, double lat1, double long2, double lat2) {
		double a = Math.abs(long1-long2);
    	double b = Math.abs(lat1-lat2);
    	return Math.sqrt((a*a)+(b*b));
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				3000, 5, locationListener);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	private class GPSLocationListener implements LocationListener {

		public void onLocationChanged(Location location) {
			checkPoints(location);
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
}
