package edu.ncc.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

public class GraphicsView extends SurfaceView implements SurfaceHolder.Callback
{
	
	
	  
	  
	  
	  private int x; // X Coordinates
	  private int y; // Y Coordinates
	  private Paint drawColor;
	  private int[] Center;
	  private GraphicsThread thread;
	private int width;
	private int height;
	private Paint p;
	

	public GraphicsView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		getHolder().addCallback(this);
		drawColor = new Paint();
		drawColor.setColor(Color.BLUE);
		x = 0;
		y = 0;
		width = 0;
		height = 0;
		p = new Paint();
		p.setColor(Color.WHITE);
		
	}
	public void drawCanvas(Canvas c)
	{
		
		c.drawRect(0, 0, width, height, p);
		int col = drawColor.getColor();
		col+= 512;
		//Toast.makeText(getContext(), col, Toast.LENGTH_SHORT).show();
		Log.d("Current Color", Integer.toHexString(col));
		
		drawColor.setColor(col);
		
		c.drawCircle(x, y, 75, drawColor);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread = new GraphicsThread(holder);
		thread.start();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
		this.width = width;
		this.height = height;
		x = width/2;
		y = height/2;
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		 while (retry)
         {
                 try
                 {
                         thread.join();
                         retry = false;
                 }
                 catch (InterruptedException e)
                 {
                         
                 }
         }
		
	}
	public int[] getCenter(Canvas c)
	{
		int[] Coor = new int[2];
		Coor[0] = c.getWidth() /2;
		Coor[1] = c.getHeight() /2;
		return Coor;
	}
	public void update(MotionEvent e)
    {
            x = (int)(e.getX());
            y = (int)(e.getY());
            
    }
	public void stopGame()
	{
		if(thread != null)
		{
			thread.setRunning(false);
			
		}
	}
	
	private class GraphicsThread extends Thread
    {
        private SurfaceHolder surfaceHolder; 
        private boolean threadIsRunning = true; 

        public GraphicsThread(SurfaceHolder holder)
        {
            surfaceHolder = holder;
        }
        public void setRunning(boolean runState)
        {
        	threadIsRunning = runState;
        }

        @Override
        public void run()
        {
            Canvas canvas = null; 
            while (threadIsRunning)
            {
                try
                {
                    canvas = surfaceHolder.lockCanvas(null);               
                    // lock the surfaceHolder for drawing
                    synchronized(surfaceHolder)
                    {
                        drawCanvas(canvas); // draw 
                    } // end synchronized block
                } // end try
                finally
                {
                    if (canvas != null) 
                        surfaceHolder.unlockCanvasAndPost(canvas);
                } // end finally
            } // end while
        } // end method run
    } // end nested class
}
	    
	  
	  
	/*
	 public class GraphicsView extends View
{ 
	private int x; // X Coordinates
	private int y; // Y Coordinates
	private Paint drawColor;
	private int[] Center;

	public GraphicsView(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		drawColor = new Paint();
		x = 0;
		y = 0;
		
	}
	public void onDraw(Canvas c)
	{
		Path path = new Path();
		drawColor.setColor(Color.RED);
		Center = getCenter(c);
		int s = 100;
		drawTriangle(s, path, c);
		
		
		
	}
	public int[] getCenter(Canvas c)
	{
		int[] Coor = new int[2];
		Coor[0] = c.getWidth() /2;
		Coor[1] = c.getHeight() /2;
		return Coor;
	}
	

    public void update(MotionEvent e)
    {
            x = (int)(e.getX());
            y = (int)(e.getY());
            invalidate();
    }
    public void drawTriangle(int Size,Path path, Canvas c)
    {
    	
    	path.moveTo(x, y);
    	path.lineTo((x-40)* Size, (y+60) * Size);
    	path.lineTo((x+40)* Size, (y+60) * Size);
    	path.lineTo(x, y);
    	
    	c.drawPath(path, new Paint(Color.BLACK));
    }


} */
