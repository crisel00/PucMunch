package com.cromero.pucmunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bt_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_play = findViewById(R.id.bt_play);

        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), Game_Action.class);
                startActivity(in);
            }
        });
    }
}

/*
 * TODO Fases del proyecto
 *      (Un pacman se mueve por la pantalla usando el acelerometro, tu mision
 *      es conseguir comer la mayor cantidad de puntos antes de que se agote
 *      el tiempo. Al recoger cierta cantidad de puntos, un fastasma aparecera
 *      e intentara atacar al jugador)
 *
 *
 *      0.Configuracion
 *
 *      1.Menu de Inicia
 *          (Fondo negro, boton azul con letras blancas "Play")
 *          Titulo del juego en grande
 *
 *      2.Juego
 *          1.- Dibujar objetos --Hecho (todo añadir imagenes)
 *          2.- Movimiento Jugador --Hecho
 *          3.- Puntuacion y objetivo --Hecho
 *          4.- Temporizador
 *          5.- Enemigo
 *
  */