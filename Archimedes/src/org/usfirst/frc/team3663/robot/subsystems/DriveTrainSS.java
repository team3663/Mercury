package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team3663.robot.Robot;
import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;

/**
 *
 */
public class DriveTrainSS extends Subsystem {
    
	CANTalon driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2;
	RobotDrive driveTrain;
	Encoder leftEncoder, rightEncoder;

	boolean brakeMode;
	
	final int buffer = 10;
	final double lowSpeed = -0.3;
	final double topSpeed = 0.3;
	public boolean encoderDriveEnabled = true;
	int changeInTicksL, changeInTicksR,
	lastTicksL, lastTicksR, startTicksL, startTicksR, totalTickChangeL,
	totalTickChangeR;
	double timeDiff;
	double currRateL;
	double currTime = 0;
	double lastTime = currTime;
	
	//rampingVars---------------------------
	int desiredTicks = 0;
	double currSpeed;
	double tickSpeed;
	double lastTick, currTick;
	//----------------------------------
	int ticksRequiredToStop;
	double speed;///////////////////////////////////////
	double speedL, speedR;
	boolean beginning;
	int ticksPerRevL = 780;
	int ticksPerRevR = 780;//233;
	int currTicksL, currTicksR, ticks, ticksL, ticksR;
	double distBetweenCenterAndWheelInches = 10.0;
	double tickPerInch360 = 360/23.561944905;
	//turn variables---
	int ticksOutside, ticksInside;
	double speedVar;
	double rateAtFullPower;//ticks per second
	double magConvert;
	//-----------------

