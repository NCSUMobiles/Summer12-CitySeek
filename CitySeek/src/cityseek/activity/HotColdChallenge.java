package cityseek.activity;

import android.content.Context;
import android.content.Intent;

public class HotColdChallenge extends Challenge {
	
	private Location goal;
	
	public HotColdChallenge(double latitude, double longitude) {
		super(latitude, longitude);
	}
	
	public HotColdChallenge(double latitude, double longitude, double radius) {
		super(latitude, longitude, radius);
	}

	public void setGoal(Location location) {
		goal = location;
	}
	
	public Intent makeIntent(Context con) {
		Intent i = new Intent(con, HotColdActivity.class);
		i.putExtra("latitude", goal.getLatitude());
		i.putExtra("longitude", goal.getLongitude());
		i.putExtra("radius", super.getRadius());
		return i;
	}
}
