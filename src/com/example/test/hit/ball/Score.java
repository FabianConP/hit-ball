package com.example.test.hit.ball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Score extends Activity {

	private int score;
	private String difficulty;
	private TextView txtHeaderScore, txtBSEasy, txtBSMiddle, txtBSHard;
	private Button btPlayAgain, btGoHome;
	private String bestScoreEasy = "anon -> 0", bestScoreMiddle = "anon -> 0",
			bestScoreHard = "anon -> 0";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		txtBSEasy = (TextView) findViewById(R.id.txtBSEasy);
		txtBSMiddle = (TextView) findViewById(R.id.txtBSMiddle);
		txtBSHard = (TextView) findViewById(R.id.txtBSHard);
		loadBestScores();
		try {
			Intent intent = getIntent();
			score = intent.getIntExtra("SCORE", 0);
			if (intent.getStringExtra("DIFFICULTY").equals("")
					|| intent.getStringExtra("DIFFICULTY") == null)
				difficulty = getString(R.string.textDifficultyEasy);
			else
				difficulty = intent.getStringExtra("DIFFICULTY").toString();
			difficulty = intent.getStringExtra("DIFFICULTY");
			Toast.makeText(getApplicationContext(), score + " " + difficulty,
					Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		txtHeaderScore = (TextView) findViewById(R.id.txtHeaderScore);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/jonzejonzing.ttf");
		txtHeaderScore.setTypeface(font);
		txtHeaderScore
				.setTextSize(getResources().getDisplayMetrics().density * 70);
		txtHeaderScore.setTextScaleX(1.0f);
		txtHeaderScore.setTextColor(Color.argb(255, 228, 240, 239));
		loadTable();
		if (isBestScore(difficulty, score)) {
			dialogBS();
		} else
			dialogNoBS();
	}

	public void dialogBS() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);

		alert.setTitle(getString(R.string.text_scores_dialog_tittle));
		alert.setMessage(getString(R.string.text_scores_dialog_bestScore) + " "
				+ difficulty + ": " + score + "\n"
				+ getString(R.string.text_scores_dialog_inputName));

		// Set an EditText view to get user input
		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton(getString(R.string.text_scores_dialog_save),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String name = input.getText().toString();
						while (!name.equals(name.replaceAll("->", "")))
							name = name.replaceAll("->", "");
						SharedPreferences prefs = getSharedPreferences(
								"HitBall", Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = prefs.edit();
						if (difficulty
								.equals(getString(R.string.textDifficultyEasy))) {
							editor.putString("HSCOREEASY", name + " -> "
									+ String.valueOf(score));
						} else if (difficulty
								.equals(getString(R.string.textDifficultyMiddle))) {
							editor.putString("HSCOREMIDDLE", name + " -> "
									+ String.valueOf(score));
						} else if (difficulty
								.equals(getString(R.string.textDifficultyHard))) {
							editor.putString("HSCOREHARD", name + " -> "
									+ String.valueOf(score));
						}
						editor.commit();
						loadBestScores();
						loadTable();
					}
				});
		alert.show();
	}

	public void loadTable() {
		txtBSEasy.setText(getString(R.string.textDifficultyEasy) + ": "
				+ bestScoreEasy);
		txtBSMiddle.setText(getString(R.string.textDifficultyMiddle) + ": "
				+ bestScoreMiddle);
		txtBSHard.setText(getString(R.string.textDifficultyHard) + ": "
				+ bestScoreHard);
	}

	public void dialogNoBS() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.text_scores_dialog_tittle));
		builder.setMessage(getString(R.string.text_scores_dialog_message)
				+ ": " + score);
		builder.setPositiveButton("Ok", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
	}

	/*
	 * // Load bests scores at the start of application
	 * 
	 * @Override protected void onStart() { //super.onStart();
	 * 
	 * }
	 */

	// Loading the configuration in Android application using SharedPreferences
	public void loadBestScores() {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		bestScoreEasy = prefs.getString("HSCOREEASY", "anon -> 0");
		bestScoreMiddle = prefs.getString("HSCOREMIDDLE", "anon -> 0");
		bestScoreHard = prefs.getString("HSCOREHARD", "anon -> 0");
	}

	public boolean isBestScore(String dif, int score) {
		boolean ans = false;
		if (dif.equals(getString(R.string.textDifficultyEasy))) {
			if (score > Integer.parseInt(bestScoreEasy.split(" -> ")[1]))
				ans = true;
		} else if (dif.equals(getString(R.string.textDifficultyMiddle))) {
			if (score > Integer.parseInt(bestScoreMiddle.split(" -> ")[1]))
				ans = true;
		} else if (dif.equals(getString(R.string.textDifficultyHard))) {
			if (score > Integer.parseInt(bestScoreHard.split(" -> ")[1]))
				ans = true;
		}
		return ans;
	}

	public void playAgain(View view) {
		finish();
		startActivity(new Intent(getApplicationContext(), GameActivity.class));
	}

	public void goHome(View view) {
		finish();
		// startActivity(new Intent(getApplicationContext(),
		// PrincipalMenu.class));
	}

	public void restartRecords(View view) {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString("HSCOREEASY", "anon -> 0");
		editor.putString("HSCOREMIDDLE", "anon -> 0");
		editor.putString("HSCOREHARD", "anon -> 0");
		editor.commit();
		loadBestScores();
		loadTable();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