	public DriveTrainSS()
	{
		driveTalonL1 = new CANTalon(10);
		driveTalonL2 = new CANTalon(12);
		driveTalonR1 = new CANTalon(11);
		driveTalonR2 = new CANTalon(13);
		driveTrain = new RobotDrive(driveTalonL1, driveTalonL2, driveTalonR1, driveTalonR2);

    	driveTalonL1.enableBrakeMode(true);
    	driveTalonL2.enableBrakeMode(true);
    	driveTalonR1.enableBrakeMode(true);
    	driveTalonR2.enableBrakeMode(true);
    	brakeMode = true;
    	
		leftEncoder = new Encoder(6,7);
		rightEncoder = new Encoder(8,9);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new C_ArcadeDrive());
    }
    
    public void arcadeDrive(Joystick joystick)
    {
    	driveTrain.arcadeDrive(joystick);
    	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
    	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());
    }
    
    boolean finished;
    int ticksAtPeak;
    
    int lastRightEncoderTicks = 0;
    int lastLeftEncoderTicks = 0;
    double max = 1.0;
    double change = .0125;
    double growth = .0125;
    double lastMag = 0;
    double lastRightSpeed = 0;
    double lastLeftSpeed = 0;
    double rampTimeStart = 0;
    double rampRightTicksStart = 0;
    double rampLeftTicksStart = 0;
    int state = 0;
	double mag = 0;
	int counter = 0;
	int incrementor = 0;
	double rampTime;
	double rampRightTicks;
	double rampLeftTicks;
	double rampRightSpeed;
	double rampLeftSpeed;
	int pass = 0;
	
	boolean firstInitKing = true;
	boolean resetEncoderDriveKing = false;
	
	public void resetEncoderDriveKing()
	{
		resetEncoderDriveKing = true;
	}
	
	public void initEncoderDriveKing(double pMax, double pGrowth)
	{
		max = pMax;
		if (firstInitKing)
		{
		    change = pGrowth;
		    growth = pGrowth;
		    firstInitKing = false;
		}
		else if (resetEncoderDriveKing)
		{
			firstInitKing = true;
			resetEncoderDriveKing = false;
		}
	    lastMag = 0;
	    lastRightSpeed = 0;
	    lastLeftSpeed = 0;
	    state = 0;
		mag = 0;
		counter = 0;
		incrementor = 0;
		rampTime = 0;
    	rampRightTicks = 0;
    	rampLeftTicks = 0;
    	rampRightSpeed = 0;
    	rampLeftSpeed = 0;
    	pass = 0;
	}
	
    public boolean encoderDriveKing(int distTicks){
    	
    	finished = false;
    	//ticksAtPeak = (int)(distTicks-(2*113000));
    	
    	double currentTime = Timer.getFPGATimestamp();
    	double deltaTime = currentTime - lastTime;
    	
    	int currentLeftEncoderTicks = leftEncoder.getRaw();
    	int deltaLeftTicks = currentLeftEncoderTicks - lastLeftEncoderTicks;
    	lastLeftEncoderTicks = currentLeftEncoderTicks;
    	
    	int currentRightEncoderTicks = rightEncoder.getRaw();
    	int deltaRightTicks = currentRightEncoderTicks - lastRightEncoderTicks;
    	lastRightEncoderTicks = currentRightEncoderTicks;
    	
    	double currentLeftSpeed = ((double)deltaLeftTicks)/deltaTime;
    	double currentRightSpeed = ((double)deltaRightTicks)/deltaTime;
    	
    	switch (state%4){
	    	case 0:
	    		// ramping up
	    		mag = mag+change;
	    		if (mag > max){
	    			mag = max;
	  			}
	    		if (mag == max && currentLeftSpeed <= lastLeftSpeed){
	    			state++;
	    			counter = 0;

	    	    	rampTime = currentTime - rampTimeStart;
	    	    	rampRightTicks = currentRightEncoderTicks - rampRightTicksStart;
	    	    	rampLeftTicks = currentLeftEncoderTicks - rampLeftTicksStart;
	    	    	rampRightSpeed = rampRightTicks/rampTime;
	    	    	rampLeftSpeed = rampLeftTicks/rampTime;
	    		}
	    		break;
	    	case 1:
	    		// peak speed
	    		mag = max;
	    		if (counter++ == 100){
	    			counter = 0;
	    			state++;

	    			rampTimeStart = currentTime;
	    		    rampRightTicksStart = currentRightEncoderTicks;
	    		    rampLeftTicksStart = currentLeftEncoderTicks;
	    			rampTime = 0;
	    	    	rampRightTicks = 0;
	    	    	rampLeftTicks = 0;
	    	    	rampRightSpeed = 0;
	    	    	rampLeftSpeed = 0;
	    		}
	    			
	    		break;
	    	case 2:
	    		// ramp down
	    		mag = mag-change;
	    		if (mag < 0){
	    			mag = 0;
	  			}
	    		if (currentLeftSpeed == 0){
	    			state++;
	    			pass++;
	    			counter = 0;

	    			rampTime = currentTime - rampTimeStart;
	    	    	rampRightTicks = currentRightEncoderTicks - rampRightTicksStart;
	    	    	rampLeftTicks = currentLeftEncoderTicks - rampLeftTicksStart;
	    	    	rampRightSpeed = rampRightTicks/rampTime;
	    	    	rampLeftSpeed = rampLeftTicks/rampTime;
	    		}
	    		break;
	    	case 3:
	    		// stopped
    			state = 0;
    			change += growth;
    			counter = 0;
    			if (change >= 1)
    				change = growth;
    			
    			rampTimeStart = currentTime;
    		    rampRightTicksStart = currentRightEncoderTicks;
    		    rampLeftTicksStart = currentLeftEncoderTicks;
    			rampTime = 0;
    	    	rampRightTicks = 0;
    	    	rampLeftTicks = 0;
    	    	rampRightSpeed = 0;
    	    	rampLeftSpeed = 0;
				mag = 0;
    	    	finished = true;
				break;
    	}
    	
    	lastRightSpeed = currentRightSpeed;
    	lastLeftSpeed = currentLeftSpeed;

		driveTalonL1.set(-mag);
	    driveTalonL2.set(-mag);
	
	    driveTalonR1.set(mag);
	    driveTalonR2.set(mag);

	    SmartDashboard.putNumber("deltaEncoderTime", deltaTime);
    	SmartDashboard.putNumber("currentLeftEncoderTicks", currentLeftEncoderTicks);
    	SmartDashboard.putNumber("deltaLeftTicks", deltaLeftTicks);
    	SmartDashboard.putNumber("currentRightEncoderTicks", currentRightEncoderTicks);
    	SmartDashboard.putNumber("deltaRightTicks", deltaRightTicks);
    	SmartDashboard.putNumber("currentLeftSpeed", currentLeftSpeed);
    	SmartDashboard.putNumber("currentRightSpeed", currentRightSpeed);
    	SmartDashboard.putNumber("mag", mag);
    	SmartDashboard.putNumber("change", change);
    	SmartDashboard.putNumber("state", state);
    	SmartDashboard.putNumber("incrementor", incrementor++);
    	SmartDashboard.putNumber("rampTime", rampTime);
    	SmartDashboard.putNumber("rampRightTicks", rampRightTicks);
    	SmartDashboard.putNumber("rampLeftTicks", rampLeftTicks);
    	SmartDashboard.putNumber("rampRightSpeed", rampRightSpeed);
    	SmartDashboard.putNumber("rampLeftSpeed", rampLeftSpeed);
    	SmartDashboard.putNumber("pass", pass);

    	return finished;
    }
    
    //------------------------------------------------------------
    public boolean encoderDrive(int ticksDL, int ticksDR, boolean stopAtEnd)
    {
    	boolean encoderDriveFinished = false;
    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	currTime = Timer.getFPGATimestamp();
    	int ticksRemaining = ticksDL-currTicksL;
    	SmartDashboard.putNumber("leftEncoder: ", currTicksL);
    	SmartDashboard.putNumber("rightEncoder: ", currTicksR);
    	if (lastTime == 0)
    	{
    		currRateL = 1;
    	}
    	else
    	{
        	currRateL = (currTicksL-lastTicksL)/(currTime-lastTime);
    	}
    	lastTime = currTime;
    	lastTicksL = currTicksL;
		///left
    	if (currTicksL < ticksDL+buffer)
    	{
    		speedL+=0.0125;
    	}
    	else if (currTicksL > ticksDL-buffer)
    	{
    		speedL-=0.0125;
    	}
		if (speedL > topSpeed)
		{
			speedL = topSpeed;
		}
		else if (speedL < lowSpeed)
		{
			speedL = lowSpeed;
		}
		////right
		if (currTicksR < ticksDR+buffer)
    	{
    		speedR+=0.0125;
    	}
    	else if (currTicksR > ticksDR-buffer)
    	{
    		speedR-=0.0125;
    	}
		if (speedR > topSpeed)
		{
			speedR = topSpeed;
		}
		else if (speedR < lowSpeed)
		{
			speedR = lowSpeed;
		}
    	totalTickChangeL = currTicksL - startTicksL;
    	totalTickChangeR = currTicksR - startTicksR;
    	if (ticksRemaining/currRateL < 0.5 && stopAtEnd)
    	{
    		speedL = -0.1;
    	}
    	if (currRateL < 10 || ticksRemaining <= 0)
    	{
    	//	speedL = 0;
    	//	encoderDriveFinished = true;
    	//	lastTime = 0;
    	}
		driveTalonL1.set(-speedL);
    	driveTalonL2.set(-speedL);
    	driveTalonR1.set(speedL);
    	driveTalonR2.set(speedL);
    	SmartDashboard.putNumber("desiredTicks", ticksDL);
    	SmartDashboard.putNumber("speed ", speedL);
    	
    	return encoderDriveFinished;
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
    
    public boolean encoderDriveF(int inches)
    {
    	speed = 0;
    	beginning = true;
    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	//conversion algorithm to ticks
    	ticksL = (int)(inches*tickPerInch360*780/360);
    	ticksR = (int)(inches*tickPerInch360*780/360);
    	while (leftEncoder.get() > (currTicksL - ticksL) || rightEncoder.get() < (currTicksR + ticksR))//see if && or ||
    	{
    		if (beginning)
    		{
    			//trying to make sure does not over accelerate if distance too short to fully accelerate
    			while (speed < topSpeed && (currTicksL - leftEncoder.get()) < (ticksL)/2)
    			{
    	    		driveTalonL1.set(-speed);
    	        	driveTalonL2.set(-speed);
    	        	driveTalonR1.set(speed);
    	        	driveTalonR2.set(speed);
    	        	speed+=0.025;
    	        	beginning = false;
    			}
    		}
    		driveTalonL1.set(-speed);
        	driveTalonL2.set(-speed);
        	driveTalonR1.set(speed);
        	driveTalonR2.set(speed);
        	//distance calculation----
        	ticksRequiredToStop = (int)(speed*360/0.098);
        	//------------------------
 /*       	if ((currTicksL - leftEncoder.get()) > ticksL - ticksRequiredToStop)
        	{
        		speed-=(0.025);
        	}
    		//driveTrain.arcadeDrive(0.2,0);
        	SmartDashboard.putNumber("leftEncoder: ", leftEncoder.get());
        	SmartDashboard.putNumber("rightEncoder: ", rightEncoder.get());        */
        	if (currTicksL - leftEncoder.get() > ticksL/2)
        	{
        		speed = 0.2;
        	}
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
    	ticksR = (int)(inches*tickPerInch360*780/360);
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
    
    public boolean encoderTurn(boolean turningL, int radius, double degreeArc)
    {
    	speedVar = -0.5;
    	
    	currTicksL = leftEncoder.get();
    	currTicksR = rightEncoder.get();
    	
    	//algorithms
    	ticksOutside = (int)(tickPerInch360*(((radius + distBetweenCenterAndWheelInches)*2*3.141592654)*(degreeArc/360.0)));
    	ticksInside = (int)(tickPerInch360*(((radius - distBetweenCenterAndWheelInches)*2*3.141592654)*(degreeArc/360.0)));
    	if (turningL) rateAtFullPower = 360/0.156;//0.2: /0.801;//1.0: /0.098;
    	else rateAtFullPower = 360/0.156;//0.2: /2.292;//1.0: /0.362;
    	magConvert = rateAtFullPower;
    	speedVar = speedVar + ((ticksInside*rateAtFullPower/ticksOutside)/magConvert);//speedVar + ticksInside/ticksOutside
    	//end of algorithms
    	
    	SmartDashboard.putNumber("ticksInside", ticksInside);
		SmartDashboard.putNumber("ticksOutside", ticksOutside);
		
    	if (turningL)
    	{
    		ticksOutside = (ticksOutside*780)/360;
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
    		ticksInside = ticksInside*780/360;
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
    
    public void setSpeed(double Speed)
    {
    	speed = Speed;
    }
    
    public void setSpeedVar(double Speed)
    {
    	speedVar = Speed;
    }
    
    public void resetEncoders()
    {
    	leftEncoder.reset();
    	rightEncoder.reset();
    }
    
    public int getLeftEncoderTicks()
    {
    	return leftEncoder.get();
    }

    public int getRightEncoderTicks()
    {
    	return rightEncoder.get();
    }
    
    public void enableEncoderDrive()
    {
    	encoderDriveEnabled = true;
    }
    
    public void disableEncoderDrive()
    {
    	encoderDriveEnabled = false;
    }
}

