package org.usfirst.frc.team686.robot.lib.sensors;

import org.usfirst.frc.team686.robot.Constants;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class NavX extends GyroBase
{
	private static NavX instance = new NavX();
	public static NavX getInstance() { return instance; }
	
	 AHRS ahrs;
	
    // The SPI port the NavX is connected to
    // (see https://www.pdocs.kauailabs.com/navx-mxp/guidance/selecting-an-interface/)
    public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;						// the SPI port has low latency (<0.1 ms)
	public static byte NAVX_UPDATE_RATE = (byte) (1.0 / Constants.kLoopDt);		// the SPI port supports update rates from 4-200 Hz
	 
	 
    // constructors
    public NavX() 
    {
    	ahrs = new AHRS(NAVX_PORT, NAVX_UPDATE_RATE);
    }
	
	/**
	 * Returns heading for the GyroBase class.
	 *
	 */
	public double getHeadingDeg() {
		return -ahrs.getAngle();	// sign correction so that heading increases as robot turns to the left	 
	}
	
	public double getWorldLinearAccelerationX(){
		return ahrs.getWorldLinearAccelX();
	}
	
	public double getWorldLinearAccelerationY(){
		return ahrs.getWorldLinearAccelY();
	}
}
