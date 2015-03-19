package com.example.maltingame;

import java.util.ArrayList;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class GuessNames extends ActionBarActivity{
	
	String movie;
	Bundle bundle;
	TextView category_str, year_str, rating_str, description_str;
	TextView playerturn_str,directions_str,names_str,option1_str,or_str,option2_str;
	TextView name_list,player_instructions,player_instructions2;
	Button force_guess_btn,next_btn1,next_btn2,next_btn3;
	int num_players,whose_turn,previous_player, previous_bid,max_negative_bid;
	Spinner spinner1,spinner2;
	int[] player_scores;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guessnames_layout);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        
        Intent intent = getIntent();
        bundle = intent.getExtras();
        
        num_players = Integer.parseInt(bundle.getString("num_players"));
        whose_turn = bundle.getInt("whose_turn");
        String category = bundle.getString("category");
        movie = bundle.getString("movie");
        ArrayList<String> movies_played = bundle.getStringArrayList("movies_played");
        player_scores = bundle.getIntArray("player_scores");
        
        //All activities for first part
        directions_str = (TextView) findViewById(R.id.player_directions);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        names_str = (TextView) findViewById(R.id.names_str);
        next_btn1 = (Button) findViewById(R.id.next_btn1);
        
        //All activities for second part
        option1_str = (TextView) findViewById(R.id.option1_str);
        option2_str = (TextView) findViewById(R.id.option2_str);
        or_str = (TextView) findViewById(R.id.or_str);
        force_guess_btn = (Button) findViewById(R.id.force_guess_btn);
        next_btn2 = (Button) findViewById(R.id.next_btn2); 
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        
        //All activities for third part
        name_list = (TextView) findViewById(R.id.name_list);
        player_instructions = (TextView) findViewById(R.id.player_instructions);
        player_instructions2 = (TextView) findViewById(R.id.player_instructions2);
        next_btn3 = (Button) findViewById(R.id.next_btn3); 
        
        initializeCategoryText(category);
        
        playerturn_str = (TextView) findViewById(R.id.playerturn_heading);
        playerturn_str.setText("Player " + Integer.toString(whose_turn) + "'s Turn");
        
        
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
	    		startActivity(intent);
	    		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	            return true;	    
		    case R.id.scores:
	        	String scores_str = "";
	        	
	        	for(int i=1;i<player_scores.length;i++){
	        		scores_str += "Player "+Integer.toString(i)+":          " + Integer.toString(player_scores[i]);
	        		
	        		if (i+1 != player_scores.length)
	        			scores_str += "\n";
	        	}
	        	TextView myMsg = new TextView(this);
	        	myMsg.setTextSize(30);
	        	myMsg.setGravity(Gravity.CENTER);
	        	myMsg.setText(scores_str);
	        	myMsg.setWidth(200);
	        	AlertDialog alertdialog = new AlertDialog.Builder(this)
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
	
	public void initializeCategoryText(String category){
		
		String lookup;
		int resId;
		String packageName = getPackageName();
		
		category_str = (TextView) findViewById(R.id.category_text);
		category_str.setText(category);
		
		year_str = (TextView) findViewById(R.id.year_text);
		lookup = movie.replaceAll("\\s+","") + "_year";
		resId = getResources().getIdentifier(lookup, "string", packageName);
		year_str.setText(getResources().getString(resId));
		
		rating_str = (TextView) findViewById(R.id.rating_text);
		lookup = movie.replaceAll("\\s+","") + "_rating";
		resId = getResources().getIdentifier(lookup, "string", packageName);
		rating_str.setText(getResources().getString(resId));
		
		description_str = (TextView) findViewById(R.id.description_text);
		lookup = movie.replaceAll("\\s+","") + "_description";
		resId = getResources().getIdentifier(lookup, "string", packageName);
		description_str.setText(getResources().getString(resId));
		
		String actor_lookup = movie.replaceAll("\\s+","") + "_array";
		resId = getResources().getIdentifier(actor_lookup, "array", packageName);
		String[] actors = getResources().getStringArray(resId);
		
		directions_str.setText("From the bottom up, how many names from the cast list "
				+ "do you need to see to guess the movie? There are a total of " + Integer.toString(actors.length) 
				+ " names.");
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		max_negative_bid = 0-actors.length;
		for(int i=actors.length;i>=max_negative_bid;i--){
			numbers.add(i);			
		}
		
		
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, numbers);
        spinner1.setAdapter(adapter);
        
		
		//category.replaceAll("\\s+","") + "_array";
		//resId = getResources().getIdentifier(movie_year_lookup, "string", packageName);
		//category_str.setText(getResources().getString(resId));
		
	}
	
	public void nextMove(View view){
		
		if(spinner1.getVisibility() == View.VISIBLE)
			previous_bid = (Integer) spinner1.getSelectedItem();
		else{
			previous_bid = (Integer) spinner2.getSelectedItem();
		}
		
		if (previous_bid == max_negative_bid){
			spinner1.setVisibility(View.GONE);
			names_str.setVisibility(View.GONE);
			next_btn1.setVisibility(View.GONE);
			getNames(view);
		}
		else{
		
			spinner1.setVisibility(View.GONE);
			names_str.setVisibility(View.GONE);
			next_btn1.setVisibility(View.GONE);
			
			option1_str.setVisibility(View.VISIBLE);
			force_guess_btn.setVisibility(View.VISIBLE);
			or_str.setVisibility(View.VISIBLE);
			option2_str.setVisibility(View.VISIBLE);
			spinner2.setVisibility(View.VISIBLE);
			next_btn2.setVisibility(View.VISIBLE);
			
			ArrayList<Integer> numbers = new ArrayList<Integer>();
			for(int i=previous_bid-1;i>=max_negative_bid;i--){
				numbers.add(i);			
			}
			
			ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
	                android.R.layout.simple_spinner_item, numbers);
	        spinner2.setAdapter(adapter);
	        
			previous_player = whose_turn;
			whose_turn++;
			
			if (whose_turn > num_players)
				whose_turn = 1;
			
			playerturn_str.setText("Player " + Integer.toString(whose_turn) + "'s Turn");
			
			
			if (previous_bid == 1){
				directions_str.setText("Player " + Integer.toString(previous_player) + " says they can name the movie after "
						+ "seeing only 1 name.  What do you want to do?");
			}
			else{
				directions_str.setText("Player " + Integer.toString(previous_player) + " says they can name the movie after seeing "
					+ Integer.toString(previous_bid) + " names.  What do you want to do?");
			}
		}
	}
	
	public void getNames(View view){
		
		String packageName = getPackageName();
		String actor_lookup = movie.replaceAll("\\s+","") + "_array";
		int resId = getResources().getIdentifier(actor_lookup, "array", packageName);
		String[] actors = getResources().getStringArray(resId);
		
		option1_str.setVisibility(View.GONE);
		force_guess_btn.setVisibility(View.GONE);
		or_str.setVisibility(View.GONE);
		option2_str.setVisibility(View.GONE);
		spinner2.setVisibility(View.GONE);
		next_btn2.setVisibility(View.GONE);
		
		name_list.setVisibility(View.VISIBLE);
		player_instructions.setVisibility(View.VISIBLE);
		player_instructions2.setVisibility(View.VISIBLE);
		next_btn3.setVisibility(View.VISIBLE);
		
		if (previous_bid > 0){
			String name_str = "";
			for (int i=1; i<=previous_bid;i++){
				name_str += Integer.toString(actors.length-(i-1)) + ". " + actors[actors.length-i] + "\n";
			}
			
			name_list.setText(name_str);
			
			whose_turn = previous_player;
			playerturn_str.setText("Player " + Integer.toString(whose_turn) + "'s Turn");
			directions_str.setText("Here are your " + Integer.toString(previous_bid) + " names..");
		}
		else if (previous_bid < -1){
			directions_str.setVisibility(View.GONE);
			name_list.setVisibility(View.GONE);
			player_instructions.setText("Say aloud the movie that you think it is AND the top " + Integer.toString(previous_bid * -1) +
					" names on the casting list in order.");
			player_instructions2.setText("Once You've announced your guesses, reveal the movie");
		}
		else if (previous_bid == -1){
			player_instructions.setText("Say aloud the title of the movie that you think it is AND the top "
					+ "billed actor on the cast list.");
			directions_str.setVisibility(View.GONE);
			name_list.setVisibility(View.GONE);
		}
		else {
			name_list.setVisibility(View.GONE);
			directions_str.setVisibility(View.GONE);
			player_instructions.setText("Say aloud the title of the movie that you think it is.");
		}
	}
	
	public void runResults(View view){
		
		Intent intent = new Intent(this, MovieReveal.class);
		
		bundle.putString("movie", movie);
		bundle.putInt("previous_bid", previous_bid);
		bundle.putInt("whose_turn", whose_turn);
		
		intent.putExtras(bundle);

		startActivity(intent);
				
	}
	
	@Override
	public void onBackPressed() {
		//Disabled
	}
	
}
