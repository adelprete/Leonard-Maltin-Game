package com.example.maltingame;

import java.util.ArrayList;
import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieReveal extends ActionBarActivity{
	
	Bundle bundle;
	TextView movie_title,names_title,name_list,player_directions;
	ImageView movie_image;
	Button yes_btn,no_btn,continue_btn;
	int whose_turn;
	int[] player_scores;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviereveal_layout);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(android.R.color.transparent);
        
        Intent intent = getIntent();
        bundle = intent.getExtras();
        String movie = bundle.getString("movie");
        int previous_bid = bundle.getInt("previous_bid");
        whose_turn = bundle.getInt("whose_turn");
        player_scores=bundle.getIntArray("player_scores");
        HashMap<String, Integer>  category_map = (HashMap<String, Integer>) bundle.getSerializable("category_map");
        
        //increase num played numbers
        int category_num = category_map.get(bundle.getString("category"));
        
        category_num = category_num - 1;
        category_map.put(bundle.getString("category"), category_num);
        bundle.putSerializable("category_map", category_map);
        
        //Remember that we played this movie
        ArrayList<String> movies_played = bundle.getStringArrayList("movies_played");
        movies_played.add(movie.replaceAll("\\s+",""));
        bundle.putStringArrayList("movies_played", movies_played);
        
        movie_title = (TextView)findViewById(R.id.movie_title);
        movie_image = (ImageView)findViewById(R.id.movie_image);
        
        String packageName = getPackageName();
        
        String lookup = movie.replaceAll("\\s+","") + "_title";
		int resId = getResources().getIdentifier(lookup, "string", packageName);
		movie_title.setText(getResources().getString(resId));
        
		lookup = movie.replaceAll("\\s+","") + "_image";
		resId = getResources().getIdentifier(lookup, "string", packageName);
		
		double screen_size = getScreenSize();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		
		String image_url;
		if(width <= 1000){
			image_url = "http://image.tmdb.org/t/p/w1000";
		}
		else{
			image_url = "http://image.tmdb.org/t/p/original";
		}
		
		Picasso.with(this).load(image_url + getResources().getString(resId)).into(movie_image);
		
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f , 1.0f );
        fadeIn.setStartOffset(1500);
        fadeIn.setDuration(2250);
        movie_title.startAnimation(fadeIn);
        movie_image.startAnimation(fadeIn);
        
        movie_title.setVisibility(View.VISIBLE);
        movie_image.setVisibility(View.VISIBLE);
        
        if(previous_bid <= -1){
        	names_title = (TextView)findViewById(R.id.names_title);
        	name_list = (TextView)findViewById(R.id.name_list);
        	if(previous_bid == -1){
        		names_title.setText("The top billed actor was..");
        	}
        	else{
        		names_title.setText("The top " + Integer.toString(previous_bid*-1) + " actors were..");
        	}
        	
        	String actor_lookup = movie + "_array";
    		resId = getResources().getIdentifier(actor_lookup, "array", packageName);
    		String[] actors = getResources().getStringArray(resId);
    		
    		String name_str = "";
			for (int i=0; i<previous_bid*-1;i++){
				name_str += Integer.toString(i+1) + ". " + actors[i] + "\n";
			}
			name_list.setText(name_str);
			
			names_title.startAnimation(fadeIn);
			name_list.startAnimation(fadeIn);
			names_title.setVisibility(View.VISIBLE);
	        name_list.setVisibility(View.VISIBLE);
    		
        }
        
        player_directions = (TextView)findViewById(R.id.player_directions);
        yes_btn = (Button)findViewById(R.id.yes_btn);
        no_btn = (Button)findViewById(R.id.no_btn);
        continue_btn = (Button)findViewById(R.id.continue_btn);
        
        if (previous_bid < 0){
        	player_directions.setText("Did player " + Integer.toString(whose_turn) + " correctly guess the movie and the top " + Integer.toString(previous_bid*-1) + " actors in order?");
        }
        else{
        	player_directions.setText("Did player " + Integer.toString(whose_turn) + " guess it right?");
        }
        AlphaAnimation fadeIn2 = new AlphaAnimation(0.0f , 1.0f );
        fadeIn2.setStartOffset(5500);
        fadeIn2.setDuration(10);
        player_directions.startAnimation(fadeIn2);
        yes_btn.startAnimation(fadeIn2);
        no_btn.startAnimation(fadeIn2);
        
        player_directions.setVisibility(View.VISIBLE);
        yes_btn.setVisibility(View.VISIBLE);
        no_btn.setVisibility(View.VISIBLE);

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
	
	public void yesClick(View view){
		
		
		player_scores[whose_turn] = player_scores[whose_turn] + 1;
		
		player_directions.setText("Player " + whose_turn + " gets the point!");
		
		yes_btn.setVisibility(view.GONE);
		no_btn.setVisibility(view.GONE);
		continue_btn.setVisibility(view.VISIBLE);
		
	}
	public void noClick(View view){
		
		whose_turn++;
		if (whose_turn > Integer.parseInt(bundle.getString("num_players"))){
			whose_turn = 1;
		}
		player_scores[whose_turn] = player_scores[whose_turn] + 1;
		
		player_directions.setText("Player " + (whose_turn) + " gets the point!");
		
		yes_btn.setVisibility(view.GONE);
		no_btn.setVisibility(view.GONE);
		continue_btn.setVisibility(view.VISIBLE);
		
	}
	
	public void loopBackToCategories(View view){
		
		int points_to_win = Integer.parseInt(bundle.getString("points_to_win"));
		
		if (player_scores[whose_turn] == points_to_win){
			Intent intent = new Intent(this, WinnerScreen.class);
			bundle.putInt("winner",whose_turn);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else{
			Intent intent = new Intent(this, PickCategory.class);
			if ((whose_turn+1) > Integer.parseInt(bundle.getString("num_players"))){
				whose_turn = 1;
			}
			else{
				whose_turn++;
			}
			bundle.putInt("whose_turn", whose_turn);
			bundle.putIntArray("player_scores", player_scores);
			bundle.putString("flag", "B");
			intent.putExtras(bundle);

			startActivity(intent);
		}
	}
	
	public double getScreenSize(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		double screenInches = Math.sqrt(x+y);
		return screenInches;
	}
	
	@Override
	public void onBackPressed() {
		//Disabled
	}
	

}
