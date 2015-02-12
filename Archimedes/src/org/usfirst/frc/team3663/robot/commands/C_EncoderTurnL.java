package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderTurnL extends Command {

	int Rad;
	double Degr;
	boolean finished;
	
    public C_EncoderTurnL(int radius, double degrees) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        Rad = radius;
        Degr = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	finished = Robot.chassis.encoderTurn(true, Rad, Degr);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (finished)return true;
        else return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
