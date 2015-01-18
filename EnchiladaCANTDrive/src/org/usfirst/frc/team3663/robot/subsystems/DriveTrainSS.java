package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import org.usfirst.frc.team3663.robot.RobotMap;
import org.usfirst.frc.team3663.robot.Robot;

/**
 *
 */
public class DriveTrainSS extends Subsystem 
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2;
	Encoder leftEncoder, rightEncoder;
	RobotDrive driveTrain;
	
	int encoderValL;
	int encoderValR;
	double twist;
	double mag;
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public DriveTrainSS()
    {
    	driveTalonL1 = new CANTalon(RobotMap.CANTalonNumL1);
    	driveTalonL2 = new CANTalon(RobotMap.CANTalonNumL2);
    	driveTalonR1 = new CANTalon(RobotMap.CANTalonNumR1);
    	driveTalonR2 = new CANTalon(RobotMap.CANTalonNumR2);
    	
    	driveTrain = new RobotDrive(driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2);

    	leftEncoder = new Encoder (RobotMap.leftDriveEncoderPort1, RobotMap.leftDriveEncoderPort2);
    }
    
    public void arcadeDrive()
    {
    	mag = Robot.oi.driveStick.getY();
    	twist = Robot.oi.driveStick.getTwist();
    	driveTrain.drive(mag, twist);
    }
    
    public void encoderDrive()
    {
    	mag = Robot.oi.driveStick.getY();
    	driveTalonL1.set(mag);
    	driveTalonL2.set(mag);
    	encoderValL = leftEncoder.get();
    	SmartDashboard.putNumber("leftEncoder: ", encoderValL);
    }
}
