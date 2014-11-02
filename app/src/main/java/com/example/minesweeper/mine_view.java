package com.example.minesweeper;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector;
import android.widget.Button;
import android.widget.TextView;


public class mine_view extends View {
	GestureDetector gestureDetector;
	   private float width;    // width of one tile
	   private float height;   // height of one tile
	   private int selX;       // X index of selection
	   private int selY;       // Y index of selection
	   boolean mine_present=false;
	   boolean single_tap=false;
	   static int total_mines=0;
	   int mine_grid[][];
	   int no_rows=MainActivity.row;
	   int no_cols=MainActivity.column;
       boolean double_tap=false;
       boolean color=false;
       boolean single_flag=false;
       int uncovered_cell=0;
       int mine_found=10;
       int flag_img=999;
       int flag_mine=-4;
    Button b = (Button)findViewById(R.id.button1);



    boolean expansion=false;
	  private final Rect selRect = new Rect();
	  private SharedPreferences pr;
	   public mine_view(Context context) {
		      super(context);
		      gestureDetector = new GestureDetector(context, new GestureListener());
		      setFocusable(true);
		      setFocusableInTouchMode(true);
		   }

	   @Override
	   public boolean onTouchEvent(MotionEvent event) {
		      select((int) (event.getX() / width),
                      (int) (event.getY() / height));
		      return gestureDetector.onTouchEvent(event);
	   }



	   private class GestureListener extends GestureDetector.SimpleOnGestureListener {

		   @Override
		    public boolean onDown(MotionEvent e) {
		        return true;
		    }
		    // event when double tap occurs
		    @Override
		    public boolean onDoubleTap(MotionEvent e) {
		        mine_present=false;
		        double_tap=true;
                single_tap=false;
                if(mine_grid[selX][selY]==mine_found)
		        {
		        	mine_present=true;
		        }
		        else
		        {
		        	expansion_mine(selX,selY);		    
		        }
		        invalidate();
		        return true;    
		    }
		    //event when single tap occurs
		   @Override 
		   public boolean onSingleTapConfirmed(MotionEvent e)
		   {
			   single_tap=true;
			   if (no_rows==0 && no_cols==0)
				 {
					 no_rows=4;
					 no_cols=4;

				 }
			   if(selY<no_rows)
			   {

			   if(mine_grid[selX][selY] == uncovered_cell)
		        {
		            mine_grid[selX][selY]= flag_img;
                    single_flag=true;
		            total_mines--;
		        }
		        else if(mine_grid[selX][selY]== flag_img)
		        {
		            mine_grid[selX][selY]=uncovered_cell;
                    single_flag=true;
		            total_mines++;
		        }
		        else if (mine_grid[selX][selY]==mine_found)
		        {
		            mine_grid[selX][selY]=flag_mine;
                    single_flag=true;
		            total_mines--;
		        }
		        else if (mine_grid[selX][selY]==flag_mine)
		        {
		            mine_grid[selX][selY]=mine_found;
                    single_flag=true;
		            total_mines++;
		        }
			   }
			   invalidate();
			   return true;
		   }
		   
		}
	   private void getRect(int x, int y, Rect rect) {
		      rect.set((int) (x * width), (int) (y * height), (int) (x
		            * width + width), (int) (y * height + height));
		   }
	   private void select(int x, int y) {
		      selX = Math.min(Math.max(x, 0), no_rows-1);
		      selY = Math.min(Math.max(y, 0), no_cols-1);
		      getRect(selX, selY, selRect);
		 }
	         
	 protected void onSizeChanged(int w, int h, int oldw, int oldh)
	 {
		 if (no_rows==0 && no_cols==0)
		 {
			 no_rows=4;
			 no_cols=4;
			 width = w / no_rows;
		      height = h / no_cols;
		      super.onSizeChanged(w, h, oldw, oldh);
		 }
		 else
		 {
	      width = w / no_rows;
	      height = h / no_cols;
	      super.onSizeChanged(w, h, oldw, oldh);
		 }
	 }

