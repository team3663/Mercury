package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderDriveKing extends Command {

	boolean finished;
	int ticks;
	
    public C_EncoderDriveKing(double Inches) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        ticks = (int)(Inches*780/23.561944905);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.chassis.initEncoderDriveKing(1.0, .0125);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = Robot.chassis.encoderDriveKing(ticks);
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
