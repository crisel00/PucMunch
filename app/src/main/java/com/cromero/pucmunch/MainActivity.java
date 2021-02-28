package com.cromero.pucmunch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cromero.pucmunch.control.GenVars;
import com.cromero.pucmunch.control.RecordData;

public class MainActivity extends AppCompatActivity {

    Button bt_play;
    TextView tv_record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(RecordData.loadData(this).length() < 1){
            RecordData.saveData(this, 0);
        }

        bt_play = findViewById(R.id.bt_play);
        tv_record = findViewById(R.id.tv_record);

        tv_record.setText("Record: " + RecordData.loadData(this));

        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getBaseContext(), Game_Action.class);
                GenVars.gameRunning = true;

                startActivity(in);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tv_record.setText("Record: " + RecordData.loadData(this));
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