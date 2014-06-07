package cx.ath.there.android.glass.livevideo;
import cx.ath.there.android.glass.util.*;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity 
	extends 
		Activity 
	implements 
		GestureDetector.OnGestureListener, 
		Camera.OnZoomChangeListener, 
		TemperatureNotifier.OnTemperatureChangedListener 
{
	public static String TAG = "LiveVideo";
	
	private TemperatureNotifier mTemperatureNotifier = new TemperatureNotifier();
	private TextView mTemperatureText;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        mTemperatureText = (TextView)findViewById(R.id.temperature);
        mTemperatureNotifier.setTemperatureChangedListener(this);
    }

    @Override
    public void onResume() 
    {
        super.onResume();
    }

    @Override
    public void onPause() 
    {
        super.onPause();
    }
    
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) 
    {
        return false;
    }

	@Override
	public boolean onDown(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{		
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		//Log.d(TAG, "distanceX: " + distanceX + ", distanceY: " + distanceY);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onZoomChange(int zoomValue, boolean stopped, Camera camera) 
	{
	}
	
	@Override
	public void onTemperatureChanged(Temperature temperature)
	{
		mTemperatureText.setText("TEMP: " + temperature.toString());
	}
}
