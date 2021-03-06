package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.CANTalon;
//import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;

import org.usfirst.frc.team3663.robot.RobotMap;
import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;

public class DriveTrainSS extends Subsystem 
{
	CANTalon driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2;
	CANTalon otherTalon;
	Encoder leftEncoder, rightEncoder;
	RobotDrive driveTrain;

	boolean brakeMode;
	
	boolean encoderDriveFinished;
	public double topSpeed = 0.5;
	public boolean reachedPeakSpeed;
	public double mag;
	public double decrementVal = 0.00625;
	int currTicks, lastChange;
	int lastTicks = 0;
	double currTime, lastTimeChange;
	double lastTime = 0;
	double startTime, endTime;
	
	int ticksPerRevL = 780;
	int ticksPerRevR = 233;
	int currTicksL, currTicksR, ticks, ticksL, ticksR;
	double x;
	int i = 0;
	double distBetweenCenterAndWheelInches = 10.0;
	double tickPerInch360 = 360/23.561944905;
	//turn variables---
	int ticksOutside, ticksInside;
	double speedVar;
	double rateAtFullPower;//ticks per second
	double magConvert;
	//-----------------
	
    public void initDefaultCommand() 
    {
        setDefaultCommand(new C_ArcadeDrive());
    }
    
    public DriveTrainSS()
    {
    	driveTalonL1 = new CANTalon(10);
    	driveTalonL2 = new CANTalon(12);
    	driveTalonR1 = new CANTalon(11);
    	driveTalonR2 = new CANTalon(13);
    	
    	driveTalonL1.enableBrakeMode(true);
    	driveTalonL2.enableBrakeMode(true);
    	driveTalonR1.enableBrakeMode(true);
    	driveTalonR2.enableBrakeMode(true);
    	brakeMode = true;
    	
  //  	otherTalon = new CANTalon(14);
    	
//    	driveTalonL1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
  //  	driveTalonR1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
    	
    	driveTrain = new RobotDrive(driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2);

    	leftEncoder = new Encoder(RobotMap.leftDriveEncoderPort1, RobotMap.leftDriveEncoderPort2);
    	rightEncoder = new Encoder(RobotMap.rightDriveEncoderPort1, RobotMap.rightDriveEncoderPort2);
    	
    	SmartDashboard.putNumber("decrementVal: ", 0.00625);
    	SmartDashboard.putNumber("topSpeed: ", 0.5);
    	SmartDashboard.putNumber("speedInput: ", 0);
    }
    
    public boolean driveConstant()
    {
    	mag = 0.5;
		driveTalonL1.set(-mag);
    	driveTalonL2.set(-mag);
    	driveTalonR1.set(mag);
    	driveTalonR2.set(mag);
    	return false;
    }
    
    public void arcadeDrive()
    {
    	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
    	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    	mag = Robot.oi.driveStick.getY();
    	x = Robot.oi.driveStick.getX();
    	//driveTrain.drive(mag, x);
    }
    
    public boolean encoderDrive()
    {
    	int jjj = 0;
    	if (jjj == 0)
    	{
    		return true;
    	}
    	currTicks = leftEncoder.get();
    	currTime = Timer.getFPGATimestamp();
    	if (lastTicks == 0)
    	{
    		lastTicks = currTicks;
    		lastChange = (currTicks-lastTicks)/1;
    	}
    	if (lastTime == 0)
    	{
    		lastTime = currTime;
    		lastTimeChange = 0;
    	}
    	//boolean braked = false;
    	encoderDriveFinished = false;
    /*	if (braked)
    	{
    		mag = 0;
    	}
    	else*/ if (!reachedPeakSpeed && mag < topSpeed)
    	{
    		mag+=0.0125;
    		if (mag >= topSpeed)
    		{
    			reachedPeakSpeed = true;
    		}
    	}
    	else if (reachedPeakSpeed && ((currTicks-lastTicks)/(currTime-lastTime) 
    			> (lastChange/lastTimeChange)-5 && (currTicks-lastTicks)/(currTime-lastTime) 
    			< (lastChange/lastTimeChange)+5) && mag > 0)
    	{
    		mag-=mag/7;
    		if (mag < 0.0015)
    		{
    			mag = 0;
    		}
    		//mag-=0.00625;//decrementVal;
    		//mag = decrementVal;
    		//mag = 0;
    	//	braked = true;
    	}
    	if (reachedPeakSpeed && (mag <= 0.05 && mag >= -0.05))
    	{
    		return true;
    	}
		driveTalonL1.set(-mag);
    	driveTalonL2.set(-mag);
    	driveTalonR1.set(mag);
    	driveTalonR2.set(mag);
    	//print .set, ticks,
    	SmartDashboard.putNumber("leftEncoderTicks: ", leftEncoder.get());
    	SmartDashboard.putNumber("rightEncoderTicks: ", rightEncoder.get());
    	SmartDashboard.putNumber("magSpeed: ", mag);
    	lastChange = currTicks-lastTicks;
    	lastTimeChange = currTime-lastTime;
    	lastTicks = currTicks;
    	return false;
    }
    
