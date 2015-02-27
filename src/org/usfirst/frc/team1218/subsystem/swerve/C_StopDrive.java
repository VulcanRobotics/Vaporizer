package org.usfirst.frc.team1218.subsystem.swerve;

import org.usfirst.frc.team1218.robot.Robot;
import org.usfirst.frc.team1218.subsystem.swerve.math.Vector;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class C_StopDrive extends Command {

    public C_StopDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.swerveDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.swerveDrive.powerDrive(new Vector(0, 0), 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
