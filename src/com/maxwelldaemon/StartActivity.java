package com.maxwelldaemon;

import com.maxwelldaemon.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class StartActivity extends Activity {
	
	private Button btStartGame, btList;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
			    WindowManager.LayoutParams.FLAG_FULLSCREEN,
			    WindowManager.LayoutParams.FLAG_FULLSCREEN);		
		
		setContentView(R.layout.activity_start);

		//final View contentView = findViewById(R.id.startrootlayout);
		
		setButtons();

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daemon, menu);
		return true;
	}	
	
	
	private class XOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = null;
			
			if (v == btStartGame) {
				i = new Intent(StartActivity.this, DaemonActivity.class);
				startActivity(i);
			}
			
			if (v == btList) {
				FragmentManager fm = StartActivity.this.getFragmentManager();
		   		ResultFragment rf = new ResultFragment();
	    		rf.show(fm, "hint_dialg");					
			}		
			
		}
		
	}

	
	private void setButtons() {

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		DisplayMetrics outMetrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);			
		
		btStartGame = (Button)findViewById(R.id.btStartGame);
		btList  = (Button)findViewById(R.id.btList);
		
		btStartGame.setTextColor(Color.WHITE);
		btList.setTextColor(Color.WHITE);
		btStartGame.setOnClickListener(new XOnClickListener());
		btList.setOnClickListener(new XOnClickListener());
		
	}
	
	
}
