package com.maxwelldaemon;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ResultFragment extends DialogFragment {

	public ResultFragment() {
		// TODO Auto-generated constructor stub
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
	
	
	class ElementListAdapter extends BaseAdapter {
	    private Context mContext;
	    final static String SETTING_FILE = "myResults";
	    SharedPreferences sp;
	    Map<String, ?> map;
	    List<String> list = new LinkedList<String>();

	    public ElementListAdapter(Context c) {
	        mContext = c;     
	        Activity a = (Activity)c;
	        sp = a.getSharedPreferences(SETTING_FILE, Context.MODE_PRIVATE);
	        if (sp != null) {
	        	map = sp.getAll();
	        }
	    }

	    @Override
	    public int getCount() {
	        return (map != null) ? map.size()+1 : 0;
	    }

	    public Object getItem(int position) {
	        return null;
	    }

	    @Override
	    public long getItemId(int position) {
	        return position;
	    }

	    OnClickListener onClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Editor editor = ElementListAdapter.this.sp.edit();
				editor.clear();
				editor.commit();
				ResultFragment.this.dismiss();
			}
			
		};
	    
	    // create a new ImageView for each item referenced by the Adapter
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	View rowView = null;
	    	LayoutInflater inflater =(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	if (position == map.size()) {
	    		rowView = inflater.inflate(R.layout.button_row, parent, false);
	    		Button btClear = (Button)rowView.findViewById(R.id.buttonClean);
	    		btClear.setOnClickListener(onClickListener);
	    	}
	    	else {
	    		rowView = inflater.inflate(R.layout.result_row, parent, false);
	    		TextView textStep = (TextView)rowView.findViewById(R.id.textStep);
	    		TextView textValue = (TextView)rowView.findViewById(R.id.textValue);
	    		textStep.setText(GameStep.values()[position].getStepName());
	    		int value = sp.getInt(GameStep.values()[position].name(), 0);
	    		textValue.setText(String.format(Locale.US, "%4.1f",((double)value)/10));
	    	}	
	        return rowView;
	    }
	    
	    
	    
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = null;
        v = (View)inflater.inflate(R.layout.result_dialog, container);
        ListView lv = (ListView)v.findViewById(R.id.resultList);
		lv.setAdapter( new ElementListAdapter(this.getActivity()) );
		return v;
		
	}
	

}
