package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderDrive2 extends Command {

	int changeTicksL, changeTicksR;
	int desiredTicksL;
	int desiredTicksR;
	boolean finished;
	
    public C_EncoderDrive2(int deltaTicksL, int deltaTicksR, boolean stopAtEnd) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	changeTicksL = deltaTicksL;
    	changeTicksR = deltaTicksR;
    }
    
    protected void initialize() {
    	Robot.chassis.resetEncoders();
    	desiredTicksL = Robot.chassis.getLeftEncoderTicks() + changeTicksL;
    	desiredTicksR = Robot.chassis.getRightEncoderTicks() + changeTicksR;
    	finished = false;
    }

    protected void execute() {
    	finished = Robot.chassis.encoderDrive(desiredTicksL, desiredTicksR, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (finished)
		{
    		return true;
    	}
        else 
        {
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
