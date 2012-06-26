package cityseek.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InstructionsActivity extends Activity{
	
	private String instructions;
	private double lat;
	private double longi;
	private double rad;
	private String question;
	private String answer;
	private Class nextActivity;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		
		Intent i = getIntent();
		nextActivity = (Class)i.getSerializableExtra("class");
		instructions = i.getStringExtra("instructions");
		lat = i.getDoubleExtra("latitude", 361);
		longi = i.getDoubleExtra("longitude", 361);
		rad = i.getDoubleExtra("radius", 361);
		question = i.getStringExtra("question");
		answer = i.getStringExtra("answer");
		
		TextView text = (TextView) findViewById(R.id.instructions);
		text.setText(instructions);
		
		Button cont = (Button) findViewById(R.id.cont);
		cont.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				start();
			}
		});
	}
	
	private void start() {
		Intent i = new Intent(this, nextActivity);
		i.putExtra("latitude", lat);
		i.putExtra("longitude", longi);
		i.putExtra("radius", rad);
		i.putExtra("question", question);
		i.putExtra("answer", answer);
		startActivity(i);
	}

}
