package com.example.maltingame;

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
import android.widget.TextView;

public class WinnerScreen extends ActionBarActivity {
	
	Bundle bundle;
	TextView winner_text;
	int[] player_scores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.winnerscreen_layout);
	    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	    ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
	    
	    winner_text = (TextView)findViewById(R.id.winner_txt);
	    
	    
	    Intent intent = getIntent();
	    bundle = intent.getExtras();
	    int winner = bundle.getInt("winner");
	    winner_text.setText("Player "+Integer.toString(winner)+ " Wins!");
	    player_scores = bundle.getIntArray("player_scores");
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
	        		scores_str += "Player "+Integer.toString(i)+":    " + Integer.toString(player_scores[i]);
	        		
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
	
	public void backToMain(View view){
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); 
		
	}
	
	public void goToCredits(View view){
		
		Intent intent = new Intent(this, Credits.class);
		startActivity(intent);
		
	}
	
	@Override
	public void onBackPressed() {
		//Disabled
	}
	
}
