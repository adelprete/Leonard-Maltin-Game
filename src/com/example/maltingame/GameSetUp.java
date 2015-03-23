package com.example.maltingame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.prefs.Preferences;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class GameSetUp extends ActionBarActivity{
	
	private String array_spinner[];
	private TextView section_txt;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamesetup_layout);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setTitle("Back To Main Menu");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
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
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	//We initialize the game's stats including the movie categories and scores 
	public void startGame(View view){
		Intent intent = new Intent(this, PickCategory.class);
		
		Spinner spinner1 = (Spinner)findViewById(R.id.players_spinner);
		Spinner spinner2 = (Spinner)findViewById(R.id.points_spinner);
		
		String num_players = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();
		String points_to_win = spinner2.getItemAtPosition(spinner2.getSelectedItemPosition()).toString();
		
		
		//Map of all the categories and the number of movies in each
		Hashtable<String, Integer> source = new Hashtable<String,Integer>();
		HashMap<String, Integer>  category_map = new HashMap(source);
		
		category_map.put("Greatest Movie Ever Told", 9);
		category_map.put("Orlando Magic",5);
		category_map.put("Sausage Fest", 8);
		category_map.put("Nuthin But a G Thang", 3);
		category_map.put("Movies Where Brad Pitt Eats", 6);
		category_map.put("Golden Showers Playbook", 8);
		category_map.put("Quaids Awareness Month", 7);
		category_map.put("Based On A Two Story", 6);
		category_map.put("The Future That Was", 5);
		category_map.put("Hairy Maguire", 4);
		category_map.put("Wayans World", 5);
		category_map.put("Top Billed", 4);
		category_map.put("Mann Made Movies", 6);
		category_map.put("Female Bits", 4);
		category_map.put("Waist Down", 6);
		category_map.put("In Your Face", 4);
		category_map.put("Icy Dead People", 4);
		category_map.put("Put Me In Coach", 5);
		category_map.put("Dog Gone It", 4);
		category_map.put("Whose Line Is It Anyway", 5);
		category_map.put("Will Smith Loves Pussy", 3);
		category_map.put("Million Dollar Baby Arm", 5);
		category_map.put("We Are Farmers", 4);
		category_map.put("Arch Enemies", 4);
		category_map.put("Smaug Life", 6);
		category_map.put("Perfect Dorm", 7);
		category_map.put("Day Before Tomorrow", 8);
		category_map.put("Thats What She Said", 4);		
		
		Bundle bundle =new Bundle();
		bundle.putString("num_players", num_players);
		bundle.putString("points_to_win", points_to_win);
		bundle.putInt("whose_turn", 1);
		bundle.putSerializable("category_map",category_map);
		bundle.putString("flag", "A");
		int[] player_scores = new int[Integer.parseInt(num_players)+1];
		for(int i=0;i<player_scores.length;i++){
			player_scores[i] = 0;			
		}
		bundle.putIntArray("player_scores", player_scores);
		
		//this is just a list of movies that have been played in the current game so they don't come up again
		ArrayList<String> movies_played = new ArrayList<String>();
		bundle.putStringArrayList("movies_played", movies_played);
		
		intent.putExtras(bundle);

		startActivity(intent);
		
	}
	
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);  
	}

}
