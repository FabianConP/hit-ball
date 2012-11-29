package com.example.test.hit.ball;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class OptionsPrinMenu extends Activity {

	private ToggleButton togBtSound, togBtVibrate;
	private Spinner spDifficulty;
	public final static String EXTRA_MESSAGE = "com.example.test.hit.ball";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_prin_menu);
		spDifficulty = (Spinner) findViewById(R.id.spDifficulty);
		togBtSound = (ToggleButton) findViewById(R.id.togBtSound);
		togBtSound.setChecked(true);
		togBtVibrate = (ToggleButton) findViewById(R.id.togBtVibrate);
		getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		loadConfiguration();
	}

	public void save(View view) {
		saveConfiguration();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_options_prin_menu, menu);
		return true;
	}

	// Save the configuration
	@Override
	public void onDestroy() {
		super.onDestroy();
		saveConfiguration();
	}

	// Saving the configuration in Android application using SharedPreferences
	public void saveConfiguration() {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean("SOUND", togBtSound.isChecked());
		editor.putBoolean("VIBRATE", togBtVibrate.isChecked());
		editor.putString("DIFFICULTY", spDifficulty.getSelectedItem()
				.toString());
		//editor.putStringSet("BESTSCORES", arg1)
		editor.commit();
	}

	// Load configuration at the start of application
	@Override
	protected void onStart() {
		super.onStart();
		loadConfiguration();
	}

	// Loading the configuration in Android application using SharedPreferences
	public void loadConfiguration() {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		togBtSound.setChecked(prefs.getBoolean("SOUND", true));
		togBtVibrate.setChecked(prefs.getBoolean("VIBRATE", true));
		String difficulty = prefs.getString("DIFFICULTY", "Fácil");
		if (difficulty.equals("Fácil"))
			spDifficulty.setSelection(0);
		else if (difficulty.equals("Medio"))
			spDifficulty.setSelection(1);
		else
			spDifficulty.setSelection(2);
	}
}
