package org.usfirst.frc.team3663.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SSCompresser extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Compressor compresser;
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public SSCompresser(){
    	compresser = new Compressor(1);
    }
    public void turnOn(){
    	compresser.start();
    }
    public void turnOff(){
    	compresser.stop();
    }
}

