package com.maxwelldaemon;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SemaphoreItem {
	
	public static Thread sharedThread;
	public volatile static GameStep gameStep = null;
	public volatile static String   timerValue = null;
	public volatile static int time;
	
	private static ReentrantLock lock = new ReentrantLock();
	
	public static void getLock() {
		lock.lock();
	}
	
	public static void unLock() {
		try {
			lock.unlock();
		}
		catch(IllegalMonitorStateException e) {
			
		}
	}
	
	static private final AtomicBoolean ab = new AtomicBoolean(false);
	
	public static void setTrue() {
		ab.getAndSet(true);
	}
	public static void setFalse() {
		ab.getAndSet(false);
	}

	
	public static boolean get() {
		return ab.get();
	}

	private SemaphoreItem() {
		// TODO Auto-generated constructor stub
	}

	public static void allocateResults(Activity activity, Map<Integer, String> values) {
		try {
			FileOutputStream fos = activity.openFileOutput("myresults", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(values);
			oos.flush();
			oos.close();
		}
		catch(IOException e) {}
	}
	
	public static Map<Integer, String> getResults(Activity activity) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		try {
			FileInputStream fis = activity.openFileInput("myresults");
			ObjectInputStream ois = new ObjectInputStream(fis);
			for(Entry<Integer, String> e : ((Map<Integer, String>)ois.readObject()).entrySet()) {
			    map.put(e.getKey(), e.getValue());
			}
		}
		catch(IOException e) {
			
		}
		catch(ClassNotFoundException e) {
			
		}
		return map;
	}	
	
	
}
