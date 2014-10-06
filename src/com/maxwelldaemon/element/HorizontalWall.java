/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.maxwelldaemon.element;

import android.graphics.Canvas;

/**
 *
 * @author Dmnikiforov
 */
public class HorizontalWall extends Wall {
    
    public HorizontalWall(int y, int startX, int endX, int clr) {
        super.startY = y;
        super.stopY   = y;
        super.startX = startX;
        super.stopX   = endX;
        p.setColor(clr);
    }

    public void draw(Canvas cvs) {
    	cvs.drawLine(startX, startY, stopX, stopY, p);
    }
        
    
}
