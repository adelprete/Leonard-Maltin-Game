package com.example.maltingame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PickYear extends ActionBarActivity{

	Bundle bundle;
	TextView text1;
	Button button1,button2,button3;
	String movie1_title_lookup, movie2_title_lookup, movie3_title_lookup;
	String button1_movie = "";
	String button2_movie = "";
	String button3_movie = "";
	int[] player_scores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickyear_layout);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        
        Intent intent = getIntent();
        bundle = intent.getExtras();
        
        int whose_turn = bundle.getInt("whose_turn");
        String category = bundle.getString("category");
        ArrayList<String> movies_played = bundle.getStringArrayList("movies_played");
        player_scores = bundle.getIntArray("player_scores");
        
        HashMap<String, Integer> category_map = (HashMap<String, Integer>) bundle.getSerializable("category_map");
        
        text1 = (TextView) findViewById(R.id.game_msg);
        button1 = (Button) findViewById(R.id.year1);
        button2 = (Button) findViewById(R.id.year2);
        button3 = (Button) findViewById(R.id.year3);
        
        text1.setText("Player " + Integer.toString(whose_turn) + "'s turn");
        
        int movies_not_played = category_map.get(category);
        
        initializeButtons(category, movies_not_played, movies_played);
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
		    case android.R.id.home:
	    		Intent intent = new Intent(this,MainActivity.class);
	    		bundle.putString("transition","left");
	    		intent.putExtras(bundle);
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
	 * We initialize the years button choices for the players
	 * We check the list of movies that we have played so far and make sure they aren't pick-able again this game.
	 */
	public void initializeButtons(String category, int movies_not_played, ArrayList<String> movies_played){
		
		Random rand = new Random();
		
		//get movies from category to see how many movies they can choose from
		String packageName = getPackageName();
		String category_lookup = category.replaceAll("\\s+","") + "_array";
		int resId = getResources().getIdentifier(category_lookup, "array", packageName);
		String[] category_movies = getResources().getStringArray(resId);
		
		if (movies_not_played >= 0){

			while(button1.getText() == ""){
				
				int randomNum = rand.nextInt(category_movies.length);
				if (!movies_played.contains(category_movies[randomNum].replaceAll("\\s+",""))){
					String movie1_title_lookup = category_movies[randomNum].replaceAll("\\s+","") + "_title";
					resId = getResources().getIdentifier(movie1_title_lookup, "string", packageName);
					button1_movie = category_movies[randomNum].replaceAll("\\s+","");
					
					String movie_year_lookup = category_movies[randomNum].replaceAll("\\s+","") + "_year";
					resId = getResources().getIdentifier(movie_year_lookup, "string", packageName);
					button1.setText(getResources().getString(resId));
					movies_not_played--;
				}
			}
		}
		
		if (movies_not_played > 0){
			while(button2.getText() == ""){
				
				int randomNum = rand.nextInt(category_movies.length);
				if (!movies_played.contains(category_movies[randomNum].replaceAll("\\s+","")) && !category_movies[randomNum].equals(button1_movie)){
					String movie2_title_lookup = category_movies[randomNum].replaceAll("\\s+","") + "_title";
					resId = getResources().getIdentifier(movie2_title_lookup, "string", packageName);
					button2_movie = category_movies[randomNum].replaceAll("\\s+","");
					
					String movie_year_lookup = category_movies[randomNum].replaceAll("\\s+","") + "_year";
					resId = getResources().getIdentifier(movie_year_lookup, "string", packageName);
					button2.setText(getResources().getString(resId));
					movies_not_played--;
				}
			}
		}
		else{
			button2.setVisibility(View.GONE);
		}
		
		if (movies_not_played > 0){
			while(button3.getText() == ""){
				
				int randomNum = rand.nextInt(category_movies.length);
				if (!movies_played.contains(category_movies[randomNum].replaceAll("\\s+","")) && !category_movies[randomNum].equals(button1_movie) && !category_movies[randomNum].equals(button2_movie)){
					
					String movie_year_lookup = category_movies[randomNum].replaceAll("\\s+","") + "_year";
					resId = getResources().getIdentifier(movie_year_lookup, "string", packageName);
					button3.setText(getResources().getString(resId));
					button3_movie = category_movies[randomNum].replaceAll("\\s+","");
					movies_not_played--;
				}
			}
		}
		else{
			button3.setVisibility(View.GONE);
		}
	}
	
	/* 
	 * Depending on the year chosen one of the next three functions will be triggered.  
	 * We pass the movie chosen to the next Activity
	 */
	public void yearOneChosen(View view){
		
		Intent intent = new Intent(this, GuessNames.class);
		
		bundle.putString("movie", button1_movie);
		
		intent.putExtras(bundle);

		startActivity(intent);

	}
	
	public void yearTwoChosen(View view){
		
		Intent intent = new Intent(this, GuessNames.class);
		
		bundle.putString("movie", button2_movie);
		
		intent.putExtras(bundle);

		startActivity(intent);

	}
	
	public void yearThreeChosen(View view){
		
		Intent intent = new Intent(this, GuessNames.class);
		
		bundle.putString("movie", button3_movie);
		
		intent.putExtras(bundle);

		startActivity(intent);

	}
	
	@Override
	public void onBackPressed() {
		//Disabled
	}
}
