package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.Print;
import org.usfirst.frc.team1218.commands.elevator.ReferenceElevator;
import org.usfirst.frc.team1218.commands.swerve.AutonZeroHeading;
import org.usfirst.frc.team1218.commands.swerve.CalibrateModules;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * @author afiolmahon
 * @author lcook
 */
public class Auton_Calibrate extends CommandGroup {
    
    public  Auton_Calibrate(boolean calibrateModules) {
    	addParallel(new ReferenceElevator());
        addSequential(new AutonZeroHeading());
        if (calibrateModules) {
            addParallel(new CalibrateModules());
        }
    	addSequential(new Print("Auton_Calibrate Complete"));
    }
}
