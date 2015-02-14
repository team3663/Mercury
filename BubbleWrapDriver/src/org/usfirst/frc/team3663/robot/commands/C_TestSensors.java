package org.usfirst.frc.team3663.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class C_TestSensors extends Command {

    public C_TestSensors() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.ssDriveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("leftDriveEncoder: ", Robot.ssDriveTrain.leftEncoder.get());
    	SmartDashboard.putNumber("rightDriveEncoder: ", Robot.ssDriveTrain.rightEncoder.get());
    	SmartDashboard.putNumber("winchEncoder: ", Robot.ssElevator.winchEncoder.get());
    	SmartDashboard.putBoolean("elevLimitSwitch: ", Robot.ssElevator.elevLimitSwitch.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}