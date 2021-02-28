package com.cromero.pucmunch.objects;

import android.graphics.Color;

public class Enemy {
    public float coordX, coordY;
    public int color;
    private int type;


    //Se introduce un numero del 1 al 4, dependiendo del numero el fantasma tendra un color y un comportamiento
    public Enemy(int type) {
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

    }
}
