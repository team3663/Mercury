package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team3663.robot.commands.C_Test;
import org.usfirst.frc.team3663.robot.commands.ExampleCommand;
import org.usfirst.frc.team3663.robot.commands.C_TestSensors;
import org.usfirst.frc.team3663.robot.commands.C_MotorDriveTest;
import org.usfirst.frc.team3663.robot.commands.C_IncrementMotorSpeed;
import org.usfirst.frc.team3663.robot.commands.C_DecrementMotorSpeed;
import org.usfirst.frc.team3663.robot.commands.C_IncrementTestMotor;
import org.usfirst.frc.team3663.robot.commands.C_DecrementTestMotor;
import org.usfirst.frc.team3663.robot.commands.C_ReverseMotorSpeed;
import org.usfirst.frc.team3663.robot.commands.C_ToggleBrake;

public class OI {
	
	public Joystick driveStick = new Joystick(0);
	
	public JoystickButton testSensors;
	public JoystickButton motorDriveTest;
	public JoystickButton incrementSpeed;
	public JoystickButton decrementSpeed;
	public JoystickButton incrementTestMotor;
	public JoystickButton decrementTestMotor;
	public JoystickButton reverseMotorSpeed;
	public JoystickButton toggleBrake;
	
	public OI(){
	//	testSensors = new JoystickButton(driveStick, 1);
		//testSensors.whileHeld(new C_TestSensors());
		
		motorDriveTest = new JoystickButton(driveStick, 1);
		motorDriveTest.whileHeld(new C_MotorDriveTest());
		
		incrementSpeed = new JoystickButton(driveStick, 5);
		incrementSpeed.whenPressed(new C_IncrementMotorSpeed());
		
		decrementSpeed = new JoystickButton(driveStick, 3);
		decrementSpeed.whenPressed(new C_DecrementMotorSpeed());
		
		incrementTestMotor = new JoystickButton(driveStick, 6);
		incrementTestMotor.whenPressed(new C_IncrementTestMotor());
		
		decrementTestMotor = new JoystickButton(driveStick, 4);
		decrementTestMotor.whenPressed(new C_DecrementTestMotor());
		
		reverseMotorSpeed = new JoystickButton(driveStick, 2);
		reverseMotorSpeed.whenPressed(new C_ReverseMotorSpeed());
		
		toggleBrake = new JoystickButton(driveStick, 12);
		toggleBrake.whenPressed(new C_ToggleBrake());
	}
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

