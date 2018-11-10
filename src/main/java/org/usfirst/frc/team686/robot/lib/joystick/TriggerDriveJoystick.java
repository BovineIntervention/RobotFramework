package org.usfirst.frc.team686.robot.lib.joystick;

import org.usfirst.frc.team686.robot.lib.joystick.JoystickControlsBase;
import org.usfirst.frc.team686.robot.lib.util.Util;
import org.usfirst.frc.team686.robot.Constants;
import org.usfirst.frc.team686.robot.command_status.DriveCommand;

/**
 * Joystick controls that use the trigger buttons for acceleration/deceleration.
 */
public class TriggerDriveJoystick extends JoystickControlsBase 
{
    private static JoystickControlsBase mInstance = new TriggerDriveJoystick();

    public static JoystickControlsBase getInstance() 
    {
        return mInstance;
    }

    
    public DriveCommand getDriveCommand()
    {
	    boolean squaredTrigger = false;
	    boolean squaredTurn    = false;
	    
    	double throttle = mStick.getRawAxis(Constants.kXboxRTriggerAxis) - mStick.getRawAxis(Constants.kXboxLTriggerAxis);
        double turn     = mStick.getX();
        boolean isQuickTurn = mStick.getRawButton(Constants.kQuickTurnButton);
    		    
	    double moveValue   = Util.limit(throttle, 1.0);
	    double rotateValue = Util.limit(turn,     1.0);
	    double lMotorSpeed, rMotorSpeed;
	    
	    if (squaredTrigger) {
	      // square the inputs (while preserving the sign) to increase fine control
	      // while permitting full power
	      if (moveValue >= 0.0) {
	        moveValue = (moveValue * moveValue);
	      } else {
	        moveValue = -(moveValue * moveValue);
	      }
	    }
	    if (squaredTurn) {
	      if (rotateValue >= 0.0) {
	        rotateValue = (rotateValue * rotateValue);
	      } else {
	        rotateValue = -(rotateValue * rotateValue);
	      }
	    }
	
	    // normal trigger drive turning
	    if (rotateValue > 0.0) {
	      lMotorSpeed = moveValue;
	      rMotorSpeed = moveValue-rotateValue*moveValue*2;
	    } else {
	      lMotorSpeed = moveValue+rotateValue*moveValue*2;
	      rMotorSpeed = moveValue;
	    }
	    
	    // override normal trigger turning when quick turn button is pressed
	    if (isQuickTurn)
	    {
		    lMotorSpeed = +rotateValue;
		    rMotorSpeed = -rotateValue;
	    }
	    
	    DriveCommand signal = new DriveCommand(lMotorSpeed, rMotorSpeed);
	   	    
	    return signal;
    }
}
