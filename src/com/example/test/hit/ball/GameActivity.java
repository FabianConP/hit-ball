package com.example.test.hit.ball;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.code.Game;

public class GameActivity extends Activity {

	private Handler handlerTime = new Handler();
	private Handler[] handlerHoles = new Handler[6];
	private TextView txtTest1, txtTest2;
	private ImageView imgHole0, imgHole1, imgHole2, imgHole3, imgHole4,
			imgHole5;
	private Game game;
	private Vibrator v;
	private int speed;
	private MediaPlayer mp;

	Animation scaleAnim;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		LinearLayout llTittle = (LinearLayout) findViewById(R.id.llTittle);
		llTittle.setBackgroundColor(Color.argb(255, 34, 34, 34));
		txtTest1 = (TextView) findViewById(R.id.txtTest1);
		txtTest1.setTextColor(Color.argb(255, 249, 249, 249));
		txtTest2 = (TextView) findViewById(R.id.txtTest2);
		txtTest2.setTextColor(Color.argb(255, 249, 249, 249));
		imgHole0 = (ImageView) findViewById(R.id.imgHole0);
		imgHole1 = (ImageView) findViewById(R.id.imgHole1);
		imgHole2 = (ImageView) findViewById(R.id.imgHole2);
		imgHole3 = (ImageView) findViewById(R.id.imgHole3);
		imgHole4 = (ImageView) findViewById(R.id.imgHole4);
		imgHole5 = (ImageView) findViewById(R.id.imgHole5);
		mp = MediaPlayer.create(getApplicationContext(), R.raw.hitok);
		v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
		game = new Game("Fácil", true, true);
		restartValues();
		scaleAnim = AnimationUtils.loadAnimation(this, R.anim.scale);
		loadConfiguration();
		if (!game.isSound())
			deleteSoundEffectsButtons();
		if (game.getDifficulty().equals(getString(R.string.textDifficultyEasy))) {
			speed = 4000;
		} else if (game.getDifficulty().equals(
				getString(R.string.textDifficultyMiddle))) {
			speed = 2500;
		} else {
			speed = 1500;
		}
		startGame();
	}

	public void restartValues() {
		txtTest1.setText(getString(R.string.text_game_topbar_time) + ": 30");
		txtTest2.setText(getString(R.string.text_game_topbar_score) + ": 0");
		game.setScore(0);
		game.setTimeCounter(30);
		for (int i = 0; i < game.getHole().length; i++) {
			handlerHoles[i] = new Handler();
		}
		imgHole0.setImageResource(R.drawable.nosrcball);
		imgHole1.setImageResource(R.drawable.nosrcball);
		imgHole2.setImageResource(R.drawable.nosrcball);
		imgHole3.setImageResource(R.drawable.nosrcball);
		imgHole4.setImageResource(R.drawable.nosrcball);
		imgHole5.setImageResource(R.drawable.nosrcball);
		handlerTime.removeCallbacks(generalTime);
		handlerHoles[0].removeCallbacks(changeStateHole0);
		handlerHoles[1].removeCallbacks(changeStateHole1);
		handlerHoles[2].removeCallbacks(changeStateHole2);
		handlerHoles[3].removeCallbacks(changeStateHole3);
		handlerHoles[4].removeCallbacks(changeStateHole4);
		handlerHoles[5].removeCallbacks(changeStateHole5);
	}

	public void startGame() {
		try {
			handlerTime.postDelayed(generalTime, 1000); // delay 1 second
			handlerHoles[0].postDelayed(changeStateHole0,
					(long) (Math.random() * speed) + 1000); // delay 1 second
			handlerHoles[1].postDelayed(changeStateHole1,
					(long) (Math.random() * speed) + 1000); // delay 1 second
			handlerHoles[2].postDelayed(changeStateHole2,
					(long) (Math.random() * speed) + 1000); // delay 1 second
			handlerHoles[3].postDelayed(changeStateHole3,
					(long) (Math.random() * speed) + 1000); // delay 1 second
			handlerHoles[4].postDelayed(changeStateHole4,
					(long) (Math.random() * speed) + 1000); // delay 1 second
			handlerHoles[5].postDelayed(changeStateHole5,
					(long) (Math.random() * speed) + 1000); // delay 1 second
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pauseGame() {
		try {
			handlerTime.removeCallbacks(generalTime);
			handlerHoles[0].removeCallbacks(changeStateHole0);
			handlerHoles[1].removeCallbacks(changeStateHole1);
			handlerHoles[2].removeCallbacks(changeStateHole2);
			handlerHoles[3].removeCallbacks(changeStateHole3);
			handlerHoles[4].removeCallbacks(changeStateHole4);
			handlerHoles[5].removeCallbacks(changeStateHole5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Runnable generalTime = new Runnable() {
		public void run() {
			try {
				System.gc();
				game.setTimeCounter(game.getTimeCounter() - 1);
				if (game.getTimeCounter() >= 0) {
					handlerTime.removeCallbacks(generalTime);
					handlerTime.postDelayed(generalTime, 1000);
				} else {
					handlerTime.removeCallbacks(generalTime);
					handlerHoles[0].removeCallbacks(changeStateHole0);
					handlerHoles[1].removeCallbacks(changeStateHole1);
					handlerHoles[2].removeCallbacks(changeStateHole2);
					handlerHoles[3].removeCallbacks(changeStateHole3);
					handlerHoles[4].removeCallbacks(changeStateHole4);
					handlerHoles[5].removeCallbacks(changeStateHole5);
					txtTest1.setText(getString(R.string.text_game_topbar_time)
							+ ": " + 0);
					showFinalScore();
				}
				txtTest1.setText(getString(R.string.text_game_topbar_time)
						+ ": " + game.getTimeCounter());
			} catch (Exception e) {
				txtTest1.setText(e.getMessage());
			}

		}
	};

	private Runnable changeStateHole0 = new Runnable() {
		public void run() {
			if (game.getHole()[0].isState()) {
				scaleAnim.reset();
				imgHole0.startAnimation(scaleAnim);
				imgHole0.setImageResource(randomBall(0));
				game.getHole()[0].setState(false);
			} else {
				imgHole0.setImageResource(R.drawable.nosrcball);
				game.setNoBall(0);
			}
			handlerHoles[0].removeCallbacks(changeStateHole0);
			handlerHoles[0].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	private Runnable changeStateHole1 = new Runnable() {
		public void run() {
			if (game.getHole()[1].isState()) {
				scaleAnim.reset();
				imgHole1.startAnimation(scaleAnim);
				imgHole1.setImageResource(randomBall(1));
				game.getHole()[1].setState(false);
			} else {
				imgHole1.setImageResource(R.drawable.nosrcball);
				game.setNoBall(1);

			}
			handlerHoles[1].removeCallbacks(changeStateHole1);
			handlerHoles[1].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	private Runnable changeStateHole2 = new Runnable() {
		public void run() {
			if (game.getHole()[2].isState()) {
				scaleAnim.reset();
				imgHole2.startAnimation(scaleAnim);
				imgHole2.setImageResource(randomBall(2));
				game.getHole()[2].setState(false);
			} else {
				imgHole2.setImageResource(R.drawable.nosrcball);
				game.setNoBall(2);

			}
			handlerHoles[2].removeCallbacks(changeStateHole2);
			handlerHoles[2].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	private Runnable changeStateHole3 = new Runnable() {
		public void run() {
			if (game.getHole()[3].isState()) {
				scaleAnim.reset();
				imgHole3.startAnimation(scaleAnim);
				imgHole3.setImageResource(randomBall(3));
				game.getHole()[3].setState(false);
			} else {
				imgHole3.setImageResource(R.drawable.nosrcball);
				game.setNoBall(3);

			}
			handlerHoles[3].removeCallbacks(changeStateHole3);
			handlerHoles[3].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	private Runnable changeStateHole4 = new Runnable() {
		public void run() {
			if (game.getHole()[4].isState()) {
				scaleAnim.reset();
				imgHole4.startAnimation(scaleAnim);
				imgHole4.setImageResource(randomBall(4));
				game.getHole()[4].setState(false);
			} else {
				imgHole4.setImageResource(R.drawable.nosrcball);
				game.setNoBall(4);

			}
			handlerHoles[4].removeCallbacks(changeStateHole4);
			handlerHoles[4].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	private Runnable changeStateHole5 = new Runnable() {
		public void run() {
			if (game.getHole()[5].isState()) {
				scaleAnim.reset();
				imgHole5.startAnimation(scaleAnim);
				imgHole5.setImageResource(randomBall(5));
				game.getHole()[5].setState(false);
			} else {
				imgHole5.setImageResource(R.drawable.nosrcball);
				game.setNoBall(5);

			}
			handlerHoles[5].removeCallbacks(changeStateHole5);
			handlerHoles[5].postDelayed(this,
					(long) (Math.random() * speed) + 1000);
		}
	};

	public void score(View view) {
		mp = MediaPlayer.create(getApplicationContext(), R.raw.hitok);
		// int numHole = 0;
		switch (view.getId()) {
		case R.id.imgHole0:
			if (game.getHole()[0].isState()) {
				setScore(false, game.getHole()[0].isExtra(),
						game.getHole()[0].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[0].isExtra(),
						game.getHole()[0].isMine());
				imgHole0.setImageResource(R.drawable.nosrcball);
				game.getHole()[0].setState(true);
			}
			game.getHole()[0].setExtra(false);
			game.getHole()[0].setMine(false);
			break;
		case R.id.imgHole1:
			if (game.getHole()[1].isState()) {
				setScore(false, game.getHole()[1].isExtra(),
						game.getHole()[1].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[1].isExtra(),
						game.getHole()[1].isMine());
				imgHole1.setImageResource(R.drawable.nosrcball);
				game.getHole()[1].setState(true);
			}
			game.getHole()[1].setExtra(false);
			game.getHole()[1].setMine(false);
			break;
		case R.id.imgHole2:
			if (game.getHole()[2].isState()) {
				setScore(false, game.getHole()[2].isExtra(),
						game.getHole()[2].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[2].isExtra(),
						game.getHole()[2].isMine());
				imgHole2.setImageResource(R.drawable.nosrcball);
				game.getHole()[2].setState(true);
			}
			game.getHole()[2].setExtra(false);
			game.getHole()[2].setMine(false);
			break;
		case R.id.imgHole3:
			if (game.getHole()[3].isState()) {
				setScore(false, game.getHole()[3].isExtra(),
						game.getHole()[3].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[3].isExtra(),
						game.getHole()[3].isMine());
				imgHole3.setImageResource(R.drawable.nosrcball);
				game.getHole()[3].setState(true);
			}
			game.getHole()[3].setExtra(false);
			game.getHole()[3].setMine(false);
			break;
		case R.id.imgHole4:
			if (game.getHole()[4].isState()) {
				setScore(false, game.getHole()[4].isExtra(),
						game.getHole()[4].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[4].isExtra(),
						game.getHole()[4].isMine());
				imgHole4.setImageResource(R.drawable.nosrcball);
				game.getHole()[4].setState(true);
			}
			game.getHole()[4].setExtra(false);
			game.getHole()[4].setMine(false);
			break;
		case R.id.imgHole5:
			if (game.getHole()[5].isState()) {
				setScore(false, game.getHole()[5].isExtra(),
						game.getHole()[5].isMine());
			} else {
				sounAndVibrate();
				setScore(true, game.getHole()[5].isExtra(),
						game.getHole()[5].isMine());
				imgHole5.setImageResource(R.drawable.nosrcball);
				game.getHole()[5].setState(true);
			}
			game.getHole()[5].setExtra(false);
			game.getHole()[5].setMine(false);
			break;
		}
		txtTest2.setText("Puntuación: " + game.getScore());
	}

	private void showFinalScore() {
		Intent intent = new Intent(getApplicationContext(), Score.class);
		intent.putExtra("SCORE", game.getScore());
		intent.putExtra("DIFFICULTY", game.getDifficulty());
		finish();
		startActivity(intent);
		/*
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Información");
		builder.setMessage("Su puntuación fue: " + game.getScore()
				+ " ¿Desea volver a jugar?");
		builder.setPositiveButton("Sí", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				restartValues();
				startGame();
				dialog.cancel();
			}
		});
		builder.setNegativeButton("No", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
		builder.show();
		if (game.isSound()) {
			mp.create(getApplicationContext(), R.raw.finalscore).start();
			mp.release();
		}*/

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game, menu);
		return true;
	}

	// Load configuration at the start of application
	@Override
	protected void onStart() {
		super.onStart();
		loadConfiguration();
		if (!game.isSound())
			deleteSoundEffectsButtons();
	}

	public void loadConfiguration() {
		SharedPreferences prefs = getSharedPreferences("HitBall",
				Context.MODE_PRIVATE);
		game.setSound(prefs.getBoolean("SOUND", true));
		game.setVibrate(prefs.getBoolean("VIBRATE", true));
		game.setDifficulty(prefs.getString("DIFFICULTY",
				getString(R.string.textDifficultyEasy)));
	}

	public void deleteSoundEffectsButtons() {
		imgHole0.setSoundEffectsEnabled(false);
		imgHole1.setSoundEffectsEnabled(false);
		imgHole2.setSoundEffectsEnabled(false);
		imgHole3.setSoundEffectsEnabled(false);
		imgHole4.setSoundEffectsEnabled(false);
		imgHole5.setSoundEffectsEnabled(false);
	}

	public int randomBall(int h) {
		int ans = 0;
		if ((Math.random() * 10) > 2) {
			int n = (int) ((Math.random() * 5) + 1);
			switch (n) {
			case 1:
				ans = R.drawable.srcball1;
				break;
			case 2:
				ans = R.drawable.srcball2;
				break;
			case 3:
				ans = R.drawable.srcball3;
				break;
			case 4:
				ans = R.drawable.srcball4;
				break;
			case 5:
				ans = R.drawable.srcball5;
				break;
			}
		} else {
			if ((Math.random() * 10) > 2) {
				ans = R.drawable.balldestroy;
				game.getHole()[h].setMine(true);
			} else {
				ans = R.drawable.ballextra;
				game.getHole()[h].setExtra(true);
			}
		}
		return ans;
	}

	public void sounAndVibrate() {
		if (game.isVibrate())
			v.vibrate(20);
	}

	public void setScore(boolean correct, boolean extra, boolean mine) {
		if (!extra && !mine)
			game.setScore((correct) ? 10 + game.getScore() : -5
					+ game.getScore());
		else if (extra) {
			Toast.makeText(getApplicationContext(), "+50", Toast.LENGTH_SHORT)
					.show();
			game.setScore(50 + game.getScore());
		} else if (mine) {
			Toast.makeText(getApplicationContext(), "-50", Toast.LENGTH_SHORT)
					.show();
			game.setScore(-50 + game.getScore());
		}
	}
}