	 void expansion_mine (int x_coord, int y_coord)   //recursive function for expansion of empty cells
	 {
         expansion=true;
         single_tap=false;
         double_tap=false;
		 if(no_rows==0 && no_cols==0)
		 {
			 no_rows=4;
			 no_cols=4;
			// mines = no_rows*no_cols*0.2;
		 }
	    
	     int startx=0, starty=0,endx=0,endy=0,count=0;
	     if(mine_grid[x_coord][y_coord]!=100 && mine_grid[x_coord][y_coord]!=flag_img && mine_grid[x_coord][y_coord]!=mine_found && mine_grid[x_coord][y_coord]!=flag_mine )
	     {
	         if(x_coord-1<0)
	         {
	             startx=x_coord;
	         }
	         else startx= x_coord-1;
	         if(y_coord-1<0)
	         {
	             starty=y_coord;
	             
	         }
	         else starty=y_coord-1;
	         if(x_coord+1>no_rows-1)
	         {
	             endx=x_coord;
	         }
	         else endx=x_coord+1;
	         if(y_coord+1>no_cols-1)
	         {
	             endy=y_coord;
	         }
	         else endy=y_coord+1;
	         
	         for(int i= startx;i<=endx;i++)
	         {
	             for(int j = starty; j<= endy;j++)
	             {
	                 
	                 if(mine_grid[i][j]==mine_found|| mine_grid[i][j]==flag_mine)
	                 {
	                     count++;
	                 }
	             }
	         }
	         if(count==0)
	         {
	             mine_grid[x_coord][y_coord]=100;
	             for(int i=startx;i<=endx;i++)
	             {
	                 for(int j=starty; j<=endy;j++)
	                 {
	                   	 expansion_mine(i,j);
	                 }
	             }
	         }
	         else
	         {
	             mine_grid[x_coord][y_coord]=count;
	         }
	     }
	 }

