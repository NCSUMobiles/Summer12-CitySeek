package cityseek.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private String question;
	private String answer;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);
		
		Intent intent = getIntent();
		question = intent.getStringExtra("question");
		answer = intent.getStringExtra("answer");

		final EditText editText = (EditText) findViewById(R.id.editText1);
		TextView q = (TextView) findViewById(R.id.question);
		
		q.setText(question);

		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = editText.getText().toString();
				if (text.equalsIgnoreCase(answer)) {
					Toast.makeText(getBaseContext(), "Correct!",
							Toast.LENGTH_SHORT).show();
				} else {
					editText.clearComposingText();
					Toast.makeText(getBaseContext(), "Try again",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
