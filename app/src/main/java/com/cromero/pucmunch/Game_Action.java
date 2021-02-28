package com.cromero.pucmunch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.cromero.pucmunch.control.GenVars;
import com.cromero.pucmunch.control.TimeCountDown;

import java.sql.Time;
import java.util.Random;

public class Game_Action extends AppCompatActivity implements SensorEventListener {

    protected MainCanvas mainCanvas;

    private SensorManager sensores;
    private Sensor acelerometro;

    public int width;
    public int height;

    float ballX;
    float ballY;

    float objectiveX;
    float objectiveY;

    public int points = 0;

    public TimeCountDown count = new TimeCountDown(GenVars.INITIAL_TIME);
    Thread countTr = new Thread(count);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        mainCanvas = new MainCanvas(this);
        setContentView(mainCanvas);

        sensores = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        initTamanoPantalla();

        ballX = width/2;
        ballY = height - height/3;
        objectiveX = width/2;
        objectiveY = height/3;

        if(sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            Toast.makeText(this,"Acelerometro encontrado",Toast.LENGTH_SHORT).show();
            acelerometro = sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensores.registerListener(this,acelerometro,SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
        } else {
            muestraError();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(count.seconds < 11){
            if(count.seconds%2 == 0){
                GenVars.TIMER_COUNTER_COLOR = Color.WHITE;
            } else{
                GenVars.TIMER_COUNTER_COLOR = Color.RED;
            }
        }
        if(GenVars.gameRunning == true && GenVars.gamePaused == false){
            if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mueveBola(sensorEvent);
                if(compruebaObjetivo()){
                    if(points == 0){
                        countTr.start();
                        victoria();
                    } else {
                        victoria();
                    }
                }
            }

            if(count.seconds<1){
                gameOver();
            }
        }
        mainCanvas.invalidate();
    }

    private void gameOver() {

        GenVars.gameRunning = false;
    }

    private boolean compruebaObjetivo() {
        return (ballY + GenVars.BALL_RADIUS >= objectiveY - GenVars.OBJECTIVE_MAGNET && ballY - GenVars.BALL_RADIUS < objectiveY + GenVars.OBJECTIVE_MAGNET) && (ballX + GenVars.BALL_RADIUS >= objectiveX - GenVars.OBJECTIVE_MAGNET && ballX - GenVars.BALL_RADIUS < objectiveX + GenVars.OBJECTIVE_MAGNET);
    }

    private void victoria() {
        points++;

        objectiveX = (new Random().nextFloat()*(width-GenVars.OBJECTIVE_RADIUS-(GenVars.OBJECTIVE_MARGIN*2)))+GenVars.OBJECTIVE_MARGIN+GenVars.OBJECTIVE_RADIUS;
        objectiveY = (new Random().nextFloat()*(height-GenVars.OBJECTIVE_RADIUS-(GenVars.OBJECTIVE_MARGIN*2)))+GenVars.OBJECTIVE_MARGIN+GenVars.OBJECTIVE_RADIUS;
    }

    private void mueveBola(SensorEvent sensorEvent) {

        ballX = ballX - sensorEvent.values[0]*GenVars.BALL_SPEED;
        ballY = ballY + sensorEvent.values[1]*GenVars.BALL_SPEED;

        if(ballX <= GenVars.BALL_RADIUS || ballX >= width-GenVars.BALL_RADIUS){
            ballX += sensorEvent.values[0]*(GenVars.BALL_SPEED+GenVars.BORDER_RESISTANCE);
        }
        if(ballY <=GenVars.BALL_RADIUS || ballY >= height-GenVars.BALL_RADIUS){
            ballY -= sensorEvent.values[1]*(GenVars.BALL_SPEED+GenVars.BORDER_RESISTANCE);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    class MainCanvas extends View {

        public MainCanvas(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint pincel = new Paint();
            if(GenVars.gameRunning){
                //Primero dibujo el fondo
                pincel.setColor(GenVars.BACKGROUND_COLOR);
                pincel.setStrokeWidth(1);

                canvas.drawRect(0,0,width,height,pincel);

                //Luego dibuja la bola
                pincel.setColor(GenVars.BALL_COLOR);
                canvas.drawCircle(ballX, ballY, GenVars.BALL_RADIUS, pincel);

                pincel.setStyle(Paint.Style.STROKE);
                //Luego dibujo el objetivo
                pincel.setColor(GenVars.OBJECTIVE_COLOR);
                pincel.setStrokeWidth(GenVars.OBJECTIVE_BORDER_WITDH);
                canvas.drawCircle(objectiveX,objectiveY,GenVars.OBJECTIVE_RADIUS,pincel);

                //Y por ultimo el borde para que este por encima de lo demas
                pincel.setColor(GenVars.BORDER_COLOR);
                pincel.setStrokeWidth(GenVars.BORDER_WIDTH);

                canvas.drawRect(0,0,width,height,pincel);

                pincel.setColor(GenVars.POINT_COUNTER_COLOR);
                pincel.setStyle(Paint.Style.FILL);
                pincel.setTextSize(100);
                canvas.drawText(getString(R.string.pointCounterText) + Integer.toString(points),GenVars.POINTX,GenVars.POINTY,pincel);

                pincel.setColor(GenVars.TIMER_COUNTER_COLOR);
                canvas.drawText(Integer.toString(count.seconds),width - GenVars.TIMERX,GenVars.TIMERY,pincel);
            } else{
                pincel.setColor(GenVars.GAME_OVER_BUTTON_COLOR);
                pincel.setStyle(Paint.Style.FILL);

                canvas.drawRect(width/2-130,height/2+200,width/2+170, height/2+330,pincel);

                pincel.setColor(GenVars.POINT_COUNTER_COLOR);
                pincel.setStyle(Paint.Style.FILL);
                pincel.setTextSize(100);

                canvas.drawText(getString(R.string.pointCounterText) + Integer.toString(points),width/2-200,height/2+100,pincel);

                canvas.drawText("Menu",width/2-100,height/2+300,pincel);


            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensores.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensores.registerListener(this,acelerometro,SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM);
    }

    private void initTamanoPantalla(){
        Display display = getWindowManager().getDefaultDisplay();

        width = display.getWidth();
        height = display.getHeight();
    }

    private void muestraError(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.noAcelerometer)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                });
        AlertDialog dial = builder.create();
        dial.show();
    }
}