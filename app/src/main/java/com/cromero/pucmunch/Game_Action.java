package com.cromero.pucmunch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class Game_Action extends AppCompatActivity implements SensorEventListener {

    //segundos iniciales en el cronometro
    private static final int INITIAL_TIME = 30;

    private int BACKGROUND_COLOR = Color.BLACK;

    private int POINT_COUNTER_COLOR = Color.WHITE;
    private int POINTX = 50;
    private int POINTY = 150;

    private int BALL_COLOR = Color.YELLOW;
    private int BALL_RADIUS = 50;
    private int BALL_SPEED = 5;

    private int BORDER_COLOR = Color.parseColor("#4B0082");
    private int BORDER_WIDTH = 50;
    private int BORDER_RESISTANCE = 2;

    private int OBJECTIVE_RADIUS = 10;
    private int OBJECTIVE_COLOR = Color.WHITE;
    private int OBJECTIVE_BORDER_WITDH = 20;
    private int OBJECTIVE_MAGNET = 10;
    private int OBJECTIVE_MARGIN = 30;

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
        } else {
            muestraError();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mueveBola(sensorEvent);
            if(compruebaObjetivo()){
                if(points == 0){
                    //TODO iniciaCronometro(INITIAL_TIME);
                    points++;
                }
            };
        }
        mainCanvas.invalidate();
    }

    private boolean compruebaObjetivo() {
        if((ballY+BALL_RADIUS >= objectiveY-OBJECTIVE_MAGNET && ballY-BALL_RADIUS < objectiveY+OBJECTIVE_MAGNET) &&(ballX+BALL_RADIUS >= objectiveX-OBJECTIVE_MAGNET && ballX-BALL_RADIUS < objectiveX+OBJECTIVE_MAGNET)){
            //points++;
            //Toast.makeText(this,Integer.toString(points),Toast.LENGTH_SHORT).show();
            return true;
        } else {return false;}
    }

    private void victoria() {
        points++;

        objectiveX = (new Random().nextFloat()*(width-OBJECTIVE_RADIUS-(OBJECTIVE_MARGIN*2)))+OBJECTIVE_MARGIN+OBJECTIVE_RADIUS;
        objectiveY = (new Random().nextFloat()*(height-OBJECTIVE_RADIUS-(OBJECTIVE_MARGIN*2)))+OBJECTIVE_MARGIN+OBJECTIVE_RADIUS;
    }

    private void mueveBola(SensorEvent sensorEvent) {

        ballX = ballX - sensorEvent.values[0]*BALL_SPEED;
        ballY = ballY + sensorEvent.values[1]*BALL_SPEED;

        if(ballX <= BALL_RADIUS || ballX >= width-BALL_RADIUS){
            ballX += sensorEvent.values[0]*(BALL_SPEED+BORDER_RESISTANCE);
        }
        if(ballY <=BALL_RADIUS || ballY >= height-BALL_RADIUS){
            ballY -= sensorEvent.values[1]*(BALL_SPEED+BORDER_RESISTANCE);
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

            //Primero dibujo el fondo
            pincel.setColor(BACKGROUND_COLOR);
            pincel.setStrokeWidth(1);

            canvas.drawRect(0,0,width,height,pincel);

            //Luego dibuja la bola
            pincel.setColor(BALL_COLOR);
            canvas.drawCircle(ballX, ballY, BALL_RADIUS, pincel);

            pincel.setStyle(Paint.Style.STROKE);
            //Luego dibujo el objetivo
            pincel.setColor(OBJECTIVE_COLOR);
            pincel.setStrokeWidth(OBJECTIVE_BORDER_WITDH);
            canvas.drawCircle(objectiveX,objectiveY,OBJECTIVE_RADIUS,pincel);

            //Y por ultimo el borde para que este por encima de lo demas
            pincel.setColor(BORDER_COLOR);
            pincel.setStrokeWidth(BORDER_WIDTH);

            canvas.drawRect(0,0,width,height,pincel);

            pincel.setColor(POINT_COUNTER_COLOR);
            pincel.setStyle(Paint.Style.FILL);
            pincel.setTextSize(100);
            canvas.drawText(getString(R.string.pointCounterText) + Integer.toString(points),POINTX,POINTY,pincel);
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