package com.example.test.hit.ball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.code.Game;

public class PrincipalMenu extends Activity {

	private Game game;
	private Vibrator v;
	public final static String EXTRA_MESSAGE = "com.example.test.hit.ball";
	private Button btPlay, btOptions, btRecords, btExit;
	private TextView txtHeader;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal_menu);
		game = new Game("Fácil", true, false);
		txtHeader = (TextView) findViewById(R.id.txtHeader);
		Typeface font = Typeface.createFromAsset(getAssets(), "fonts/jonzejonzing.ttf");
		txtHeader.setTypeface(font);
		txtHeader.setTextColor(Color.argb(255, 228, 240, 239));
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		txtHeader.setTextSize(getResources().getDisplayMetrics().density*170);
		txtHeader.setTextScaleX(1.0f);
		btPlay = (Button) findViewById(R.id.btPlay);
		btOptions = (Button) findViewById(R.id.btOptions);
		btOptions.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showOptions(v);
			}
		});
		btExit = (Button) findViewById(R.id.btExit);
		btExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		loadConfiguration();
		
	}

	public void playGame(View view) {
		startActivity(new Intent(this, GameActivity.class));
	}

	public void showOptions(View view) {
		startActivity(new Intent(this, OptionsPrinMenu.class));
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		startActivity(new Intent(this, OptionsPrinMenu.class));
		return true;
	}
	
	// Load configuration at the start of application
	@Override
	protected void onStart() {
		super.onStart();
		loadConfiguration();
	}
	
	public void loadConfiguration() {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		game.setSound(prefs.getBoolean("SOUND", true));
		game.setVibrate(prefs.getBoolean("VIBRATE", true));
		game.setDifficulty(prefs.getString("DIFFICULTY", getString(R.string.textDifficultyEasy)));
	}
	
	public void goAbout(View view){
		startActivity(new Intent(this, About.class));
	}
	
}
