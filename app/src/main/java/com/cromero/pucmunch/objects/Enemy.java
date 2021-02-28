package com.cromero.pucmunch.objects;

import android.graphics.Color;

import com.cromero.pucmunch.Game_Action;
import com.cromero.pucmunch.control.GenVars;

public class Enemy {
    public float coordX, coordY;
    public int color;
    private int type;
    public int SPEED = 10;
    int leftRight = 'R';


    //Se introduce un numero del 1 al 4, dependiendo del numero el fantasma tendra un color y un comportamiento
    public Enemy(int type) {
        coordX = 20;
        coordY = 20;
        this.type = type;
        setColor(type);
        
    }

    private void setColor(int type) {
        switch(type){
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.parseColor("#ff5935");
                break;
            case 3:
                color = Color.CYAN;
                break;
            case 4:
                color = Color.parseColor("f800ff");
                break;
        }
    }

    public void move(){
        switch(type){
            case 1:
                if(coordX < GenVars.ballX){
                    coordX += SPEED;
                } else {
                    coordX -= SPEED;
                }
                if (coordY < GenVars.ballY){
                    coordY += SPEED;
                } else {
                    coordY -= SPEED;
                }
                break;
            case 2:

                if(coordY < Game_Action.height/2){
                    coordY += SPEED;
                } else{
                    coordY -= SPEED;
                }

                if(coordX > Game_Action.width - 50){
                    leftRight = 'L';
                }
                if(coordX < 32){
                    leftRight = 'R';
                }

                switch (leftRight){
                    case 'L':
                        coordX -= SPEED;
                        break;
                    case 'R':
                        coordX += SPEED;
                        break;
                }
                break;
            case 3:

                break;
            case 4:

                break;
        }
    }
}
