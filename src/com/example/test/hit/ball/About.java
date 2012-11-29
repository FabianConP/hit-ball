package com.example.test.hit.ball;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends Activity{

	private TextView txtAbout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		txtAbout = (TextView) findViewById(R.id.txtAbout);
		String text = "~ HitBall ~ \n\n" +
				"Integrantes\n" +
				"- Fabian Conejo.\n- Jhonatan Guzmán.\n- Brayan Clavijo.\n\n" +
				"1. Introducción.\n" +
				"Este es un juego que prueba que tan rapido eres, y el nivel de concentracion que tienes.\n\n" +
				"2. Información.\n" +
				"- El juego esta disponible para dispositivos Android.\n- Fue desarrollado en Eclipse, y programado en el lenguaje Java.\n\n" +
				"3. Funcionamiento.\n" +
				"Al inicio del juego, aparecen tres opciones, la primera es la opcion de Jugar en la cual se inicia el juego que consta en hacer contacto en la pantalla, cada vez que salgan los diferentes tipos de balones deportivos y de esta manera ir sumando puntos; las minas restan una gran cantidad de puntos y los trofeos suman una gran cantidad. La segunda opcion es la de las Configuraciones, donde se puede modificar la dificultad del juego y una tercera opción para conocer un poco más del juego.\n\n" +
				"4. Agradecimientos.\n" +
				"- Germán Hernandez.\n- John Parra.\n- Nhora ofelia Acuña Prieto.(Comfie)\n\n\n\n\n";
		txtAbout.setText(text);
		}
	public void goHome(View view){
		finish();
	}
}
