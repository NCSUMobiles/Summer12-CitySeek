package cityseek.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;


public class StartupActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        VideoView video = (VideoView) findViewById(R.id.startup);

        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" 
        + R.raw.startup); 

        video.setVideoURI(videoPath);
        video.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
           	    newActivity();
            }
        });

        video.start();
    }
    
    public void newActivity() {
    	Intent intent = new Intent(this, MenuActivity.class);
		startActivity(intent);
    }
}
