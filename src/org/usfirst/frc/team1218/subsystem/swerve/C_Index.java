package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Angle;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author afiolmahon
 */
public class C_Index extends Command {

	private boolean indexed[] = {false, false, false, false};
	private boolean invertTravelDirection[] = {false, false, false, false};
	private int[] indexCount = new int[4];
	
	
    public C_Index() {
        requires(Robot.swerveDrive);
    }

    protected void initialize() {
    	Robot.swerveDrive.module.stream().forEach(m -> {
    		indexed[m.moduleNumber] = false;
    		m.anglePIDController.disable();
    		invertTravelDirection[m.moduleNumber] = (Angle.diffBetweenAngles(m.getEncoderAngle(), -m.moduleAngleOffset) < 180 ? true : false); //Takes the shortest route to the last known index position
    		indexCount[m.moduleNumber] = m.getEncoderIndexCount();
    		System.out.println("SM_" + m.moduleNumber + ": Distance to Index: " + Angle.diffBetweenAngles(m.getEncoderAngle(), -m.moduleAngleOffset));
    		m.driveWheelController.set(0.0);
    	});
    }

    protected void execute() {
    	for (int i = 0; i < 4; i++) {
    		SwerveModule module = Robot.swerveDrive.getModuleList().get(i);
    		if (indexCount[i] == module.getEncoderIndexCount()) {
    			//Travels shortest distance to last known index position
        		module.angleController.set((invertTravelDirection[i]) ? -0.8 : 0.8);
    		} else {
    			module.angleController.set(0.0);
    			indexed[i] = true;
    		}
    	}
    }

    protected boolean isFinished() {
        return indexed[0] && indexed[1] && indexed[2] && indexed[3];
    }

    protected void end() {
    	Robot.swerveDrive.getModuleList().stream().forEach(m -> {
    		m.anglePIDController.enable();
    		m.setAngle(0.0);
    	});
    	System.out.println("Swerve Drive Indexed");
    }

    protected void interrupted() {
    	end();
    }
}