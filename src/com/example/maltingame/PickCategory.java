package com.example.maltingame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.SQLException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PickCategory extends ActionBarActivity {
	
	TextView text1;
	Button button1,button2,button3;
	ArrayList<String> movies_played;
	String category;
	Bundle bundle;
	int[] player_scores;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickcategory_layout);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        
        Intent intent = getIntent();
        bundle = intent.getExtras();
        
        String flag = bundle.getString("flag");
        int whose_turn = bundle.getInt("whose_turn");
        movies_played = bundle.getStringArrayList("movies_played");
        player_scores = bundle.getIntArray("player_scores");
        HashMap<String, Integer> category_map= (HashMap<String, Integer>) bundle.getSerializable("category_map");
        Set keys = category_map.keySet();
        
        text1 = (TextView) findViewById(R.id.game_msg);
        button1 = (Button) findViewById(R.id.category1);
        button2 = (Button) findViewById(R.id.category2);
        button3 = (Button) findViewById(R.id.category3);
        
        text1.setText("Player " + Integer.toString(whose_turn) + "'s turn");
        
        String[] categories = (String[]) keys.toArray(new String[keys.size()]);
        
        initializeButtons(categories,category_map);
        
        if (flag.equals("A")){
        	int duration = Toast.LENGTH_LONG;
        	Context context = getApplicationContext();
    		Toast toast = Toast.makeText(context, "Use Question marks to see descriptions", duration);
    		toast.getView().setBackgroundColor(Color.DKGRAY);
    		toast.show();
    		
        }

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
		    case android.R.id.home:
	    		Intent intent = new Intent(this,MainActivity.class);
	    		startActivity(intent);
	    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	            return true;
	            
	        case R.id.scores:
	        	String scores_str = "";
	        	
	        	for(int i=1;i<player_scores.length;i++){
	        		scores_str += "Player "+Integer.toString(i)+": " + Integer.toString(player_scores[i]);
	        		
	        		if (i+1 != player_scores.length)
	        			scores_str += "\n";
	        	}
	        	TextView myMsg = new TextView(this);
	        	myMsg.setTextSize(30);
	        	myMsg.setGravity(Gravity.CENTER);
	        	myMsg.setText(scores_str);
	        	new AlertDialog.Builder(this)
	            .setTitle("Player Scores")
	            //.setMessage(scores_str)
	            .setView(myMsg)
	            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) { 
	                    // continue
	                }
	             })
	             .show();
	        	
	        	
	        	
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/*
	 * We intialize the categories.
	 * We randomly go over the categories in the category_map and find those that still have movies in them.
	 * We then assign a category to each button.
	 */
	public void initializeButtons(String[] categories, HashMap<String, Integer> category_map){
		
		Random rand = new Random();
		
		while(button1.getText() == ""){
			
			int randomNum = rand.nextInt(categories.length);
			
			category = categories[randomNum];
			
			if(category_map.get(category) == 0){
				continue;
			}
			
			String packageName = getPackageName();
			
			String category_lookup = category.replaceAll("\\s+","") + "_array";
			int resId = getResources().getIdentifier(category_lookup, "array", packageName);
			String[] category_movies = getResources().getStringArray(resId);
			
			int i;
			for (i=0;i<category_movies.length;i++){
				if (!movies_played.contains(category_movies[i])){
					button1.setText(category);
					break;
				}
			}

		}
		
		while(button2.getText() == ""){
			
			int randomNum = rand.nextInt(categories.length);
			
			category = categories[randomNum];
			
			if (category != button1.getText()){
				
				if(category_map.get(category) == 0){
					continue;
				}
				
				String packageName = getPackageName();
				
				String category_lookup = category.replaceAll("\\s+","") + "_array";
				int resId = getResources().getIdentifier(category_lookup, "array", packageName);
				String[] category_movies = getResources().getStringArray(resId);
				
				int i;
				for (i=0;i<category_movies.length;i++){
					if (!movies_played.contains(category_movies[i])){
						button2.setText(category);
						break;
					}
				}
			}

		}
		
		while(button3.getText() == ""){
			
			int randomNum = rand.nextInt(categories.length);
			
			category = categories[randomNum];
			
			if (category != button1.getText() && category != button2.getText()){
				
				if(category_map.get(category) == 0){
					continue;
				}
				
				String packageName = getPackageName();
				
				String category_lookup = category.replaceAll("\\s+","") + "_array";
				int resId = getResources().getIdentifier(category_lookup, "array", packageName);
				String[] category_movies = getResources().getStringArray(resId);
				
				int i;
				for (i=0;i<category_movies.length;i++){
					if (!movies_played.contains(category_movies[i])){
						button3.setText(category);
						break;
					}
				}
			}

		}
		
	}
	/*
	 * button hints give the users a description of the category.  
	 */
	public void btn1Hint(View view){

		category = (String) button1.getText();
		
		String packageName = getPackageName();
		String category_lookup = category.replaceAll("\\s+","") + "_hint";
		int resId = getResources().getIdentifier(category_lookup, "string", packageName);
		String hint = getResources().getString(resId);
		
		Context context = getApplicationContext();
		CharSequence text = hint;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.getView().setBackgroundColor(Color.DKGRAY);
		toast.show();
		
	}
	
	public void btn2Hint(View view){

		category = (String) button2.getText();
		
		String packageName = getPackageName();
		String category_lookup = category.replaceAll("\\s+","") + "_hint";
		int resId = getResources().getIdentifier(category_lookup, "string", packageName);
		String hint = getResources().getString(resId);
		
		Context context = getApplicationContext();
		CharSequence text = hint;
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.getView().setBackgroundColor(Color.DKGRAY);
		toast.show();
		
	}
	
	public void btn3Hint(View view){

		category = (String) button3.getText();
		
		String packageName = getPackageName();
		String category_lookup = category.replaceAll("\\s+","") + "_hint";
		int resId = getResources().getIdentifier(category_lookup, "string", packageName);
		String hint = getResources().getString(resId);
		
		Context context = getApplicationContext();
		CharSequence text = hint;
		
		int duration = Toast.LENGTH_LONG;

		Toast toast = Toast.makeText(context, text, duration);
		toast.getView().setBackgroundColor(Color.DKGRAY);
		toast.show();
		
	}
	
	/*
	 * These next functions pass the category they choose to the next activity. Where we will then pick the years.
	 */
	public void startBtn1MovieSelection(View view){
		Intent intent = new Intent(this, PickYear.class);

		category = (String) button1.getText();
		bundle.putString("category", category);
		
		intent.putExtras(bundle);

		startActivity(intent);
	}
	
	public void startBtn2MovieSelection(View view){
		Intent intent = new Intent(this, PickYear.class);
		
		category = (String) button2.getText();
		bundle.putString("category", category);
		
		intent.putExtras(bundle);

		startActivity(intent);
	}
	
	public void startBtn3MovieSelection(View view){
		Intent intent = new Intent(this, PickYear.class);
		
		category = (String) button3.getText();
		bundle.putString("category", category);
		
		intent.putExtras(bundle);

		startActivity(intent);
	}
	
	@Override
	public void onBackPressed() {
		//Disabled
	}

}
