package com.maxwelldaemon.element;

import android.graphics.Canvas;
import android.graphics.Color;

public class Oxigen extends Molecule {

	public Oxigen(int x, int y, int velocityX, int velocityY, MolecularBox mb, TheHole thehole, MolecularBox nextDoorBox) {
		super(x, y, velocityX, velocityY, mb, thehole, nextDoorBox);
		// TODO Auto-generated constructor stub
		paint.setColor(Color.argb(0xff, 0x5e, 0xd0, 0xbd));
	}

	@Override
	public void draw(Canvas cvs) {
		// TODO Auto-generated method stub
		cvs.drawCircle(x, y, 4, paint);	
	}

}
