package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SSLift extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	CANTalon liftMotor1, liftMotor2;
	Talon liftInAndOut;
	Solenoid bikeBreak;
    public void initDefaultCommand() {
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public SSLift(){
    	bikeBreak = new Solenoid(0,1);
    	liftInAndOut = new Talon(4);
    	liftMotor1 = new CANTalon(15);
    	liftMotor2 = new CANTalon(25);
    }
    public void motor1Set(double speed){
    	liftMotor1.set(speed);
    }
    public void motor2Set(double speed){
    	liftMotor2.set(speed);
    }
    public void moveInAndOut(double speed){
    	liftInAndOut.set(speed);
    }
    public void BikeBreakTrigger(boolean open){
    	bikeBreak.set(open);
    }
    
}