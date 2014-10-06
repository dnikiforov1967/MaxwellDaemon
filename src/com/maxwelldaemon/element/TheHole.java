/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maxwelldaemon.element;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 *
 * @author Dmnikiforov
 */
public class TheHole extends VerticalWall {
	
	private int strikeCounter;
	
	public void incrementStrike() {
		strikeCounter++;
	}
	
	public int getStrikeCounter() {
		return strikeCounter;
	}

    public TheHole(int x, int startY, int endY) {
        super(x, startY, endY, Color.BLACK);
    }
    
    public void moveY(int deltaY, int minY, int maxY) {
    	if ((stopY + deltaY > maxY) || (startY + deltaY < minY))
    		return;

    	startY += deltaY;
    	stopY  += deltaY;
    }
    
    public void draw(Canvas cvs) {
    	cvs.drawLine(startX, startY, stopX, stopY, p);
    }
}
