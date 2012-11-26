/*******************************************************************************
 * Project: CatApps
 * File: src/com/hogg/catapps/cat/Cat.java
 * 
 * Description: 
 * 		Implements the Cat class. Each of the player's cats will be defined as
 * 		a Cat class variable.
 * 
 * Contributors:
 * 		Drew Burden
 * 		Daniel Thomas
 * 
 * Copyright � 2012 Hogg Studios
 * All rights reserved.
 ******************************************************************************/

package com.hogg.catapps.cat;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.simulation.Simulation.States;
import com.hogg.catapps.ui.meter.HungerMeter;
import com.hogg.catapps.ui.meter.MoodMeter;
import com.hogg.catapps.ui.meter.ThirstMeter;
import com.hogg.catapps.ui.meter.HeartsMeter;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

public class Cat {
	Activity activity;
	String name;
	Sex sex;
	// Maybe hardcore should be in Player.java?
	int hardcoreMultiplier = 3; // 1 if hardcore, 3 if not hardcore (default)
	public HeartsMeter hearts;
	public HungerMeter hunger;
	public ThirstMeter thirst;
	public MoodMeter mood;
	public Simulation simulation;
	States currentState;
	TextView stateText;

	public Cat(String _name, Sex _sex, int initHearts, int initMood,
			int initHunger, int initThirst) {
		name = _name;
		sex = _sex;
		currentState = States.NOTHING;

		// Initialize meters with specified initial values and an
		hearts = new HeartsMeter(initHearts, 5);
		mood = new MoodMeter(initMood, 1);

		@SuppressWarnings("unused")
		int hardcoreHungerHours = 48; // number of hours it takes hunger to get
										// from 100 to 0 in hardcore mode

		// the 36 is because 60 minutes in an hour times 60 seconds in a minute
		// divided by 100 points in hunger
		//hunger = new HungerMeter(initHunger,
		//		1.0 / (hardcoreHungerHours * 36 * hardcoreMultiplier));
		hunger = new HungerMeter(initHunger, 0.5);

		@SuppressWarnings("unused")
		int hardcoreThirstHours = 24; // number of hours it takes thirst to get
										// from 100 to 0 in hardcore mode

		// the 36 is because 60 minutes in an hour times 60 seconds in a minute
		// divided by 100 points in thirst
		//thirst = new ThirstMeter(initThirst,
		//		1.0 / (hardcoreThirstHours * 36 * hardcoreMultiplier));
		thirst = new ThirstMeter(initThirst, 1);
		
		simulation = new Simulation();
	}
	
	public void setActivity(Activity _activity) {
		activity = _activity;
	}

	// toggles hardcore mode on/off
	public void toggleHardcore() {
		if (hardcoreMultiplier == 3)
			hardcoreMultiplier = 1;
		else
			hardcoreMultiplier = 3;
	}

	public void startHearts(Activity activity, int progressBar, int textView) {
		hearts.startTracking(activity, progressBar, textView);
	}

	public void startMood(Activity activity, int progressBar, int textView) {
		mood.startTracking(activity, progressBar, textView);
	}

	public void startHunger(Activity activity, int progressBar, int textView) {
		hunger.startTracking(activity, progressBar, textView);
	}

	public void startThirst(Activity activity, int progressBar, int textView) {
		thirst.startTracking(activity, progressBar, textView);
	}

	public void stopTracking() {
		//hearts.stopTracking(); // Hearts meter
		hunger.stopTracking(); // Hunger meter
		thirst.stopTracking(); // Thirst meter
		mood.stopTracking(); // Mood meter
	}

	// Update all meters
	public void update() {
		updateHearts();
		updateHunger();
		updateThirst();
		updateMood();
	}

	// Update hearts meter
	public void updateHearts() {
		hearts.update();
	}

	// Update hunger meter
	public void updateHunger() {
		hunger.update();
	}

	// Update thirst meter
	public void updateThirst() {
		thirst.update();
	}

	// Update mood meter
	public void updateMood() {
		mood.update();
	}

	// Get and set name
	public void setName(String _name) {
		name = _name;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	// Get and set sex
	public void setSex(Sex _sex) {
		sex = _sex;
	}

	public Sex getSex() {
		return sex;
	}
	
	//Get and set currentState
	public void setState(States _currentState) {
		currentState = _currentState;
		updateStateText();
	}
	
	public States getState() {
		return currentState;
	}
	
	public void updateStateText() {
		Log.d("Debug", currentState.name);
		stateText = (TextView) activity.findViewById(R.id.textState);
		Runnable shit_in_my_asshole_you_motherfucker_BITCH = new Runnable() {

			public void run() {
				stateText.setText(currentState.name);
			}
			
		};
		activity.runOnUiThread(shit_in_my_asshole_you_motherfucker_BITCH);
	}
}
