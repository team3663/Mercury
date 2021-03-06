package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderDriveF extends Command {

	int i = 0;
	
	int Inches;
	boolean finished = false;
	double speed, topSpeed;
	int currTicksL, currTicksR, ticksL, ticksR, changeInTicksL, changeInTicksR,
		lastTicksL, lastTicksR, desiredTicks, startTicksL, startTicksR, totalTickChangeL,
		totalTickChangeR;
	double currTime, lastTime, timeDiff;
	double ticksPerInch = 780/23.561944905;
	
    public C_EncoderDriveF(int inches, double TopSpeed) {
        // Use requires() here to declare subsystem dependencies
   //     requires(Robot.chassis);
        Inches = inches;
        topSpeed = TopSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startTicksL = Robot.chassis.getLeftEncoderTicks();
    	startTicksR = Robot.chassis.getRightEncoderTicks();
    	ticksL = (int)(Inches*ticksPerInch);
    	ticksR = (int)(Inches*ticksPerInch);
    	lastTicksL = 0;
    	lastTicksR = 0;
    	currTime = lastTime = Timer.getFPGATimestamp();
    	SmartDashboard.putNumber("ticksL: ", ticksL);
    	SmartDashboard.putNumber("ticksR: ", ticksR);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currTime = Timer.getFPGATimestamp();
    	currTicksL = Robot.chassis.getLeftEncoderTicks();
    	currTicksR = Robot.chassis.getRightEncoderTicks();
    	changeInTicksL = currTicksL - lastTicksL;
    	timeDiff = currTime - lastTime;
    	desiredTicks = (int)(2500*timeDiff);
    	if (speed < topSpeed && (startTicksL - currTicksL < ticksL/2 || currTicksR - startTicksR < ticksR/2))
    	{
    		if ((changeInTicksL > desiredTicks || changeInTicksR < desiredTicks))
	    	{
	    		speed+=0.025;
	    		Robot.chassis.setSpeed(speed);
	    	}
	    	else if ((changeInTicksL < desiredTicks || changeInTicksR > desiredTicks))
	    	{
	    		speed-=0.025;
	    		Robot.chassis.setSpeed(speed);
	    	}
    	}
    	SmartDashboard.putNumber("leftEncoder: ", Robot.chassis.getLeftEncoderTicks());
    	SmartDashboard.putNumber("rightEncoder: ", Robot.chassis.getRightEncoderTicks());
    	SmartDashboard.putNumber("num ", i++);
    	lastTime = currTime;
    	totalTickChangeL = startTicksL - currTicksL;
    	totalTickChangeR = currTicksR - startTicksR;
    	//for now will over shoot
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (currTicksL <= startTicksL - ticksL && currTicksR >= startTicksR + ticksR) 
        {
        	return true;
        }
        else return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.chassis.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
