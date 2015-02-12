package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Button;
import org.usfirst.frc.team3663.robot.commands.C_EncoderDriveF;
import org.usfirst.frc.team3663.robot.commands.C_EncoderDriveB;
import org.usfirst.frc.team3663.robot.commands.C_EncoderTurnL;
import org.usfirst.frc.team3663.robot.commands.C_EncoderTurnR;
import org.usfirst.frc.team3663.robot.commands.C_StopEncoderDrive;
import org.usfirst.frc.team3663.robot.commands.C_EncoderDrive;
import org.usfirst.frc.team3663.robot.commands.C_EncoderDrive2;
import org.usfirst.frc.team3663.robot.commands.C_EncoderDriveKing;
//import org.usfirst.frc.team3663.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI 
{
	public Joystick driveStick = new Joystick(0);
	
	Button button1 = new JoystickButton(driveStick,1);
	Button button2 = new JoystickButton(driveStick,2);
	Button button3 = new JoystickButton(driveStick,3);
	Button button4 = new JoystickButton(driveStick,4);
	Button button5 = new JoystickButton(driveStick,5);
	Button button6 = new JoystickButton(driveStick,6);
	Button button7 = new JoystickButton(driveStick,7);
	Button button8 = new JoystickButton(driveStick,8);
	Button button12 = new JoystickButton(driveStick,12);
	
	public OI()
	{
		button1.whileHeld(new C_EncoderDriveKing(30));
		//button1.whenPressed(new C_EncoderDriveF(100, 0.5));
		button2.whenPressed(new C_EncoderDriveB(15));
		button3.whenPressed(new C_EncoderTurnL(35,180));
		button4.whenPressed(new C_EncoderTurnR(35,180));
		button5.whenPressed(new C_EncoderTurnL(25,90));
		button6.whenPressed(new C_EncoderTurnR(25,90));
		button7.whenPressed(new C_StopEncoderDrive());
		button8.whenPressed(new C_EncoderDrive());
		button12.whenPressed(new C_EncoderDrive2(1000,0,true));
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

