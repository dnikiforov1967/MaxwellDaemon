package com.maxwelldaemon;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HintDialog extends DialogFragment {
	
	final static String SETTING_FILE = "myResults";
	private boolean bingo = false;
	
	public HintDialog() {
		while(!SemaphoreItem.get()) {}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		this.setShowsDialog(true);
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub	
		super.onDestroyView();
		if (SemaphoreItem.sharedThread != null) {
			SemaphoreItem.sharedThread.interrupt();
		}
	}
	
	private void saveResults() {
			Editor editor = HintDialog.this.getActivity().getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE).edit();
			editor.putInt(SemaphoreItem.gameStep.name(), SemaphoreItem.time);
			editor.apply();
			editor.commit();		
	}
	
	private void setMessage(int prior, String current, TextView tv) {
		if (prior == 0 || (prior > SemaphoreItem.time)) {
			tv.setText("Bingo ! New best result: " + current + ((prior==0) ? "" : ", previous best result: " 
				    + String.format(Locale.US, "%4.1f",((double)prior)/10)));
			bingo = true;
		}	
		else
			tv.setText("Your current result: " + current + ((prior == 0) ? "" : ", the best result: " 
		    + String.format(Locale.US, "%4.1f",((double)prior)/10)));		
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = null;
		
		if (SemaphoreItem.gameStep == null) {
			v = inflater.inflate(R.layout.start_dialog, container);

			Button btnContinue = (Button)v.findViewById(R.id.buttonContinue);
			
			btnContinue.setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							HintDialog.this.dismiss();
						}
					
					}				
			);
			
		}
		else 
		if (SemaphoreItem.gameStep != GameStep.FINAL)
		{

			v = inflater.inflate(R.layout.hint_dialog, container);
			//Let's appoint reset listener
			Button btnReset    = (Button)v.findViewById(R.id.buttonReset);
			Button btnContinue = (Button)v.findViewById(R.id.buttonContinue);
			
			bingo = false;
			
			int prior = this.getActivity().getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE).getInt(SemaphoreItem.gameStep.name(), 0);
			String current = SemaphoreItem.timerValue;
			
			TextView tv = (TextView)v.findViewById(R.id.resultText);
			
			setMessage(prior, current, tv);
			
			btnReset.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (SemaphoreItem.gameStep != null) {
							if (SemaphoreItem.gameStep != GameStep.SIMPLE_FIRST)
								SemaphoreItem.gameStep = GameStep.values()[SemaphoreItem.gameStep.ordinal()-1];
							else
								SemaphoreItem.gameStep = null;
						}
						HintDialog.this.dismiss();
					}
				
				}				
			);
		
			btnContinue.setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						//We open shared result;
						if (bingo) {
							saveResults();
							bingo = false;
						}
						
						
						HintDialog.this.dismiss();
					}
				
				}				
			);		
			
		}
		else if (SemaphoreItem.gameStep == GameStep.FINAL) {

			v = inflater.inflate(R.layout.end_dialog, container);
			//Let's appoint reset listener
			Button btnRestart    = (Button)v.findViewById(R.id.buttonRestart);
			Button btnQuit      = (Button)v.findViewById(R.id.buttonQuit);
			Button btnReplay   = (Button)v.findViewById(R.id.buttonReplay);
			
			bingo = false;
			int prior = this.getActivity().getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE).getInt(SemaphoreItem.gameStep.name(), 0);
			String current = SemaphoreItem.timerValue;
			
			TextView tv = (TextView)v.findViewById(R.id.resultText2);
			
			setMessage(prior, current, tv);

			btnRestart.setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (bingo) {
								saveResults();
								bingo = false;
							}
							SemaphoreItem.gameStep = null;
							HintDialog.this.dismiss();
						}
					
					}				
				);
			

			btnQuit.setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
							//We open shared result;
							if (bingo) {
								saveResults();
								bingo = false;
							}
							
							
							SemaphoreItem.gameStep = GameStep.STOP;
							HintDialog.this.dismiss();
						}
					
					}				
				);
			
			btnReplay.setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (SemaphoreItem.gameStep != null) {
								if (SemaphoreItem.gameStep != GameStep.SIMPLE_FIRST)
									SemaphoreItem.gameStep = GameStep.values()[SemaphoreItem.gameStep.ordinal()-1];
								else
									SemaphoreItem.gameStep = null;
							}
							HintDialog.this.dismiss();
						}
					
					}				
				);

			
		}
		
		return v;
		
	}

		

}
