package org.usfirst.frc.team1218.commands.auton;

import org.usfirst.frc.team1218.commands.swerve.CalibrateOrientation;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auton_CalibrateOnly extends CommandGroup {
    
    public  Auton_CalibrateOnly() {
        addSequential(new CalibrateOrientation());
    }
}
