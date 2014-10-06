package com.maxwelldaemon;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class DaemonActivity extends Activity {

	BoxView bv;
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daemon);

		// TODO Auto-generated constructor stub
		SemaphoreItem.sharedThread = null;
		SemaphoreItem.gameStep = null;
		SemaphoreItem.timerValue = null;

		bv = (BoxView)this.findViewById(R.id.boxsurface);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.daemon, menu);
		return true;
	}

}

