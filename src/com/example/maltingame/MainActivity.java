package com.example.maltingame;

import java.net.HttpURLConnection;
import java.net.URL;

import com.example.maltingame.R;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	final Context context = this;
	static TextView version_text,please_wait;
	static Button start_btn, howtoplay_btn, refresh_btn;
    static String connected = "waiting"; 
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        
        start_btn = (Button)findViewById(R.id.start_btn);
        howtoplay_btn = (Button)findViewById(R.id.howtoplay_btn);
        refresh_btn = (Button)findViewById(R.id.refresh_btn);
        please_wait = (TextView)findViewById(R.id.please_wait);
        
        ImageView img = (ImageView)findViewById(R.id.dlm);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.douglovesmovies.com"));
                startActivity(intent);
            }
        });
        
        
        refreshConnection(null);
		
    }
	
	private class PingWebpageTask extends AsyncTask<String, Void, String>{
		@Override
        protected String doInBackground(String... urls) {
			ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
			if(netInfo != null){
				try {
					URL url = new URL(urls[0]);
		            HttpURLConnection urlConnect = (HttpURLConnection)url.openConnection();
					return Integer.toString(urlConnect.getResponseCode());
				} catch (Exception e) {              
		            e.printStackTrace();
		            
		            return e.getMessage();
		        }
			}
			else{
				return "No internet connection";
			}
            // params comes from the execute() call: params[0] is the url.
        }
		// onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            connected = result;
            if (connected.equals("200")){
            	please_wait.setVisibility(View.GONE);
            	start_btn.setVisibility(View.VISIBLE);
            	howtoplay_btn.setVisibility(View.VISIBLE);
            }
            else{
            	version_text.setText(result);
            	please_wait.setText("Unable to connect.");
            	refresh_btn.setVisibility(View.VISIBLE);
            }
       }
	}
	
	public void refreshConnection(View view){
		
		PackageInfo pInfo = null;
		try {
			pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String version = pInfo.versionName;
		version_text = (TextView)findViewById(R.id.version);
        version_text.setText("v" + version);
        
		please_wait.setText("Connecting Please wait...");
		refresh_btn.setVisibility(View.GONE);
		
		this.new PingWebpageTask().execute("http://www.saturdayball.com");
	}
	
	public void goToGameSetup(View view){
		
		if (connected.equals("waiting")){
			//do nothing
		}
		else if (!connected.equals("200")){
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setTitle("Error");
			alertDialogBuilder
			.setMessage("The Leonard Maltin Game is unavailable.  Make sure you have a connection to the internet.")
			.setCancelable(false)
			.setPositiveButton("ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
				}
			  });

			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
		}
		
		else{
			Intent intent = new Intent(this, GameSetUp.class);
			startActivity(intent);
		}
		
	}
	
	public void goToHowToPlay(View view){
		Intent intent = new Intent(this, HowToPlay.class);
		startActivity(intent);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
	*/
}
