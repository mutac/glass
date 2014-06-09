package cx.ath.there.android.glass.livevideo;
import cx.ath.there.android.glass.util.*;
import android.app.Activity;
import android.hardware.Camera;
import android.media.CameraProfile;
import android.os.Bundle;
import android.util.Log;
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
	
	private Camera mCamera;
	private boolean mIsCameraConfigured = false;
	private boolean mIsVideoPlaying = false;
	private TemperatureNotifier mTemperatureNotifier = new TemperatureNotifier();
	private TextView mTemperatureText;
	private SurfaceView mVideo;
	private SurfaceHolder mVideoHolder;
	
    private SurfaceHolder.Callback mVideoSurfaceCallback = new SurfaceHolder.Callback() {
        public void surfaceCreated(SurfaceHolder holder) 
        {
        	// nothing
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
        {
        	initVideo(width, height);
        	startVideo();
        }

        public void surfaceDestroyed(SurfaceHolder holder) 
        {
            // nothing
        }
    };
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        mVideo = (SurfaceView)findViewById(R.id.video);
        mVideoHolder = mVideo.getHolder();
        mVideoHolder.addCallback(mVideoSurfaceCallback);
        
        mTemperatureText = (TextView)findViewById(R.id.temperature);
        mTemperatureNotifier.setTemperatureChangedListener(this);
    }

    @Override
    public void onResume() 
    {
        super.onResume();
        
        mCamera = Camera.open();
    }

    @Override
    public void onPause() 
    {
    	stopVideo();
    	
    	mCamera.release();
    	mCamera = null;
    	
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
	
	private boolean isVideoPlaying()
	{
		return isVideoConfigured() & mIsVideoPlaying;
	}
	
	private boolean isVideoReady()
	{
		return mCamera != null && mVideoHolder.getSurface() != null;
	}
	
	private boolean isVideoConfigured()
	{
		return mIsCameraConfigured && isVideoReady();
	}
	
	private void initVideo(int width, int height)
	{
		if (isVideoReady())
		{
			try
			{
				mCamera.setPreviewDisplay(mVideoHolder);
				
			}
			catch (Throwable t)
			{
				Log.e(TAG, "Exception in initVideo: ", t);
			}
			
			if (!mIsCameraConfigured)
			{
				Camera.Parameters cameraParams = mCamera.getParameters();
				cameraParams.setPreviewSize(width, height);
				
				mIsCameraConfigured = true;	
			}
		}
	}
	
	private void startVideo()
	{
		if (isVideoConfigured())
		{
			mCamera.startPreview();
			mIsVideoPlaying = true;
		}
	}
	
	private void stopVideo()
	{
		if (isVideoPlaying())
		{
			mCamera.stopPreview();
			mIsVideoPlaying = false;
		}
	}
}
