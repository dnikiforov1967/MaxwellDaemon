/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maxwelldaemon.element;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 *
 * @author Dmnikiforov
 */
public abstract class Molecule {
    
    int velocityX, velocityY;
    int x, y, futureX, futureY;
    TheHole thehole;
    MolecularBox mb, nextDoorBox;
    Paint paint;
    
    public Molecule(int x, int y, int velocityX, int velocityY, MolecularBox mb, TheHole thehole, MolecularBox nextDoorBox) {
        this.x         = x;
        this.y         = y;
        this.futureX   = x;
        this.futureY   = y;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.mb        = mb;
        paint = new Paint();
        this.thehole = thehole;
        this.nextDoorBox = nextDoorBox;
    }
    
    public abstract void draw(Canvas cvs);
    
    public void calcNext() {
    	boolean strike = false;
        
        futureX = x + velocityX;
        futureY = y + velocityY;
        
        //Do we reach the hole ?
        if (x <= thehole.startX && futureX > thehole.startX) { //Trasspass the hole
       		int yC = y + (thehole.startX - x)*velocityY/velocityX;
       		if (thehole.startY <= yC && thehole.stopY >= yC) { //No reflection
       			MolecularBox t = mb;
       			if (this instanceof Oxigen)
       				mb.decreaseOxigenCount();
       			else if (this instanceof Nitrogen)
       				mb.decreaseNitrogenCount();
       			mb = nextDoorBox;
       			if (this instanceof Oxigen)
       				mb.increaseOxigenCount();
       			else if (this instanceof Nitrogen)
       				mb.increaseNitrogenCount();
       			nextDoorBox = t;
       	        //Pass through counter
       	        thehole.incrementStrike(); 
       	        x = futureX;
       	        y = futureY;          	        
       			return;
      		}
        }

        if (x >= thehole.startX && futureX < thehole.startX) { //Trasspass the hole
       		int yC = y + (thehole.startX - x)*velocityY/velocityX;
       		if (thehole.startY <= yC && thehole.stopY >= yC) { //No reflection
       			MolecularBox t = mb;
       			if (this instanceof Oxigen)
       			    mb.decreaseOxigenCount();
       			else if (this instanceof Nitrogen)
       				mb.decreaseNitrogenCount();
       			mb = nextDoorBox;
       			if (this instanceof Oxigen)
       				mb.increaseOxigenCount();
       			else if (this instanceof Nitrogen)
       				mb.increaseNitrogenCount();
       			nextDoorBox = t;    
       		    //Pass through counter
       			thehole.incrementStrike();
       	        x = futureX;
       	        y = futureY;       			
      			return;
       		}
        }
        
        
        //Do we reach wall ?
        if (futureX >= mb.right.startX) { //Trasspass right
        	futureX = 2*mb.right.startX - futureX; //Reflection
        	velocityX = -velocityX;          //Reflection 
        }
        if (futureX <= mb.left.startX) { //Trasspass right
        	futureX = 2*mb.left.startX - futureX; //Reflection
        	velocityX = -velocityX;          //Reflection 
        } 
        if (futureY >= mb.bottom.startY) { //Trasspass bottom
        	futureY = 2*mb.bottom.startY - futureY; //Reflection
        	velocityY = -velocityY;          //Reflection 
        }
        if (futureY <= mb.top.startY) { //Trasspass top
        	futureY = 2*mb.top.startY - futureY; //Reflection
        	velocityY = -velocityY;          //Reflection 
        }        
        x = futureX;
        y = futureY;
        
    }
    
}
