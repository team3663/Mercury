package org.usfirst.frc.team3663.robot.subsystems;

import org.usfirst.frc.team3663.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 */
public class SSElevator extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon elevMotor1, elevMotor2;
	Talon elevInAndOut;
	DoubleSolenoid bikeBreak;
	public Encoder winchEncoder;
	public DigitalInput elevLimitSwitch;
	
	public boolean brakeOn;
	
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public SSElevator(){
    	bikeBreak = new DoubleSolenoid(2,3);
    	elevInAndOut = new Talon(4);
    	elevMotor1 = new CANTalon(15);
    	elevMotor2 = new CANTalon(25);
    	winchEncoder = new Encoder(1,2);
    	elevLimitSwitch = new DigitalInput(0);
    }
    public void motor1Set(double speed){
    	elevMotor1.set(speed);
    }
    public void motor2Set(double speed){
    	elevMotor2.set(speed);
    }
    public void moveInAndOut(double speed){
    	elevInAndOut.set(speed);
    }
    public void BikeBrakeTriggerOpen()
    {
    	bikeBreak.set(DoubleSolenoid.Value.kForward);
    	brakeOn = true;
    	SmartDashboard.putBoolean("brakeOn: ", brakeOn);
    }
    public void BikeBrakeTriggerClose(){
    	bikeBreak.set(DoubleSolenoid.Value.kReverse);
    	brakeOn = false;
    }
    
    public void logValues()
    {
    	SmartDashboard.putNumber("winchEncoder: ", Robot.ssElevator.winchEncoder.get());
    	SmartDashboard.putBoolean("elevLimitSwitch: ", Robot.ssElevator.elevLimitSwitch.get());
    }
}