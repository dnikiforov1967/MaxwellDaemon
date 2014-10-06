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
public class MolecularBox {
    
    Wall top, bottom, left, right;
    private int oxigenCount;
    private int nitrogenCount;
    
    public void increaseOxigenCount() {
    	oxigenCount++;
    }
    
    public void decreaseOxigenCount() {
    	oxigenCount--;
    }
    
    public int getOxigenCount() {
    	return oxigenCount;
    }

    public void increaseNitrogenCount() {
    	nitrogenCount++;
    }
    
    public void decreaseNitrogenCount() {
    	nitrogenCount--;
    }
    
    public int getNitrogenCount() {
    	return nitrogenCount;
    }
    
    public MolecularBox(int topP, int leftP, int bottomP, int rightP,
    		int clrl, int clrr, int clrt, int clrb) {
        this.top    = new HorizontalWall(topP,leftP, rightP, clrt);
        this.bottom = new HorizontalWall(bottomP,leftP, rightP, clrb);
        this.left   = new VerticalWall(leftP,topP, bottomP, clrl);
        this.right  = new VerticalWall(rightP,topP, bottomP, clrr);
        
    }
    
    public void draw(Canvas cvs) {
    	top.draw(cvs);
    	bottom.draw(cvs);
    	left.draw(cvs);
    	right.draw(cvs);
    }
}
