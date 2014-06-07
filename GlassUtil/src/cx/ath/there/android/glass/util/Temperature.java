package cx.ath.there.android.glass.util;
import java.io.*;

public class Temperature {	
	public static double UNKNOWN_TEMPERATURE = -1.0;
	
	private static String TEMPERATURE_SENSOR_SYSFS_PATH = 
			"/sys/devices/platform/notle_pcb_sensor.0/temperature";

	private double mTemperature = UNKNOWN_TEMPERATURE;
	private String mTemperatureStr = makeString(UNKNOWN_TEMPERATURE);
	
	public Temperature()
	{
		readTemperature();
	}
	
	public double readTemperature()
	{
		double temperature = UNKNOWN_TEMPERATURE;
		
		try
		{
			InputStream tempSensor = 
					new FileInputStream(TEMPERATURE_SENSOR_SYSFS_PATH);
			
			String rawTempReading = (new BufferedReader(new InputStreamReader(tempSensor))).readLine();
			tempSensor.close();
			
			if (rawTempReading.length() < 3)
			{
				throw new Exception("Invalid temperature format");
			}
			
			String formattedTempReading = 
					rawTempReading.substring(0, rawTempReading.length() - 3) +
					"." +
					rawTempReading.substring(rawTempReading.length() - 3, rawTempReading.length());
			
			temperature = Double.parseDouble(formattedTempReading);
		}
		catch (Exception ex)
		{
			temperature = UNKNOWN_TEMPERATURE;
		}
		
		if (temperature != mTemperature)
		{
			mTemperature = temperature;
			mTemperatureStr = makeString(mTemperature);
		}
		
		return mTemperature;
	}
	
	public double getTemperature()
	{
		return mTemperature;
	}
	
	public String toString()
	{
		return mTemperatureStr;
	}
	
	public String units()
	{
		return "c";
	}
	
	private String makeString(double temperature)
	{
		return Double.toString(temperature) + units();
	}
}
