package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_EncoderDriveTest extends Command {

	int i;
	boolean finished;
	
    public C_EncoderDriveTest(int mode) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.chassis);
        i = mode;
    }

    protected void initialize() {
    	//Robot.chassis.encoderDriveTest(1.0);
    }

    protected void execute() {
    	switch(i)
    	{
		case 1:
			finished = Robot.chassis.encoderDriveF(15);
			break;
		case 2:
			finished = Robot.chassis.encoderDriveB(15);
			break;
		case 3:
			finished = Robot.chassis.encoderTurn(true,15,180);
			break;
		case 4:
			finished = Robot.chassis.encoderTurn(false,15,180);
			break;
		case 5:
			finished = Robot.chassis.encoderTurn(true,25,90);
			break;
		case 6:
			finished = Robot.chassis.encoderTurn(false,25,90);
			break;
		case 12:
			finished = Robot.chassis.encoderDriveTest(1.0);
			break;
    	}
    }

    protected boolean isFinished() {
        if (finished) return true;
        else return false;
    }

    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
