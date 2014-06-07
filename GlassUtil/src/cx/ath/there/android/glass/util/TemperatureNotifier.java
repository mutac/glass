package cx.ath.there.android.glass.util;
import android.os.Handler;

public class TemperatureNotifier {
	public interface OnTemperatureChangedListener
	{
		void onTemperatureChanged(Temperature temperature);
	}
	
	private double mLastTemperature = Temperature.UNKNOWN_TEMPERATURE;
	private Temperature mTemperature = new Temperature();
	
	private int mUpdatePeriodMs = 1000;
	private Handler mHandler = new Handler();
	private Runnable mPeriodicUpdate = new Runnable() {
		public void run() {
			double newTemperature = mTemperature.readTemperature();
			if (newTemperature != mLastTemperature)
			{
				onTemperatureChanged();
			}
			
			mHandler.postDelayed(mPeriodicUpdate, mUpdatePeriodMs);
		}
	};

	private OnTemperatureChangedListener mTemperatureChangedListener;
	
	public void finalize()
	{
		mHandler.removeCallbacks(mPeriodicUpdate);
	}
	
	public void setUpdatePeriod(int updatePeriodMs)
	{
		mUpdatePeriodMs = updatePeriodMs;
	}
	
	public void setTemperatureChangedListener(OnTemperatureChangedListener listener)
	{
		mTemperatureChangedListener = listener;
		mHandler.postDelayed(mPeriodicUpdate, mUpdatePeriodMs);
	}
	
	private void onTemperatureChanged()
	{
		if (mTemperatureChangedListener != null)
		{
			mTemperatureChangedListener.onTemperatureChanged(mTemperature);
		}
	}
}
