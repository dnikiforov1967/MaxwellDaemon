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
 * @author DNikiforov
 */
public abstract class Wall {
	
	static final int lineWidth = 3;
	Paint p = new Paint();
	{
		p.setStrokeWidth(lineWidth);
	}
	
    int startX, startY, stopX, stopY;
    
    public abstract void draw(Canvas cvs);
    
}
