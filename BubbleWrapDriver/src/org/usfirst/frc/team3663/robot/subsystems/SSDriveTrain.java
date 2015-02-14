package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 */
public class SSDriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon driveMotorL1, driveMotorL2, driveMotorR1, driveMotorR2;
	Encoder leftEncoder, rightEncoder;
	RobotDrive chassis;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new C_ArcadeDrive());
    }
    
    public SSDriveTrain()
    {
    	driveMotorL1 = new CANTalon(10);
    	driveMotorL2 = new CANTalon(11);
    	driveMotorR1 = new CANTalon(20);
    	driveMotorR2 = new CANTalon(21);
    	
    	chassis = new RobotDrive(driveMotorL1, driveMotorL2, driveMotorR1, driveMotorR2);
    	
    	System.out.println("SSDriveTrain created");
    }
    
    public void arcadeDrive(Joystick driveStick)
    {
    	chassis.arcadeDrive(driveStick);
    }
}

