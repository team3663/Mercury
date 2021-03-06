package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderDrive extends Command {

	int num;
	double startTime;
	boolean finished;
	
    public C_EncoderDrive() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.resetEncoders();
    	startTime = Timer.getFPGATimestamp();
    	Robot.chassis.mag = 0;
    	Robot.chassis.reachedPeakSpeed = false;
    	num = 0;
    	Robot.chassis.decrementVal = SmartDashboard.getNumber("decrementVal: ");
    	Robot.chassis.topSpeed = SmartDashboard.getNumber("topSpeed: ");
    	SmartDashboard.putBoolean("CommandRunning: ", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = Robot.chassis.encoderDrive();
    	
    	SmartDashboard.putNumber("number: ", num++);
    	SmartDashboard.putNumber("timeElapsed: ", Timer.getFPGATimestamp() - startTime);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (finished)
		{
        	SmartDashboard.putBoolean("CommandRunning: ", false);
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
