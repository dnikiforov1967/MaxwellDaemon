package com.maxwelldaemon;

import java.text.NumberFormat;
import java.util.ArrayList;

import com.maxwelldaemon.element.MolecularBox;
import com.maxwelldaemon.element.Nitrogen;
import com.maxwelldaemon.element.Oxigen;
import com.maxwelldaemon.element.TheHole;

import android.app.FragmentManager;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class SimpleWaiter extends Thread {
	public void run() {
		SemaphoreItem.setTrue();
		while(!isInterrupted()) {
			
		}
		SemaphoreItem.setFalse();
	}
}

class TimeCounter {
	Paint p = new Paint();
	private AtomicInteger ai = new AtomicInteger();
	
	TimeCounter() {
		p.setTextSize(40);
		p.setColor(Color.RED);
		p.setStrokeWidth(4);
	}
	
	public void reset() {
		ai.set(0);
	}
	
	public void increment() {
		ai.incrementAndGet();
	}
	
	private int get() {
		return ai.get();
	}
	
	public void draw(Canvas cvs) {
		SemaphoreItem.time = this.get(); 
		SemaphoreItem.timerValue = String.format(Locale.US, "%4.1f",((double)SemaphoreItem.time)/10);
		cvs.drawText(SemaphoreItem.timerValue, 3, 45, p);
	}
}


class CounterThread extends Thread {
	private TimeCounter tc;
	
	CounterThread(TimeCounter tc) {
		this.tc = tc;
	}
	
	public void run() {
		while(!isInterrupted()) {
			try {
				TimeUnit.MILLISECONDS.sleep(100);
				tc.increment();
			}
			catch(InterruptedException ie) {
				this.interrupt();
			}
		}
	}
	
}


public class BoxView extends SurfaceView implements SurfaceHolder.Callback {
	
    public class ShowMe implements Runnable {
    	
    	public ShowMe() {
    	}
 
    	public void run() {
    		HintDialog hd = new HintDialog();
    		FragmentManager fm = ((Activity)BoxView.this.getContext()).getFragmentManager();
    		hd.show(fm, "hint_dialg");	    		
    	}
    }	

	BoxRunnable br;
	int touchedY = 0, currentY = 0;
	volatile boolean timerRun = false;
	
	public BoxView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		getHolder().addCallback(this);		
	}
	
	public void setTouchedY(int touchedY) {
		this.touchedY = touchedY;
		br.setDeltaY(0);
	}
	
	public void setCurrentY(int currentY) {
		this.currentY = currentY;
		br.setDeltaY(currentY - touchedY);
		touchedY = currentY;
	}

	public BoxView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		
	}

	public BoxView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					((BoxView)v).setTouchedY((int)event.getY());
				}	
				if (event.getAction() == MotionEvent.ACTION_MOVE)	
					((BoxView)v).setCurrentY((int)event.getY());
					
					return true;
			}
			
		});
		
		SimpleWaiter sw = new SimpleWaiter();
		SemaphoreItem.sharedThread = sw;
		sw.start();
		
		new ShowMe().run();
		
		br = new BoxRunnable(holder, this.getWidth(), this.getHeight());
		br.setBoxView(this);
		br.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;

		if (SemaphoreItem.sharedThread != null) {
			try {
				SemaphoreItem.sharedThread.interrupt();
				SemaphoreItem.sharedThread.join();
			}
			catch(InterruptedException ie) {
				
			}
			finally {
				SemaphoreItem.sharedThread = null;
			}
		}	
		
		Thread t = br;
		br.stopRun();		
		br = null;
		
		while(retry) {
			try {
				t.join();
				retry = false;
			}
			catch(InterruptedException ie) {
				
			}
		}
		
		
	}
}

class BoxRunnable extends Thread {

	SurfaceHolder sh;
	int width, height;
	CounterThread ct;
	TimeCounter tc = new TimeCounter();
	GameStep gameStep = null;
	
	volatile boolean runFlag = true;
	MolecularBox leftBox, rightBox;
	TheHole hole;
	List<Oxigen> lox = new ArrayList<Oxigen>();
	List<Nitrogen> lni = new ArrayList<Nitrogen>();
	
	BoxView bv;
	
	int deltaY = 0;
	
