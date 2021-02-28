package com.cromero.pucmunch.control;

import android.graphics.Color;

//Variables generales de juego
public class GenVars {
    public static int INITIAL_TIME = 30;
    public static int TIME_ADD = 2;

    public static int BACKGROUND_COLOR = Color.BLACK;

    public static int POINT_COUNTER_COLOR = Color.WHITE;
    public static int POINTX = 50;
    public static int POINTY = 150;

    public static int TIMER_COUNTER_COLOR = Color.WHITE;
    public static int TIMERX = 150;
    public static int TIMERY = 150;

    public static int BALL_COLOR = Color.YELLOW;
    public static int BALL_RADIUS = 50;
    public static int BALL_SPEED = 5;

    public static int BORDER_COLOR = Color.BLUE;
    public static int BORDER_WIDTH = 50;
    public static int BORDER_RESISTANCE = 2;

    public static int OBJECTIVE_RADIUS = 10;
    public static int OBJECTIVE_COLOR = Color.WHITE;
    public static int OBJECTIVE_BORDER_WITDH = 20;
    public static int OBJECTIVE_MAGNET = 10;
    public static int OBJECTIVE_MARGIN = 30;

    public static int width;
    public static int height;

    public static int GAME_OVER_BUTTON_COLOR = Color.BLUE;

    public static float ballX;
    public static float ballY;

    public static boolean gameRunning = true;
    public static boolean gamePaused;
}
