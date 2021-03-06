package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

import org.usfirst.frc.team3663.robot.RobotMap;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;


/**
 *
 */
public class DriveTrainSS extends Subsystem 
{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Victor leftMotor1,leftMotor2;
	Victor rightMotor1,rightMotor2;
	
	Encoder leftEncoder, rightEncoder;
	
	RobotDrive chassis;

	DriverStation ds = DriverStation.getInstance();
	
	double mag = 0.2;
	int encoderValL;
	int encoderValR;
	
	public DriveTrainSS()
	{
		leftMotor1 = new Victor(RobotMap.leftMotorPort1);
		leftMotor2 = new Victor(RobotMap.leftMotorPort2);
		rightMotor1 = new Victor(RobotMap.rightMotorPort1);
		rightMotor2 = new Victor(RobotMap.rightMotorPort2);
		
		leftEncoder = new Encoder(RobotMap.leftDriveEncoderPort1, RobotMap.leftDriveEncoderPort2);
		rightEncoder = new Encoder(RobotMap.rightDriveEncoderPort1, RobotMap.rightDriveEncoderPort2);
		
		chassis = new RobotDrive(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
	}
	
    public void initDefaultCommand() 
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new C_EncoderDrive());
    }
    
    public void encoderDrive()
    {
    //	chassis.drive(mag, 0);
    	leftMotor1.set(0.2);
    	leftMotor2.set(0.2);
    	encoderValL = leftEncoder.get();
    	encoderValR = rightEncoder.get();
    	SmartDashboard.putNumber("LeftEncoder: ", encoderValL);
    	SmartDashboard.putNumber("RightEncoder: ", encoderValR);
    }
}

