package com.example.minesweeper;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.MenuItem;



public class MainActivity extends Activity implements android.view.View.OnClickListener {

	private mine_view mineview;
	private settings set;
	static int row, column;
	  static int Red=255, Blue=255, Green=255;
	  public static int seconds = 0;
	 static boolean counter_start=true;
	NumberPicker np;
	NumberPicker n;
	private SharedPreferences pr;
   Timer t;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
          setContentView(R.layout.main);
        TextView txt =(TextView) findViewById(R.id.title);
        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/disney.ttf");
        txt.setTypeface(font);
        // Set up click listeners for all the buttons
           View newButton = findViewById(R.id.new_button);
           newButton.setOnClickListener(this);
           View exitButton = findViewById(R.id.exit_button);
           exitButton.setOnClickListener(this);
           View settingsButton = findViewById(R.id.settings_button);
           settingsButton.setOnClickListener(this);
           np = (NumberPicker) findViewById(R.id.numberPicker1);
	       n=  (NumberPicker)findViewById(R.id.numberPicker2);
           View about_button = findViewById(R.id.about_button);
        about_button.setOnClickListener(this);

    }
    
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.new_button:
        	seconds=0;
        	final mine_view mineview;
        	  mineview = new mine_view(this);
        	  mineview.generate_mines(50);
           setContentView(R.layout.new_game);
            final View newgamebutton=findViewById(R.id.button1);
           //final Button b = (Button) findViewById(R.id.button1);
         //   b.setBackgroundResource(R.drawable.smiley);


            newgamebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View t) {
                    mineview.generate_mines(50);

                    mineview.invalidate();
                }
            });
           
           
        	LinearLayout l = (LinearLayout) findViewById(R.id.linear);
 	       LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
 	        mineview.setLayoutParams(lparam);
            l.addView(mineview);
            
            Music.play(this,R.raw.main);
            
            //Declare the timer
             t = new Timer();
            //Set the schedule function and rate
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                        	TextView tv = (TextView)findViewById(R.id.unflagged_mines);
                    	      tv.setText(String.valueOf(mine_view.total_mines));
                    	      
                    	      pr = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    	      int best_time = pr.getInt("best_time",0);
                    	      TextView t1 = (TextView) findViewById(R.id.besttime);
                    	      t1.setText(String.valueOf(best_time));
                    	      
                            TextView tv1 = (TextView) findViewById(R.id.Timer);
                            tv1.setText(String.valueOf(seconds));
                            seconds += 1;

                        

                        }

                    });
                }

            }, 0, 1000);
         
      	  break;
        case R.id.exit_button:
           finish();
           break;

            case R.id.about_button:
            Intent i = new Intent(this,about.class);
                startActivity(i);
                break;

            case R.id.settings_button:
        	Music.play(this,R.raw.main);
        	Intent j = new Intent(this,settings.class);
        	startActivity(j);
       
            break;
        }
     }
    
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        t.cancel();
        t=null;

        startActivity(new Intent(this, MainActivity.class));
    }
    
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
     }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Prefs.class));
                break;
        }
        return false;
    }



    @Override
     protected void onResume() {
        super.onResume();
        Music.play(this, R.raw.game);
     }

     @Override
     protected void onPause() {
        super.onPause();
        Music.stop(this);
     }
   
}

	
	
	


