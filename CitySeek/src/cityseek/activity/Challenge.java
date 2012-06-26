package cityseek.activity;

public class Challenge {
	
	public static final double DEFAULT_RADIUS = 0.001;
	
	private double latitude;
	private double longitude;
	private double radius;
	private String question;
	private String answer;
	
	public Challenge(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		radius = DEFAULT_RADIUS;
	}
	
	public Challenge(double latitude, double longitude, double radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}
	
	private double calculateDistance(double long1, double lat1, double long2, double lat2) {
		double a = Math.abs(long1-long2);
    	double b = Math.abs(lat1-lat2);
    	return Math.sqrt((a*a)+(b*b));
    }
	
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public boolean isInside(double longi, double lat) {
	    return (calculateDistance(longitude, latitude, longi, lat) <= radius);
	}
}