	 void game_won()  //function for game win
	 {
	     int empty_grid=0, win_condition=0;
	     if(no_rows==0 && no_cols==0)
		 {
			 no_rows=4;
			 no_cols=4;
			// mines = no_rows*no_cols*0.2;
		 }
	    
	     for(int i=0;i<no_rows;i++)
	     {
	        for(int j=0;j<no_cols;j++)
	         {
	             if(mine_grid[i][j]==0 || mine_grid[i][j]==flag_img)
	             {   
	            	 empty_grid++;
	             }
	         }
	     }
	     if(empty_grid==0)
	     {
	         win_condition=1;
	     }
	     if (win_condition==1)
	     {
	    	 pr = PreferenceManager.getDefaultSharedPreferences(getContext());
		     if(MainActivity.seconds < pr.getInt("best_time", 1000))
		     {
		    	 Editor edit= pr.edit();
		    	 edit.putInt("best_time", MainActivity.seconds);
		    	 edit.commit();
                 AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
		    	 alert.setMessage("Congratulations!! Best Score!");
		    	 alert.setCancelable(false);
		    	 alert.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
				
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						generate_mines(50);
						invalidate();
					}
				});
		    	 AlertDialog alert1 = alert.create();
		    	 alert1.show();
			     }
		     else
		     {
	    	 AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
	    	 alert.setMessage("Congratulations!! Game won...");
	    	 alert.setCancelable(false);
	    	 alert.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
			
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					generate_mines(50);
					invalidate();
				}
			});
	    	 AlertDialog alert1 = alert.create();
	    	 alert1.show();
		     }
	     
	    }
     }

	 
	 void generate_mines(double mines)   //function for randomly generating mines at the beginning of new game
	 {
         Music.play(this.getContext(),R.raw.main);
		 int no_rows=MainActivity.row;
		 int no_cols=MainActivity.column;
		 mine_grid= new int[16][16];
		 mine_present=false;
		 total_mines=0;
		 MainActivity.counter_start=true;
		 MainActivity.seconds=0;
		 Random rand = new Random();
		 if(no_rows==0 && no_cols==0)
		 {
			 no_rows=4;
			 no_cols=4;
			 mines = no_rows*no_cols*0.2;
		 }
		 for(int i=0;i<no_rows;i++)
		 {
			 for(int j=0;j<no_cols;j++)
			 {
				 mine_grid[i][j]=0;
			 }
		 }
		 int x,y;
		
		 mines = no_rows*no_cols*0.2;
		 for(int i=0;i<mines;i++)
		 {
			 do
			 {
				 x=rand.nextInt(15)%no_rows;
				 y=rand.nextInt(15)%no_cols;
		     }
			 while(mine_grid[x][y]==mine_found);
			 mine_grid[x][y]=mine_found;
		 }
		 for(int i=0;i<no_rows;i++)
		 {
			 for(int j=0;j<no_cols;j++)
			 {
				 if(mine_grid[i][j]==mine_found)
				 total_mines++;
			 }
		 }
	 }
	
	
	 protected void onDraw(Canvas canvas) {
		 int i,j;
		 if(no_rows==0 && no_cols==0)
		 {
			 no_rows=4;
			 no_cols=4;
		 }
	      // Draw the background...
		 Paint background = new Paint();
	      background.setColor(getResources().getColor(
	            R.color.puzzle_background));
	      canvas.drawRect(0, 0, getWidth(), getHeight(), background);
 
	      // Draw the board...

         // Define colors for the grid lines
         Paint dark = new Paint();
         dark.setColor(getResources().getColor(R.color.puzzle_dark));
         Paint hilite = new Paint();
         hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
         Paint light = new Paint();
         light.setColor(getResources().getColor(R.color.puzzle_light));
         // Draw the minor grid lines
         for (i = 0; i < no_rows; i++) {

             canvas.drawLine(0, i * height, getWidth(), i * height, dark);
         }
         for (i = 0; i < no_cols; i++) {
             canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
         }

         Paint foreground = new Paint();
         foreground.setColor(getResources().getColor(
                 R.color.puzzle_foreground));
         foreground.setStyle(Style.FILL);
         foreground.setTextSize(height * 0.75f);
         foreground.setTextScaleX(width / height);
         foreground.setTextAlign(Paint.Align.CENTER);
         game_won();
         // Draw the number in the center of the tile
         FontMetrics fm = foreground.getFontMetrics();
         // Centering in X: use alignment (and X at midpoint)
         float x = width / 2;
         // Centering in Y: measure ascent/descent first
         float y = height / 2 - (fm.ascent + fm.descent) / 2;
         if(mine_present==true)
	         {
	         for(i=0;i<no_rows;i++)
	        	{
	        		for(j=0;j<no_cols;j++)
	        		{
	        
	        			if(mine_grid[i][j]==mine_found || mine_grid[i][j]==flag_mine)  //display image of mine when mine is struck
	        			{
	        				Bitmap bit = BitmapFactory.decodeResource(getResources(), R.drawable.mine);
	        				 bit= Bitmap.createScaledBitmap(bit, (int)width, (int)height, false);	 
			        		 canvas.drawBitmap(bit,(i*width),(j*height),null); 	
			        		
	        			}
	        		}
	        	}
                 Music.play(this.getContext(),R.raw.mine_blast);

                 AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                 alert.setMessage("Sorry, you lose!!");
	    	     alert.setCancelable(true);
               //  Music.stop(this.getContext());
             alert.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

                    generate_mines(50);
					invalidate();
				}
			});
	    	 AlertDialog alert1 = alert.create();
	    	 alert1.show();
            //     Music.play(this.getContext(),R.raw.main);

             }

         for(i=0;i<no_rows;i++)
	        	{
	        		for(j=0;j<no_cols;j++)
	        		{
	        			
	        			 if((mine_grid[i][j]==mine_found || mine_grid[i][j]==uncovered_cell)&& !mine_present) //dispalying image on the grid in the beginning
	        	            {
	        				 Paint p = new Paint();
                              p.setColor(Color.rgb(MainActivity.Red, MainActivity.Green, MainActivity.Blue));
                                if(MainActivity.Red==255 || MainActivity.Green==255 || MainActivity.Blue==255)
                                    p.setColor(Color.BLACK);
		        				canvas.drawRect(i*width+1,j*height+1,(i*width)+width-1,(j*height)+height-1, p);
	        	            }

                        if(mine_grid[i][j]==flag_img || mine_grid[i][j]==flag_mine )  //checking if the cell contains the value 999 or -4 which represents flag or flag with mine and display image of flag at that cell
                        {
                            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.flag);
                            b= Bitmap.createScaledBitmap(b, (int)width, (int)height, false);
                            canvas.drawBitmap(b,(i*width),(j*height),null);
                        }
	        			 if(mine_grid[i][j]!=flag_img && mine_grid[i][j]!=flag_mine && mine_grid[i][j]!=mine_found  && mine_grid[i][j]!=100 && mine_grid[i][j]!=uncovered_cell )  //checking if surrounding cell contains a mine and displaying the number of mines in the cell clicked
	    	            {
                            Paint p = new Paint();
                            canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);

                            switch(mine_grid[i][j])
                            {

                                case 1:
                                    foreground.setColor(Color.RED);
                                    canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);

                                    break;
                                case 2:
                                    foreground.setColor(Color.BLUE);
                                    canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);

                                    break;
                                case 3:

                                    foreground.setColor(Color.GRAY);
                                    canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);
                                    break;
                                case 4:
                                    foreground.setColor(Color.YELLOW);
                                    canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);
                                    break;

                                case 5:
                                    foreground.setColor(Color.GREEN);
                                    canvas.drawText(String.valueOf(mine_grid[i][j]), ((i * width) + x), ((j * height) + y), foreground);
                                    break;
                            }



                        }


	        		}
	        	}
	         }    	         
}
	 
	


