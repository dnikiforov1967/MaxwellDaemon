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
public class VerticalWall extends Wall {
    
    public VerticalWall(int x, int startY, int endY, int clr) {
        super.startX = x;
        super.stopX   = x;
        super.startY = startY;
        super.stopY   = endY;
    	p.setColor(clr);        
    }    
    
    public void draw(Canvas cvs) {
    	cvs.drawLine(startX, startY, stopX, stopY, p);
    }
    
}
