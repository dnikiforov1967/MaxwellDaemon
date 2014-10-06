package com.maxwelldaemon.element;

import android.graphics.Canvas;
import android.graphics.Color;

public class Nitrogen extends Molecule {

	public Nitrogen(int x, int y, int velocityX, int velocityY, MolecularBox mb, TheHole thehole, MolecularBox nextDoorBox) {
		super(x, y, velocityX, velocityY, mb, thehole, nextDoorBox);
		// TODO Auto-generated constructor stubFD3F49
		paint.setColor(Color.argb(0xff, 0xfd, 0x3f, 0x49));
	}

	@Override
	public void draw(Canvas cvs) {
		// TODO Auto-generated method stub
		cvs.drawCircle(x, y, 4, paint);	
	}

}

