package com.example.minesweeper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class settings extends Activity implements android.view.View.OnClickListener   {
	 SeekBar redseekbar, greenseekbar, blueseekbar;
	  static   TextView txt;
    NumberPicker np;
	   NumberPicker n;
	    static int SeekR, SeekB, SeekG;
	   // savebutton.setOnClickListener(this);
		
	    
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.settings);
	       np = (NumberPicker) findViewById(R.id.numberPicker1);
	       n=  (NumberPicker)findViewById(R.id.numberPicker2);
	          np.setMinValue(4);
		      np.setMaxValue(16);
		      np.setWrapSelectorWheel(true);
		      np.setValue(3);
		      
		  
		      n.setMinValue(4);
		      n.setMaxValue(16);
		      n.setWrapSelectorWheel(true);
		      n.setValue(3);
		      
		      redseekbar = (SeekBar) findViewById(R.id.seekred);
		      greenseekbar = (SeekBar) findViewById(R.id.seekgreen);
		      blueseekbar = (SeekBar) findViewById(R.id.seekblue);
		      
		      redseekbar.setOnSeekBarChangeListener(seekBarChangeListener);
		      greenseekbar.setOnSeekBarChangeListener(seekBarChangeListener);
		      blueseekbar.setOnSeekBarChangeListener(seekBarChangeListener);
		      
		      txt = (TextView) findViewById(R.id.display_color);

		      View savebutton = findViewById(R.id.savesettings);
			   savebutton.setOnClickListener(this);
            TextView t =(TextView) findViewById(R.id.settings);
            TextView g = (TextView) findViewById(R.id.gridsettings);
            TextView c = (TextView) findViewById(R.id.colorsettings);
            Typeface font = Typeface.createFromAsset(getAssets(),"fonts/disney.ttf");
            t.setTypeface(font);
            g.setTypeface(font);
            c.setTypeface(font);

        }
	    
	
	  private SeekBar.OnSeekBarChangeListener seekBarChangeListener= new SeekBar.OnSeekBarChangeListener()
	      {

	      @Override
	      public void onProgressChanged(SeekBar seekBar, int progress,
	      boolean fromUser) {
	    	  SeekR = redseekbar.getProgress();
	    	  SeekB = blueseekbar.getProgress();
	    	  SeekG = greenseekbar.getProgress();
	    	  txt.setBackgroundColor(  0xff000000
                  + SeekR * 0x10000
                  + SeekG * 0x100
                  + SeekB);
               
	    	  
	      // TODO Auto-generated method stub
	    	  
	      }

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	} 	      };


	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		MainActivity.row=np.getValue();
		MainActivity.column=n.getValue();
		MainActivity.Red=SeekR;
		MainActivity.Blue=SeekB;
		MainActivity.Green=SeekG;
		finish();
		
			
	}

}