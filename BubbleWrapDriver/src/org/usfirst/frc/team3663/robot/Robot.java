
package org.usfirst.frc.team3663.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import org.usfirst.frc.team3663.robot.subsystems.ExampleSubsystem;
//import org.usfirst.frc.team3663.robot.commands.ExampleCommand;
import org.usfirst.frc.team3663.robot.commands.C_ArcadeDrive;
import org.usfirst.frc.team3663.robot.subsystems.SSDriveTrain;
import org.usfirst.frc.team3663.robot.subsystems.SSArms;
import org.usfirst.frc.team3663.robot.subsystems.SSElevator;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static SSArms ssArms;
	public static SSDriveTrain ssDriveTrain;
	public static SSElevator ssElevator;
	public static OI oi;

	Command arcadeDrive;
    Command autonomousCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	ssDriveTrain = new SSDriveTrain();
    	ssElevator = new SSElevator();
    	//ssArms = new SSArms();
		oi = new OI();
		
		arcadeDrive = new C_ArcadeDrive();
       //autonomousCommand = new ExampleCommand();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
        //if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
       // if (autonomousCommand != null) autonomousCommand.cancel();
        arcadeDrive.start();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
