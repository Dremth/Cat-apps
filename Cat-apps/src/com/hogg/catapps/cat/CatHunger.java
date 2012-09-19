/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/CatHunger.java
 * 
 * Description: 
 * 		Implements the CatHunger class. This hold the information about the
 * 		cat's current hunger level and manages the progress bar for it.
 * 
 * Contributors:
 * 		James Garner
 * 
 * Copyright � 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.R;

import android.app.Activity;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CatHunger extends Meter {
	ProgressBar progressBarHunger;
	TextView textViewHungerPercentage;
	
	// Upon decrementing or incrementing the cat's mood will go up or down by: 10% of the incrementAmount
	double moodPercentageVariable = 0.10; 
	
	public CatHunger(int initHunger, int incAmount) {
		value = initHunger;
		incrementAmount = incAmount;
	}

	protected void update() {
		//Need to update the progress bar and the text view.
		progressBarHunger.setProgress(getValue());
		textViewHungerPercentage.setText(this + "%");
	}

	public void startTracking(Activity _activity) {
		//This will track the value of the meter as it changes.
		activity = _activity;
		
		//Must find the UI components in order to change them
		textViewHungerPercentage = (TextView) activity.findViewById(R.id.textViewHungerPercentage);
		progressBarHunger = (ProgressBar) activity.findViewById(R.id.progressBarHunger);
		
		tracking = true;
		//Create a new thread in order to actually run the cat's Hunger simulation
		new Thread(new Runnable() {
			public void run() {
				while (tracking) {
					try {
						Thread.sleep(updateWaitTime);
						
						//Sleep the appropriate time, then check to see if we are still tracking before continuing.
						if(!tracking) {
							break;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					activity.runOnUiThread(new Runnable() {
						public void run() {
							//After sleeping for the appropriate time, decrement value, and update display.
							decrement();
							update();
						}
					});
				}
			}
		}).start();
		
	}
	
	public void stopTracking() {
		tracking = false;
	}
	
}