    public void toggleBrake()
    {
    	if (brakeMode)
    	{
        	driveTalonL1.enableBrakeMode(false);
        	driveTalonL2.enableBrakeMode(false);
        	driveTalonR1.enableBrakeMode(false);
        	driveTalonR2.enableBrakeMode(false);
        	brakeMode = false;
    	}
    	else
    	{
        	driveTalonL1.enableBrakeMode(true);
        	driveTalonL2.enableBrakeMode(true);
        	driveTalonR1.enableBrakeMode(true);
        	driveTalonR2.enableBrakeMode(true);
        	brakeMode = true;
    	}
    	SmartDashboard.putBoolean("brakeMode: ", brakeMode);
    }
    
    public void resetEncoders()
    {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public boolean encoderDriveTest(double magnitude)
    {
		driveTalonL1.set(-magnitude);
    	driveTalonL2.set(-magnitude);
    	driveTalonR1.set(magnitude);
    	driveTalonR2.set(magnitude);
    	
/*    	boolean coastL = true;
    	boolean coastR = true;
    	driveTalonL1.enableBrakeMode(false);
    	driveTalonL2.enableBrakeMode(false);
    	driveTalonR1.enableBrakeMode(false);
    	driveTalonR2.enableBrakeMode(false);
    	int lastTickR, lastTickL;
    	//mag 0.2 button 12
    	leftEncoder.reset();
    	rightEncoder.reset();
    	double endTime = Timer.getFPGATimestamp() + 1.0;
    	while (Timer.getFPGATimestamp() < endTime)
    	{
    		driveTalonL1.set(-magnitude);
        	driveTalonL2.set(-magnitude);
        	driveTalonR1.set(magnitude);
        	driveTalonR2.set(magnitude);
        	SmartDashboard.putNumber("leftEncoderDrive: ", leftEncoder.get());
        	SmartDashboard.putNumber("rightEncoderDrive: ", rightEncoder.get());
    	}
    	leftEncoder.reset();
    	lastTickL = leftEncoder.get();
    	rightEncoder.reset();
    	lastTickR = rightEncoder.get();
    	SmartDashboard.putNumber("leftEncoderBegin: ", leftEncoder.get());
    	SmartDashboard.putNumber("rightEncoderBegin: ", rightEncoder.get());
		double currTime = Timer.getFPGATimestamp();
    	driveTalonL1.set(0);
    	driveTalonL2.set(0);
    	driveTalonR1.set(0);
    	driveTalonR2.set(0);
    	while (coastR || coastL)
    	{
    		if (lastTickR >= rightEncoder.get())
    		{
    			coastR = false;
    		}
    		if (lastTickL <= leftEncoder.get())
    		{
    			coastL = false;
    		}
	    	SmartDashboard.putNumber("leftEncoderCoast: ", leftEncoder.get());
	    	lastTickL = leftEncoder.get();
	    	SmartDashboard.putNumber("timeElapsedCoastLLL: ", Timer.getFPGATimestamp() - currTime);
			SmartDashboard.putNumber("rightEncoderCoast: ", rightEncoder.get());
	    	lastTickR = rightEncoder.get();
	    	SmartDashboard.putNumber("timeElapsedCoastRRR: ", Timer.getFPGATimestamp() - currTime);
    	}----------------------------------*/
    	/*------------------------
    	int currTicks;
    	int startTicksL = currTicks = leftEncoder.get();
    	int startTicksR;
    	
    	while (currTicks < startTicksL + 360)
    	{
	    	driveTalonL1.set(0.5);
	    	driveTalonL2.set(0.5);
	    	currTicks = leftEncoder.get();
	    	currTime = Timer.getFPGATimestamp();
    	}
    	driveTrain.drive(0,0);
    	SmartDashboard.putNumber("time for 360 ticks Left: ", currTime - startTime);
    	
    	currTicks = rightEncoder.get();
    	startTicksR = currTicks;
    	startTime = Timer.getFPGATimestamp();
    	while (currTicks < startTicksR + 360)
    	{
    		driveTalonR1.set(0.5);
    		driveTalonR2.set(0.5);
    		currTicks = rightEncoder.get();
    		currTime = Timer.getFPGATimestamp();
    	}
    	driveTrain.drive(0,0);
    	SmartDashboard.putNumber("time for 360 ticks Right: ", currTime - startTime);
    	-----------------------*/
    	return true;
    }
    
    public boolean encoderDriveF(int inches)
    {
    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	//conversion algorithm to ticks
    	ticksL = (int)(inches*tickPerInch360*780/360);
    	ticksR = (int)(inches*tickPerInch360*233/360);
    	while (leftEncoder.get() > (currTicksL - ticksL) || rightEncoder.get() < (currTicksR + ticksR))//see if && or ||
    	{
    		driveTalonL1.set(-0.2);
        	driveTalonL2.set(-0.2);
        	driveTalonR1.set(0.2);
        	driveTalonR2.set(0.2);
    		//driveTrain.arcadeDrive(0.2,0);
        	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
        	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    	}
		driveTalonL1.set(0.0);
    	driveTalonL2.set(0.0);
    	driveTalonR1.set(0.0);
    	driveTalonR2.set(0.0);
    	
    	return true;
    }
    
    public boolean encoderDriveB(double inches)
    {

    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	//conversion algorithm to ticks
    	ticksL = (int)(inches*tickPerInch360*780/360);
    	ticksR = (int)(inches*tickPerInch360*233/360);
    	while (leftEncoder.get() < (currTicksL + ticksL) || rightEncoder.get() > (currTicksR - ticksR))//see if && or ||
    	{
    		driveTalonL1.set(0.2);
        	driveTalonL2.set(0.2);
        	driveTalonR1.set(-0.2);
        	driveTalonR2.set(-0.2);
    		//driveTrain.arcadeDrive(-0.2, 0);
        	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
        	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    	}
		driveTalonL1.set(0.0);
    	driveTalonL2.set(0.0);
    	driveTalonR1.set(0.0);
    	driveTalonR2.set(0.0);
    	
    	return true;
    }
    
    public boolean encoderTurn(boolean turningL, int radius, float degreeArc)
    {
    	speedVar = -0.5;
    	
    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	
    	//algorithms
    	ticksOutside = (int)(tickPerInch360*(((radius + distBetweenCenterAndWheelInches)*2*3.141592654)*(degreeArc/360.0)));
    	ticksInside = (int)(tickPerInch360*(((radius - distBetweenCenterAndWheelInches)*2*3.141592654)*(degreeArc/360.0)));
    	if (turningL) rateAtFullPower = 360/0.156;//0.2: /0.801;//1.0: /0.098;
    	else rateAtFullPower = 360/0.674;//0.2: /2.292;//1.0: /0.362;
    	magConvert = rateAtFullPower;
    	speedVar = speedVar + ((ticksInside*rateAtFullPower/ticksOutside)/magConvert);//speedVar + ticksInside/ticksOutside
    	//end of algorithms
    	
    	SmartDashboard.putNumber("ticksInside", ticksInside);
		SmartDashboard.putNumber("ticksOutside", ticksOutside);
		
    	if (turningL)
    	{
    		ticksOutside = (ticksOutside*233)/360;
    		ticksInside = ticksInside*780/360;
    		
    		while (rightEncoder.get() < (currTicksR + ticksOutside))
    		{
	    		driveTalonL1.set(-(speedVar));
	        	driveTalonL2.set(-(speedVar));
	        	driveTalonR1.set(0.5);
	        	driveTalonR2.set(0.5);
	        	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
	        	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    		}
    	}
    	else
    	{
    		ticksOutside = ticksOutside*780/360;
    		ticksInside = ticksInside*233/360;
    		while (leftEncoder.get() > currTicksL - ticksOutside)
    		{
	    		driveTalonL1.set(-(0.5));
	        	driveTalonL2.set(-(0.5));
	        	driveTalonR1.set(speedVar);
	        	driveTalonR2.set(speedVar);
	        	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
	        	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    		}
    	}
    	SmartDashboard.putNumber("leftEncoderMoved", leftEncoder.get() - currTicksL);
    	SmartDashboard.putNumber("rightEncoderMoved", rightEncoder.get() - currTicksR);
    	SmartDashboard.putNumber("ticksOutside: ", ticksOutside);
    	
    	return true;
    }
}