	public void setBoxView(BoxView bv) {
		this.bv = bv;
	}
	
	
	public void stopRun() {
		runFlag = false;
	}
	
	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}
	
	
	private void initMolecules(int countO, int countN ) {
		
		//initial point

		lox.clear();
		for(int i = 1; i<=countO; i++) {
			int xInit = (int)(width*Math.random()/4);
			int yInit  = (int)(height*Math.random()/2);	
			
			int vY = (int)(5*Math.sin(Math.PI*i/4/countO));
			int vX = (int)(5*Math.cos(Math.PI*i/4/countO)); 
			
			lox.add(new Oxigen(xInit, yInit, 
					vX, 
					vY, 
					leftBox, hole, rightBox));
			leftBox.increaseOxigenCount();
		}			
		

		lni.clear();
		for(int i = 1; i<=countN; i++) {
			int xInit = (int)(width/2 + width*Math.random()/4);
			int yInit  = (int)(height*Math.random()/2);	
			
			int vY = (int)(-5*Math.sin(Math.PI*i/4/countN));
			int vX = (int)(-5*Math.cos(Math.PI*i/4/countN)); 
			
			lni.add(new Nitrogen(xInit, yInit, 
					vX, 
					vY, 
					rightBox, hole, leftBox));
			rightBox.increaseNitrogenCount();
		}				
	}
	
	private void init() {
		
		leftBox = new MolecularBox(0,0,height,width/2,
				Color.WHITE,Color.GREEN,Color.WHITE,Color.WHITE);
		rightBox = new MolecularBox(0,width/2,height,width,
				Color.GREEN,Color.WHITE,Color.WHITE,Color.WHITE);
		hole     = new TheHole(width/2, 0, height/7);

	}
	
	BoxRunnable(SurfaceHolder sh, int width, int height) {
		this.sh = sh;
		this.width = width;
		this.height = height;
		
		init();
	}
	
	private void clear() {
		leftBox = null;
		rightBox = null;
		sh = null;	
	    bv = null;
	}
	
	private void processMolecules(Canvas cvs) {
			if (ct == null) {
				tc.reset();
				ct = new CounterThread(tc);
				ct.start();
			}
			for(Oxigen ox: lox) {
				ox.calcNext();				
				ox.draw(cvs);
			}
			for(Nitrogen ni: lni) {
				ni.calcNext();				
				ni.draw(cvs);
			}

			tc.draw(cvs);
			if (leftBox.getOxigenCount() == 0 && rightBox.getNitrogenCount()==0) {
				ct.interrupt();
				try {
					ct.join();
					ct = null;
					SemaphoreItem.sharedThread = new SimpleWaiter();
					SemaphoreItem.sharedThread.start();
					bv.getHandler().post(bv.new ShowMe()); 
				}
				catch(InterruptedException e) {}
			}			
	}
	
	private void allocateMolecules() {
		if (leftBox.getOxigenCount() == 0 && rightBox.getNitrogenCount()==0) {
			if (SemaphoreItem.gameStep == null) 
				SemaphoreItem.gameStep = GameStep.SIMPLE_FIRST;
			else 
				SemaphoreItem.gameStep = GameStep.values()[SemaphoreItem.gameStep.ordinal()+1];
			this.initMolecules(SemaphoreItem.gameStep.getOxigen(), SemaphoreItem.gameStep.getNitrogen());
		}		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Canvas cvs = null;
		while(runFlag) {
			
			if (SemaphoreItem.sharedThread != null) {
				try {
					SemaphoreItem.sharedThread.join();
				}
				catch(InterruptedException ie) {
					
				}
				finally {
					SemaphoreItem.sharedThread = null;
				}
			}
			
			if (SemaphoreItem.gameStep == GameStep.STOP) {
				((Activity)this.bv.getContext()).finish();
				break;
			}
			
				try {
					cvs = sh.lockCanvas();
					synchronized(sh) {
						if (cvs != null) {
							allocateMolecules();							
							cvs.drawColor(Color.BLACK);
							leftBox.draw(cvs);
							rightBox.draw(cvs);
							hole.moveY(deltaY, 0, height);
							hole.draw(cvs);
							processMolecules(cvs);
						}	
					}
				}
				finally {
					if (cvs != null)
						sh.unlockCanvasAndPost(cvs);
				}
				
		}
		clear();
	}
	
}
